package business;

import models.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.Iterator;

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
                Tuple t = new Tuple(blockId.toString(), out.toString());
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
        Iterator<Tuple> iterator = workList.iterator();
        Iterator<Tuple> iteratorCopy = workListCopy.iterator();

        while (iterator.hasNext()) {
            Tuple t = iterator.next();
            iterator.remove();

            Integer l = Integer.parseInt(t.getLeftString());
            RDTransferFunction rdTF = (RDTransferFunction) outEquations.get(l).getTransferFunction();
            ArrayList<Tuple> exit = computeExit(inEquations.get(l).getResult(), rdTF.getKills(), rdTF.getGen());
            inEquations = ripleChanges(l, exit, inEquations);

            outEquations.get(l).setResult(exit);
            Integer lprime = Integer.parseInt(t.getRightString());
            if(!outEquations.get(lprime).getResult().containsAll(exit))
            {
                outEquations.get(lprime).setResult(combineLists(exit, outEquations.get(lprime).getResult()));

                while (iteratorCopy.hasNext()) {
                    Tuple t2 = iteratorCopy.next();
                    if (t2.getLeftString().equals(lprime.toString()) && !workList.contains(t2)) {
                        workList.add(t2);
                    }
                }

            }

            System.out.println("solveEquationRD - tuple = " + t.toString());
        }


        for (Integer i : outEquations.keySet()) {
            for (Tuple t2 : outEquations.get(i).getResult()) {
                System.out.println("OutEquations - Label = " + i.toString() + "; result = " + t2.toString());
            }
        }
    }

    private void solveEquationSA(Map<Integer, Equation> inEquations, Map<Integer, Equation> outEquations) {

    }

    private ArrayList<Tuple> computeExit(ArrayList<Tuple> entry, ArrayList<Tuple> kills, Tuple gen) {
        ArrayList<Tuple> exit = new ArrayList<Tuple>();
        for (Tuple t : entry) {
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

    private Map<Integer, Equation> ripleChanges(int label, ArrayList<Tuple> changes, Map<Integer, Equation> inEquations)
    {
        for (Block b : fg.getBlocks()) {
            if(b.getId() == label) {
                ArrayList<Integer> outFlows = b.getOutFlows();
                for(int i : outFlows){
                    ArrayList<Tuple> currentResult = inEquations.get(i).getResult();

                    for(Tuple change : changes){
                        if(!currentResult.contains(change)){
                            inEquations.get(i).addResult(change);
                        }
                    }
                }
            }
        }

        return inEquations;
    }

    private ArrayList<Tuple> combineLists(ArrayList<Tuple> l1, ArrayList<Tuple> l2) {
        ArrayList<Tuple> l3 = new ArrayList<Tuple>(l1);
        for (Tuple t : l2) {
            if (!l1.contains(t)) {
                l3.add(t);
            }
        }
        return l3;
    }



}

