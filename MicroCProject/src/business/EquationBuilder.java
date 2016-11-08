package business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import models.*;

public class EquationBuilder {

    private FlowGraph fg;
    private Map<Integer, Equation> inEquations;
    private Map<Integer, Equation> outEquations;
    private Map<String, ArrayList<Tuple>> killsMap;


    public enum EquationType
    {
        REACHING_DEFINITIONS, SIGN_Equation
    }

    public EquationBuilder(FlowGraph _fg) {
        this.fg = _fg;
        this.inEquations = new HashMap<Integer, Equation>();
        this.outEquations = new HashMap<Integer, Equation>();
        this.killsMap = new HashMap<String, ArrayList<Tuple>>();
    }

    public Equation buildEquation(EquationType at) {
        Equation eq = new Equation();
        TransferFunction tf;

        switch (at) {

            case REACHING_DEFINITIONS:
                transferFunctions();
                //buildReachingDefinitionsEquations()
                break;
            case SIGN_Equation:

                break;
            default:

        }
        return eq;
    }

    public void transferFunctions() {

        HashSet<String> variables = getAllVariables();

        for (int i=0; i<fg.getBlocks().size(); i++) {
            Block b = fg.getBlocks().get(i);
            Equation in = new Equation();
            Equation out = new Equation();
            RDTransferFunction inTransferFunction = new RDTransferFunction(fg);
            RDTransferFunction outTransferFunction = new RDTransferFunction(fg);
            in.setTransferFunction(inTransferFunction);
            out.setTransferFunction(outTransferFunction);

            if (i == 0) {
                for (String var : variables) {
                    inTransferFunction.addGen(new Tuple(var, "?"));
                }
                putInEquation(b.getId(), in);
            }

            switch (b.getInstructionType()) {
                case ASSIGNMENT:
                case READ:
                case DECLARATION:
                    String var = b.getLeftVar();
                    outTransferFunction.addKills(killsMap.get(var));
                    outTransferFunction.addGen(new Tuple(var, b.getId().toString()));
                    break;
                default:

            }

            putInEquation(b.getId(), in);
            putOutEquation(b.getId(), out);
        }
        setInflowingEquations();
        setOutflowingEquations();
        printKillsAndGens();

    }

    private void setInflowingEquations() {

        for (Integer k : inEquations.keySet()) {
            System.out.println("Equations for "+k);
            for (Block b : fg.getBlocks()) {
                if (b.getId() == k) {
                    ArrayList<Integer> inFlows = b.getInFlows();
                    ArrayList<Equation> outEQ = new ArrayList<Equation>();
                    for(Integer i : inFlows) {
                        outEQ.add(outEquations.get(i));
                        System.out.println(i.toString());
                    }
                    inEquations.get(k).setEquations(outEQ);
                    break;
                }
            }
        }
    }

    private void setOutflowingEquations(){
        for (Integer k : outEquations.keySet()) {
            outEquations.get(k).addEquation(inEquations.get(k));
        }
    }

    private HashSet<String> getAllVariables() {
        HashSet<String> set = new HashSet<String>();
        for (Block b : fg.getBlocks()) {
            String lv = b.getLeftVar();
            set.add(lv);
            for (String rv : b.getRightVar()) {
                set.add(rv);
            }

            switch (b.getInstructionType()) {
                case READ:
                case ASSIGNMENT:
                case DECLARATION:
                    String var = b.getLeftVar();
                    if (!killsMap.containsKey(var)) {
                        killsMap.put(var, new ArrayList<Tuple>());
                    }
                    killsMap.get(var).add(new Tuple(var, b.getId().toString()));
                    break;
                default:

            }

        }
        return set;
    }

    private void printKillsAndGens() {
        System.out.println("____ ENTRY ____");
        Iterator it1 = getInEquations().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry) it1.next();
            Equation eq = (Equation) pair.getValue();
            RDTransferFunction tf = (RDTransferFunction) eq.getTransferFunction();
            System.out.print(((Integer) pair.getKey()).toString() + "-gen: ");
            for (Tuple t : tf.getGens()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple t : tf.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            it1.remove();
        }
        System.out.println("____ EXIT ____");
        Iterator it2 = getOutEquations().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            Equation eq = (Equation) pair.getValue();
            RDTransferFunction tf = (RDTransferFunction) eq.getTransferFunction();
            System.out.print(((Integer) pair.getKey()).toString() + "-gen: ");
            for (Tuple t : tf.getGens()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple t : tf.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            it2.remove();
        }
    }


    public Map<Integer, Equation> getInEquations() {
        return inEquations;
    }

    public Map<Integer, Equation> getOutEquations() {
        return outEquations;
    }

    public void putInEquation(Integer i, Equation a) {
        this.inEquations.put(i, a);
    }

    public void putOutEquation(Integer i, Equation a) {
        this.outEquations.put(i, a);
    }

}