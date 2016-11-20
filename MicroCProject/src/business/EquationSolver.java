package business;

import models.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.ListIterator;

public class EquationSolver {

    FlowGraph fg;
    LinkedList<Tuple> workList;
    LinkedList<Tuple> workListCopy;

    public EquationSolver(FlowGraph _fg) {
        this.fg = _fg;
        this.workList = new LinkedList<Tuple>();
    }

    private void buildWorkList() {
        for (Block b : fg.getBlocks()) {
            Integer blockId = b.getId();
            ArrayList<Integer> outFlows = b.getOutFlows();
            for (Integer out : outFlows) {
                Tuple t = new Tuple(blockId.toString(), out.toString());
                workList.add(t);
                System.out.println("workList = "  + t.toString());
            }
        }

    //    workListCopy = (LinkedList<Tuple>) workList.clone();
        workListCopy = new LinkedList<Tuple>(workList);
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

        System.out.println("IN EQUATION RESULTS");
        for(int i = 0; i < inEquations.size(); i++){
            if(inEquations.get(i) == null)
            {
                System.out.println("null: " + i);
            }
            for(Tuple t : inEquations.get(i).getResult())
            {
                System.out.println(t.getLeftString() + " " + t.getRightString());
            }
        }

        System.out.println("OUT EQUATION RESULTS");
        for(int i = 0; i < outEquations.size(); i++){
            for(Tuple t : outEquations.get(i).getResult())
            {
                System.out.println(t.getLeftString() + " " + t.getRightString());
            }
        }

        ListIterator<Tuple> iterator = workList.listIterator();


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

                for (Tuple t2 : workListCopy) {
                    if (t2.getLeftString().equals(lprime.toString()) && !workList.contains(t2)) {
                        //workList.addLast(t2);
                        iterator.add(t2);
                    }
                }
            }

            System.out.println("solveEquationRD - tuple = " + t.toString());
        }


        for (Integer i : outEquations.keySet()) {
            for (Tuple t2 : outEquations.get(i).getResult()) {
                if(i == null)
                {
                    System.out.println("Label is null");
                }
                if(t2 == null)
                {
                    System.out.println("Result is null");
                    System.out.println(i);
                }
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
                System.out.println("Label: "  + t.getLeftString() );
                System.out.println(" result: " + t.getRightString());
            }
        }
        if (!exit.contains(gen)) {
            exit.add(gen);
        }

        for(Tuple t : exit) {
            System.out.println("Label: "  + t.getLeftString());
            System.out.println(" result: " + t.getRightString());
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

