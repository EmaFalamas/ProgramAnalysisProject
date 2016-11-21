package business;

import models.*;
import util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.Iterator;
import java.lang.NumberFormatException;
import java.util.HashMap;

public class EquationSolver {

    FlowGraph fg;
    Worklist workList;
    Worklist workListCopy;

    public EquationSolver(FlowGraph _fg, Worklist.WorklistType _wType) {
        this.fg = _fg;
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
                System.out.println("workList = "  + t.toString());
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
        System.out.println("inEquations size = " + inEquations.size());
        Iterator<Tuple<String, String>> iterator = workList.iterator();
        Iterator<Tuple<String, String>> iteratorCopy = workListCopy.iterator();

        while (iterator.hasNext()) {
            Tuple<String, String> t = iterator.next();
            iterator.remove();

            Integer l = Integer.parseInt(t.getLeft());
            RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(l).getTransferFunction();
            ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(l).getResult(), rdTF.getKills(), rdTF.getGen());
            inEquations = ripleChanges(l, exit, inEquations);

            outEquations.get(l).setResult(exit);
            Integer lprime = Integer.parseInt(t.getRight());
            if(!outEquations.get(lprime).getResult().containsAll(exit))
            {
                outEquations.get(lprime).setResult(combineLists(exit, outEquations.get(lprime).getResult()));

                while (iteratorCopy.hasNext()) {
                    Tuple<String, String> t2 = iteratorCopy.next();
                    if (t2.getLeft().equals(lprime.toString()) && !workList.contains(t2)) {
                        workList.add(t2);
                    }
                }

            }

            System.out.println("solveEquationRD - Tuple<String, String> = " + t.toString());
        }


        for (Integer i : outEquations.keySet()) {
            for (Tuple<String, String> t2 : outEquations.get(i).getResult()) {
                System.out.println("OutEquations - Label = " + i.toString() + "; result = " + t2.toString());
            }
        }
    }

    private void solveEquationSA(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations) {
        Iterator<Tuple<String, String>> iterator = workList.iterator();
        Iterator<Tuple<String, String>> iteratorCopy = workListCopy.iterator();
        Map<String, ArrayList<SignType>> entry= new HashMap <String, ArrayList<SignType>>();

        for( Tuple<String,String> t : inEquations.get(0).getResult() ){
            if(!entry.containsKey(t.getLeft())) {
                entry.put(t.getLeft(),new ArrayList<SignType>());
            }
            switch (t.getRight()) {
                case "+":
                    entry.get(t.getLeft()).add(SignType.PLUS);
                    break;
                case "0":
                    entry.get(t.getLeft()).add(SignType.ZERO);
                    break;
                case "-":
                    entry.get(t.getLeft()).add(SignType.MINUS);
                    break;
                default:
                    break;
            }
        }

        while (iterator.hasNext()) {
            Tuple<String, String> t = iterator.next();
            iterator.remove();

            Integer l = Integer.parseInt(t.getLeft());
            SATransferFunction saTF = (SATransferFunction) outEquations.get(l).getTransferFunction();
            Map<String, ArrayList<SignType>> exit = computeExitSA(inEquations.get(l), l, entry);
            /*inEquations = ripleChanges(l, exit, inEquations);

            outEquations.get(l).setResult(exit);
            Integer lprime = Integer.parseInt(t.getRight());
            if(!outEquations.get(lprime).getResult().containsAll(exit))
            {
                outEquations.get(lprime).setResult(combineLists(exit, outEquations.get(lprime).getResult()));

                while (iteratorCopy.hasNext()) {
                    Tuple<String, String> t2 = iteratorCopy.next();
                    if (t2.getLeft().equals(lprime.toString()) && !workList.contains(t2)) {
                        workList.add(t2);
                    }
                }

            }*/
        }
    }

    private ArrayList<Tuple<String, String>> computeExitRD(ArrayList<Tuple<String, String>> entry, ArrayList<Tuple<String, String>> kills, Tuple<String, String> gen) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>();
        for (Tuple<String, String> t : entry) {
            if (!kills.contains(t)) {
                exit.add(t);
            }
        }
        if(gen != null) {
            if (!exit.contains(gen)) {
                exit.add(gen);
            }
        }

        return exit;
    }

    private Map<String, ArrayList<SignType>> computeExitSA(Equation eq, int equationLabel,
                                                             Map<String, ArrayList<SignType>> variableSigns) {
        //ArrayList<Tuple<String, SignType>> exit = new ArrayList<Tuple<String, SignType>>();

        if (eq.getTransferFunction().getInstructionNode().getLabel().equals("AssignmentNode")) {
            for (Block b : fg.getBlocks())
            {
                if (b.getId() == equationLabel)
                {
                    String leftVariable = b.getLeftVar();
                    ArrayList<String> rightVariables = b.getRightValues();
                    Operand operand = b.getOperand();

                    switch (operand)
                    {
                        case BLANK:
                            try {
                                // we have a constant
                                int constant = Integer.parseInt(rightVariables.get(0));
                                if (constant == 0){
                                    variableSigns.get(leftVariable).addAll(SAUtils.getArrayListZero());
                                }
                                else {
                                    variableSigns.get(leftVariable).addAll(SAUtils.getArrayListPlus());
                                }
                            }
                            catch (NumberFormatException ex){
                                //we have a variable

                                variableSigns.get(leftVariable).addAll(variableSigns.get(rightVariables.get(0)));
                            }
                            break;

                        case UNARY_MINUS:
                            try {
                                // we have a constant
                                int constant = Integer.parseInt(rightVariables.get(0));
                                if (constant == 0){
                                    variableSigns.get(leftVariable).addAll(SAUtils.getArrayListZero());
                                }
                                else {
                                    variableSigns.get(leftVariable).addAll(SAUtils.getArrayListMinus());
                                }
                            }
                            catch (NumberFormatException ex){
                                //we have a variable
                                variableSigns.get(leftVariable).addAll(SAUtils.getUnaryMinusTransferFunction().get(variableSigns.get(rightVariables.get(0))));
                            }
                            break;
                    }

                    break;
                }
            }
        }
        if (eq.getTransferFunction().getInstructionNode().getLabel().equals("DeclarationNode")) {
            for(Block b : fg.getBlocks())
            {
                String leftVariable = b.getLeftVar();
                if(b.getId() == equationLabel)
                {
                    String declaredVariable = b.getLeftVar();
                    variableSigns.get(leftVariable).addAll(SAUtils.getArrayListZero());
                    break;
                }
            }
        }

        return variableSigns;
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



}

