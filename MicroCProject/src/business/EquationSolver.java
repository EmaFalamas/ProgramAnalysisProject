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
        //for each block in the flow graph, add to the woklist all tuples of the form (block_id, out_flow), for every
        //out_flow corresponding to the block
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
            //get the next tuple from the worklist
            Tuple<String, String> t = iterator.next();
            iterator.remove();

            //initialize l and lprime as the left and right strings of the tuple
            Integer l = Integer.parseInt(t.getLeft());
            Integer lprime = Integer.parseInt(t.getRight());

            //get the maximum label in the worklist's lprimes
            if (lprime > lastProcessedLabel) {
                lastProcessedLabel = lprime;
            }

            //get the transfer function of the out equation
            RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(l).getTransferFunction();
            //compute the result of the out equation, using the in equation, the kills and the gen from the transfer function
            ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(l).getResult(), rdTF.getKills(), rdTF.getGen());
            outEquations.get(l).setResult(exit);

            //check for the inclusion condition of the algorithm (step 3)
            if (!outEquations.get(lprime).getResult().containsAll(exit)) {
                //if the condition is sarisfied, the result of the in equation corresponding to lprime will become
                //the union of the current result of the in equation and the result of the out equation corresponding
                //to l
                inEquations.get(lprime).setResult(combineLists(outEquations.get(l).getResult(), inEquations.get(lprime).getResult()));

                //add to the worklist the tuples of the form (lprime, lsecond)
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

        //compute the result of the out equation corresponding to the maximum lprime in the worklist
        RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(lastProcessedLabel).getTransferFunction();
        ArrayList<Tuple<String, String>> exit = computeExitRD(inEquations.get(lastProcessedLabel).getResult(), rdTF.getKills(), rdTF.getGen());
        outEquations.get(lastProcessedLabel).setResult(exit);

        printResults(inEquations, outEquations);
    }

    private ArrayList<Tuple<String, String>> computeExitRD(ArrayList<Tuple<String, String>> entry,
                                                           ArrayList<Tuple<String, String>> kills, Tuple<String, String> gen) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>();

        //for all the tuples in the in equation result, if they are not in the list of kills, add them to the result of
        //the out equation
        for (Tuple<String, String> t : entry) {
            if (!kills.contains(t)) {
                exit.add(t);
            }
        }

        //if gen is not null and not already in the reault of the out equation, add it
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

        //for every tuple in the worklist
        while (iterator.hasNext()) {
            Tuple<String, String> t = iterator.next();
            iterator.remove();

            //set the l and lprime according to the tuple
            Integer l = Integer.parseInt(t.getLeft());
            Integer lprime = Integer.parseInt(t.getRight());

            //compute the maximum label in the worklist's lprimes
            if(lprime > lastProcessedLabel) {
                lastProcessedLabel = lprime;
            }

            //compute the result of the out equation l, according to the in equation, the out equation and the label l
            ArrayList<Tuple<String, String>> exit = computeExitSA(inEquations.get(l), outEquations.get(l), l);
            outEquations.get(l).setResult(exit);

            //check for the inclusion condition of the algorithm
            if (!inEquations.get(lprime).getResult().containsAll(outEquations.get(l).getResult())) {
                //if the condition checks, set the result of the in equation lprime to the union of the current result
                //of the in equation lprime and the result of the out equation of l
                inEquations.get(lprime).setResult(combineLists(outEquations.get(l).getResult(), inEquations.get(lprime).getResult()));

                //add all the tuples of the form (lprime, lsecond) to the worklist
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

        //compute the result of the out equation corresponding to the maximum lprime in the worklist
        outEquations.get(lastProcessedLabel).setResult(
                computeExitSA(inEquations.get(lastProcessedLabel), outEquations.get(lastProcessedLabel), lastProcessedLabel));
        printResults(inEquations, outEquations);
    }

    private ArrayList<Tuple<String, String>> computeExitSA(Equation eq, Equation outEq, int equationLabel) {
        ArrayList<Tuple<String, String>> exit = new ArrayList<Tuple<String, String>>(eq.getResult());

        Tuple<String, String> t;

        //if the instruction corresponding to the out equation is an assignment
        if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("AssignmentNode")) {

            //find the block corresponding to the instruction
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
                                //get the sign of the variable on the right side (we will look in the list of signs
                                //corresponding to the current equation and get all the signs corresponding to
                                //the variable on the right side
                                ArrayList<String> signs = getSignsOfTuple(eq.getResult(), rightVariables.get(0));

                                //if we have an illegal sign (division by zero) the result becomes the bottom state
                                //(an empty arraylist)
                                if(signs.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                else {
                                    //remove from the exit all the tuples corresponding to the left variable (because
                                    //the sign is unary minus, and we will get completely opposite signs)
                                    for (Tuple<String, String> t2 : eq.getResult()) {
                                        if (t2.getLeft().equals(leftVariable.getLeft())) {
                                            exit.remove(t2);
                                        }
                                    }
                                    //for all the signs that were computed, add to the exit list a new tuple of the form
                                    //(left_variable, sign)
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

                                //if there are any illegal signs, the equation's result becomes the bottom state
                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                //otherwise, compute the results of combining every left sign with every right sign and
                                //add these results to the exit list
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

                                //if there are any illegal signs, the equation's result becomes the bottom state
                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                //otherwise, compute the results of combining every left sign with every right sign and
                                //add these results to the exit list
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

                                //if there are any illegal signs, the equation's result becomes the bottom state
                                if(leftSigns.contains("illegal") || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                //otherwise, compute the results of combining every left sign with every right sign and
                                //add these results to the exit list
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

                                //if there are any illegal signs, the equation's result becomes the bottom state
                                if(rightSigns.contains("0") || leftSigns.contains("illegal")
                                        || rightSigns.contains("illegal")) {
                                    exit = new ArrayList<Tuple<String, String>>();
                                }
                                //otherwise, compute the results of combining every left sign with every right sign and
                                //add these results to the exit list
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
                    }
                    //if the operand is null (the instruction is of type read, write and so on)
                    else {
                        ArrayList<String> signsOfValue = getSignsOfTuple(eq.getResult(), rightVariables.get(0));

                        //if any of the signs are illegal, set the equation's result to the bottom state
                        if(signsOfValue.contains("illegal")) {
                            exit = new ArrayList<Tuple<String, String>>();
                        }
                        else {
                            //remove from the result all the tuples where the variable is the left variable, that we
                            //have ripled from the in equation
                            for (Tuple<String, String> t2 : eq.getResult()) {
                                if (t2.getLeft().equals(leftVariable.getLeft())) {
                                    exit.remove(t2);
                                }
                            }
                            //add to the result all the tuples of the form (left_variable, sign)
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
        }
        //if the instruction type is declaration, add a tuple of the form (variable, 0) to the result
        else if (outEq.getTransferFunction().getInstructionNode().getLabel().equals("DeclarationNode")) {
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
        //we get the value and the index of the tuple (in case the tuple is (A, i) it corresponds to the variable A[i]
        //in case it is of the form (x, ""), it corresponds to the variable x
        String value = valueTuple.getLeft();
        String index = valueTuple.getRight();

        //if we don't have an index, we return the sign of the value
        if(index.equals(""))
        {
            return getSignsOfValue(variableSigns, value);
        }
        else
        {
            //if the index canNOT be negative, return the signs of the value
            if(!getSignsOfValue(variableSigns, index).contains("-")) {
                return getSignsOfValue(variableSigns, value);
            }
            else {
                //if the index can be negative, return the illegal state
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


    private ArrayList<Tuple<String, String>> combineLists(ArrayList<Tuple<String, String>> l1, ArrayList<Tuple<String, String>> l2) {
        //perform in l3 the union of l1 and l2
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

