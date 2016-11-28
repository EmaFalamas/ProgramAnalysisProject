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

    private FlowGraph fg;
    private Worklist workList;
    private Worklist workListCopy;
    private ArrayList<Tuple<String, String>> workArray;
    private Worklist.WorklistType wType;

    public EquationSolver(FlowGraph _fg, Worklist.WorklistType _wType) {
        this.fg = _fg;
        this.workArray = new ArrayList<Tuple<String, String>>();
        this.wType = _wType;
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
                              EquationBuilder eb) {
        buildWorkList();
        switch(eqType) {
            case REACHING_DEFINITIONS:
                solveEquationRD(eb.getInEquations(), eb.getOutEquations());
                break;
            case SIGN_ANALYSIS:
                solveEquationSA(eb.getInEquations(), eb.getOutEquations());
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
                        workArray.add(t2);
                        iterator = workArray.listIterator();
                    }
                }
                iteratorCopy = workListCopy.iterator();
            }
        }


        RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(lastProcessedLabel).getTransferFunction();
        ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(lastProcessedLabel).getResult(), rdTF.getKills(), rdTF.getGen());
        outEquations.get(lastProcessedLabel).setResult(exit);
        printResults(inEquations, outEquations);
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
                inEquations.get(lprime).setResult(combineStringLists(outEquations.get(l).getResult(), inEquations.get(lprime).getResult()));
                while (iteratorCopy.hasNext()) {
                    Tuple<String, String> t2 = iteratorCopy.next();
                    if (t2.getLeft().equals(lprime.toString()) && !workList.contains(t2)) {
                        workArray.add(0, t2);
                        iterator = workArray.listIterator();
                    }
                }

                iteratorCopy = workListCopy.iterator();
            }
        }

        outEquations.get(lastProcessedLabel).setResult(
                computeExitSA(inEquations.get(lastProcessedLabel), outEquations.get(lastProcessedLabel), lastProcessedLabel));
        printResults(inEquations, outEquations);
    }

    private ArrayList<Tuple<String, String>> computeExitSA(Equation eq, Equation outEq, int equationLabel) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>(eq.getResult());

        Tuple<String, String> t;

        if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("AssignmentNode")) {
            for (Block b : fg.getBlocks()) {
                if (b.getId() == equationLabel) {
                    Tuple<String, String> leftVariable = b.getLeftVar();
                    ArrayList<Tuple<String, String>> rightVariables = b.getRightValues();
                    Operand operand = b.getOperand();

                    if (operand != null) {
                        ArrayList<String> leftSigns;
                        ArrayList<String> rightSigns;

                        switch (operand) {
                            case UNARY_MINUS:
                                ArrayList<String> signs = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                                if(signs.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    for (Tuple<String, String> t2 : eq.getResult()) {
                                        if (t2.getLeft().equals(leftVariable.getLeft())) {
                                            exit.remove(t2);
                                        }
                                    }
                                    for (String sign : signs) {
                                        t = new Tuple<String, String>(leftVariable.getLeft(), SAUtils.getUnaryMinusTransferFunction().get(sign).get(0));
                                        if (!exit.contains(t)) {
                                            exit.add(t);
                                        }
                                    }
                                }
                                break;
                            case PLUS:
                                leftSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                                rightSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(1));

                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    for (String leftSign : leftSigns) {
                                        for (String rightSign : rightSigns) {
                                            ArrayList<String> s = SAUtils.getPlusTransferFunction().get(leftSign + rightSign);
                                            if (s != null) {
                                                for (String sign : s) {
                                                    t = new Tuple<String, String>(leftVariable.getLeft(), sign);
                                                    if (!exit.contains(t)) {
                                                        exit.add(t);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case MINUS:
                                leftSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                                rightSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(1));
                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    for (String leftSign : leftSigns) {
                                        for (String rightSign : rightSigns) {
                                            ArrayList<String> s = SAUtils.getMinusTransferFunction().get(leftSign + rightSign);
                                            if (s != null) {
                                                for (String sign : s) {
                                                    t = new Tuple<String, String>(leftVariable.getLeft(), sign);
                                                    if (!exit.contains(t)) {
                                                        exit.add(t);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case MUL:
                                leftSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                                rightSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(1));
                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    for (String leftSign : leftSigns) {
                                        for (String rightSign : rightSigns) {
                                            ArrayList<String> s = SAUtils.getProductTransferFunction().get(leftSign + rightSign);
                                            if (s != null) {
                                                for (String sign : s) {
                                                    t = new Tuple<String, String>(leftVariable.getLeft(), sign);
                                                    if (!exit.contains(t)) {
                                                        exit.add(t);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case DIV:
                                leftSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                                rightSigns = getSignsOfTuple(eq.getResult(), rightVariables.get(1));


                                if(rightSigns.contains("0") || leftSigns.contains("illegal")
                                        || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    for (String leftSign : leftSigns) {
                                        for (String rightSign : rightSigns) {
                                            ArrayList<String> s = SAUtils.getDivisionTransferFunction().get(leftSign + rightSign);
                                            if (s != null) {
                                                for (String sign : s) {
                                                    t = new Tuple<String, String>(leftVariable.getLeft(), sign);
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
                    } else {
                        ArrayList<String> signsOfValue = getSignsOfTuple(eq.getResult(), rightVariables.get(0));
                        if(signsOfValue.contains("illegal")) {
                            exit = new ArrayList<Tuple<String, String>>();
                        }
                        else {
                            for (Tuple<String, String> t2 : eq.getResult()) {
                                if (t2.getLeft().equals(leftVariable.getLeft())) {
                                    exit.remove(t2);
                                }
                            }
                            for (String signOfValue : signsOfValue) {
                                t = new Tuple<String, String>(leftVariable.getLeft(), signOfValue);
                                if (!exit.contains(t)) {
                                    exit.add(t);
                                }
                            }
                        }
                    }
                }
            }
        } else if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("DeclarationNode")) {
            for (Block b : fg.getBlocks()) {
                Tuple<String, String> leftVariable = b.getLeftVar();
                if (b.getId() == equationLabel) {
                    ArrayList<String> s = SAUtils.getArrayListZero();
                    for (String sign : s) {
                        t = new Tuple<String, String>(leftVariable.getLeft(), sign);
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

    private ArrayList<String> getSignsOfTuple(ArrayList<Tuple<String, String>> variableSigns, Tuple<String,String> valueTuple)
    {
        String value = valueTuple.getLeft();
        String index = valueTuple.getRight();

        if(index.equals(""))
        {
            return getSignsOfValue(variableSigns, value);
        }
        else
        {
            if(!getSignsOfValue(variableSigns, index).contains("-")) {
                return getSignsOfValue(variableSigns, value);
            }
            else {
                return SAUtils.getArrayListIllegal();
            }
        }
    }

    private ArrayList<String> getSignsOfValue(ArrayList<Tuple<String, String>> variableSigns, String value) {
        try {
            // we have a constant
            int constant = Integer.parseInt(value);
            if (constant == 0) {
                return SAUtils.getArrayListZero();
            } else {
                return SAUtils.getArrayListPlus();
            }
        } catch (NumberFormatException ex) {
            //we have a variable
            ArrayList<String> signs = new ArrayList<String>();

            for (Tuple<String, String> variableSign : variableSigns) {
                if (variableSign.getLeft().equals(value)) {
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

    private void printResults(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations)
    {
        System.out.println("_____________ Solution of the equation _____________");
        for(Integer i : inEquations.keySet())
        {
            String in = "In equation " + i + ": " + inEquations.get(i).getResult().toString();
            System.out.println(in);

            String out = "Out equation " + i + ": " + outEquations.get(i).getResult().toString();
            System.out.println(out);
        }
    }

    public Worklist.WorklistType getWorklistType() {
        return wType;
    }

}

