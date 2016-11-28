package business;

import models.*;
import util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.ListIterator;
import java.lang.NumberFormatException;
import java.util.HashMap;

public class EquationSolver {

    FlowGraph fg;
    Worklist workList;
    Worklist workListCopy;
    ArrayList<Tuple<String, String>> workArray;

    public EquationSolver(FlowGraph _fg, Worklist.WorklistType _wType) {
        this.fg = _fg;
        this.workArray = new ArrayList<Tuple<String, String>>();
        switch(_wType) {
            case FIFO:
                workList = new FIFOWorklist();
                workListCopy = new FIFOWorklist();
                break;
            case LIFO:
                workList = new LIFOWorklist();
                workListCopy = new LIFOWorklist();
                break;
        }
    }

    private void buildWorkList() {
        for (Block b : fg.getBlocks()) {
            Integer blockId = b.getId();
            ArrayList<Integer> outFlows = b.getOutFlows();
            for (Integer out : outFlows) {
                Tuple<String, String> t = new Tuple<String, String>(blockId.toString(), out.toString());
                workList.add(t);
                workListCopy.add(t);
                workArray.add(t);
            }
        }
    }


    public void solveEquation(EquationBuilder.EquationType eqType,
                              Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations) {
        buildWorkList();
        switch(eqType) {
            case REACHING_DEFINITIONS:
                solveEquationRD(inEquations, outEquations);
                break;
            case SIGN_ANALYSIS:
                solveEquationSA(inEquations, outEquations);
                break;
            default:
        }
    }

    private void solveEquationRD(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations) {
        ListIterator<Tuple<String, String>> iterator = workList.iterator();
        ListIterator<Tuple<String, String>> iteratorCopy = workListCopy.iterator();

        workArray.remove(0);
        Integer lastProcessedLabel = 0;

        while (iterator.hasNext()) {
            Tuple<String, String> t = iterator.next();
            iterator.remove();
            System.out.println("Solving for " + t.toString());

            Integer l = Integer.parseInt(t.getLeft());
            Integer lprime = Integer.parseInt(t.getRight());

            if (lprime > lastProcessedLabel) {
                lastProcessedLabel = lprime;
            }

            RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(l).getTransferFunction();
            ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(l).getResult(), rdTF.getKills(), rdTF.getGen());
            outEquations.get(l).setResult(exit);

            if (!outEquations.get(lprime).getResult().containsAll(exit)) {
                inEquations.get(lprime).setResult(combineLists(outEquations.get(l).getResult(), inEquations.get(lprime).getResult()));

                while (iteratorCopy.hasNext()) {
                    Tuple<String, String> t2 = iteratorCopy.next();
                    if (t2.getLeft().equals(lprime.toString()) && !workList.contains(t2)) {
                        System.out.println("ADDED!" + t2.toString());
                        workArray.add(t2);
                        iterator = workArray.listIterator();
                    }
                }
                iteratorCopy = workListCopy.iterator();
            }
        }

        System.out.println("The last out: " + lastProcessedLabel);

        RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(lastProcessedLabel).getTransferFunction();
        ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(lastProcessedLabel).getResult(), rdTF.getKills(), rdTF.getGen());

        outEquations.get(lastProcessedLabel).setResult(exit);

        for (Integer i : outEquations.keySet()) {
            for (Tuple<String, String> t2 : outEquations.get(i).getResult()) {
                System.out.println("OutEquations - Label = " + i.toString() + "; result = " + t2.toString());
            }
        }
    }

    private ArrayList<Tuple<String, String>> computeExitRD(ArrayList<Tuple<String, String>> entry,
                                                           ArrayList<Tuple<String, String>> kills, Tuple<String, String> gen) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>();
        for (Tuple<String, String> t : entry) {
            if (!kills.contains(t)) {
                exit.add(t);
            }
        }
        if (gen != null) {
            if (!exit.contains(gen)) {
                exit.add(gen);
            }
        }

        return exit;
    }

    private void solveEquationSA(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations) {
        ListIterator<Tuple<String, String>> iterator = workList.iterator();
        ListIterator<Tuple<String, String>> iteratorCopy = workListCopy.iterator();

        workArray.remove(0);
        Integer lastProcessedLabel = 0;

        while (iterator.hasNext()) {
            Tuple<String, String> t = iterator.next();

            iterator.remove();

            Integer l = Integer.parseInt(t.getLeft());
            Integer lprime = Integer.parseInt(t.getRight());

            if(lprime > lastProcessedLabel) {
                lastProcessedLabel = lprime;
            }

            SATransferFunction saTF = (SATransferFunction) outEquations.get(l).getTransferFunction();

            ArrayList<Tuple<String, String>> exit = computeExitSA(inEquations.get(l), outEquations.get(l), l);
            outEquations.get(l).setResult(exit);

            if (!inEquations.get(lprime).getResult().containsAll(outEquations.get(l).getResult())) {
                System.out.println("Entered with lprime: " + lprime);

                inEquations.get(lprime).setResult(combineStringLists(outEquations.get(l).getResult(), inEquations.get(lprime).getResult()));

                while (iteratorCopy.hasNext()) {
                    Tuple<String, String> t2 = iteratorCopy.next();
                    if (t2.getLeft().equals(lprime.toString()) && !workList.contains(t2)) {
                        System.out.println("ADDED!" + t2.toString());
                        workArray.add(t2);
                        iterator = workArray.listIterator();
                    }
                }

                iteratorCopy = workListCopy.iterator();
            }
        }


        System.out.println("The last out: " + lastProcessedLabel);
        outEquations.get(lastProcessedLabel).setResult(
                computeExitSA(inEquations.get(lastProcessedLabel), outEquations.get(lastProcessedLabel), lastProcessedLabel));

        printSignAnalysisResults(inEquations, outEquations);
    }

    private ArrayList<Tuple<String, String>> computeExitSA(Equation eq, Equation outEq, int equationLabel) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>(eq.getResult());

        Tuple<String, String> t;

        if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("AssignmentNode")) {
            for (Block b : fg.getBlocks()) {
                if (b.getId() == equationLabel) {
                    String leftVariable = b.getLeftVar();
                    ArrayList<String> rightVariables = b.getRightValues();
                    Operand operand = b.getOperand();

                    ArrayList<String> leftSigns;
                    ArrayList<String> rightSigns;

                    switch (operand) {
                        case BLANK:
                            ArrayList<String> signsOfValue = getSignsOfValue(eq.getResult(), rightVariables.get(0));
                            System.out.println("BLANK" + leftVariable);
                            for (String signOfValue : signsOfValue) {
                                t = new Tuple<String, String>(leftVariable, signOfValue);
                                if (!exit.contains(t)) {
                                    exit.add(t);
                                }
                            }
                            break;

                        case UNARY_MINUS:
                            ArrayList<String> signs = getSignsOfValue(eq.getResult(), rightVariables.get(0));

                            System.out.println("UNARY_MINUS" + leftVariable);
                            for (String sign : signs) {
                                //get the opposite sign type of this sign and add it to the results

                                t = new Tuple<String, String>(leftVariable, SAUtils.getUnaryMinusTransferFunction().get(sign).get(0));
                                if (!exit.contains(t)) {
                                    exit.add(t);
                                }
                            }
                            break;
                        case PLUS:
                            leftSigns = getSignsOfValue(eq.getResult(), rightVariables.get(0));
                            rightSigns = getSignsOfValue(eq.getResult(), rightVariables.get(1));

                            for (String leftSign : leftSigns) {
                                for (String rightSign : rightSigns) {
                                    ArrayList<String> s = SAUtils.getPlusTransferFunction().get(leftSign + rightSign);
                                    if (s != null) {
                                        for (String sign : s) {
                                            t = new Tuple<String, String>(leftVariable, sign);
                                            if (!exit.contains(t)) {
                                                exit.add(t);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case MINUS:
                            leftSigns = getSignsOfValue(eq.getResult(), rightVariables.get(0));
                            rightSigns = getSignsOfValue(eq.getResult(), rightVariables.get(1));
                            for (String leftSign : leftSigns) {
                                for (String rightSign : rightSigns) {
                                    ArrayList<String> s = SAUtils.getMinusTransferFunction().get(leftSign + rightSign);
                                    if (s != null) {
                                        for (String sign : s) {
                                            t = new Tuple<String, String>(leftVariable, sign);
                                            if (!exit.contains(t)) {
                                                exit.add(t);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case MUL:
                            leftSigns = getSignsOfValue(eq.getResult(), rightVariables.get(0));
                            rightSigns = getSignsOfValue(eq.getResult(), rightVariables.get(1));
                            for (String leftSign : leftSigns) {
                                for (String rightSign : rightSigns) {
                                    ArrayList<String> s = SAUtils.getProductTransferFunction().get(leftSign + rightSign);
                                    if (s != null) {
                                        for (String sign : s) {
                                            t = new Tuple<String, String>(leftVariable, sign);
                                            if (!exit.contains(t)) {
                                                exit.add(t);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case DIV:
                            leftSigns = getSignsOfValue(eq.getResult(), rightVariables.get(0));
                            rightSigns = getSignsOfValue(eq.getResult(), rightVariables.get(1));

                            if(!rightSigns.contains("0")) {
                                for (String leftSign : leftSigns) {
                                    for (String rightSign : rightSigns) {
                                        ArrayList<String> s = SAUtils.getDivisionTransferFunction().get(leftSign + rightSign);
                                        if (s != null) {
                                            for (String sign : s) {
                                                t = new Tuple<String, String>(leftVariable, sign);
                                                if (!exit.contains(t)) {
                                                    exit.add(t);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        } else if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("DeclarationNode")) {
            for (Block b : fg.getBlocks()) {
                String leftVariable = b.getLeftVar();
                if (b.getId() == equationLabel) {
                    System.out.println("DECLARATION" + leftVariable);
                    String declaredVariable = b.getLeftVar();

                    ArrayList<String> s = SAUtils.getArrayListZero();
                    for (String sign : s) {
                        t = new Tuple<String, String>(leftVariable, sign);
                        if (!exit.contains(t)) {
                            exit.add(t);
                        }
                    }
                    break;
                }
            }
        }

        return exit;
    }

    private ArrayList<String> getSignsOfValue(ArrayList<Tuple<String, String>> variableSigns, String value)
    {
        try {
            // we have a constant
            int constant = Integer.parseInt(value);
            if (constant == 0){
                return SAUtils.getArrayListZero();
            }
            else {
                return SAUtils.getArrayListPlus();
            }
        }
        catch (NumberFormatException ex){
            //we have a variable
            ArrayList<String> signs = new ArrayList<String>();

            for(Tuple<String, String> variableSign : variableSigns)
            {
                if(variableSign.getLeft().equals(value)){
                    signs.add(variableSign.getRight());
                }
            }

            return signs;
        }
    }


    private Map<Integer, Equation> ripleChanges(int label, ArrayList<Tuple<String, String>> changes, Map<Integer, Equation> inEquations)
    {
        for (Block b : fg.getBlocks()) {
            if(b.getId() == label) {
                ArrayList<Integer> outFlows = b.getOutFlows();
                for(int i : outFlows){
                    ArrayList<Tuple<String, String>> currentResult = inEquations.get(i).getResult();

                    for(Tuple<String, String> change : changes){
                        if(!currentResult.contains(change)){
                            inEquations.get(i).addResult(change);
                        }
                    }
                }
            }
        }

        return inEquations;
    }

    private ArrayList<Tuple<String, String>> combineLists(ArrayList<Tuple<String, String>> l1, ArrayList<Tuple<String, String>> l2) {
        ArrayList<Tuple<String, String>> l3 = new ArrayList<Tuple<String, String>>(l1);
        for (Tuple<String, String> t : l2) {
            if (!l1.contains(t)) {
                l3.add(t);
            }
        }
        return l3;
    }

    private ArrayList<Tuple<String, String>> combineStringLists(ArrayList<Tuple<String, String>> l1, ArrayList<Tuple<String, String>> l2) {
        ArrayList<Tuple<String, String>> l3 = new ArrayList<Tuple<String, String>>(l1);
        for (Tuple<String, String> t : l2) {
            if (!l1.contains(t)) {
                l3.add(t);
            }
        }
        return l3;
    }

    private void printSignAnalysisResults(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations)
    {
        for(Integer i : inEquations.keySet())
        {
            System.out.println("In equation " + i);
            System.out.println(inEquations.get(i).getResult().toString());

            System.out.println("Out equation " + i);
            System.out.println(outEquations.get(i).getResult().toString());
        }
    }
}

