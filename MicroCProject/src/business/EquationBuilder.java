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
    private Map<String, ArrayList<Tuple<String, String>>> killsMap;
    private HashSet<String> variables;

    public enum EquationType
    {
        REACHING_DEFINITIONS, SIGN_ANALYSIS
    }

    public EquationBuilder(FlowGraph _fg) {
        this.fg = _fg;
        this.inEquations = new HashMap<Integer, Equation>();
        this.outEquations = new HashMap<Integer, Equation>();
        this.killsMap = new HashMap<String, ArrayList<Tuple<String, String>>>();
        this.variables = getAllVariables();
    }

    public Equation buildEquation(EquationType at) {
        Equation eq = new Equation();
        TransferFunction tf;

        switch (at) {
            case REACHING_DEFINITIONS:
                setRDEquations();
                break;
            case SIGN_ANALYSIS:
                setSignEquations();
                break;
            default:

        }
        return eq;
    }

    public void setRDEquations() {

        int i = 0;
        for (Block b : fg.getBlocks()) {
            Equation in = new Equation();
            Equation out = new Equation();
            RDTransferFunction inTransferFunction = new RDTransferFunction();
            RDTransferFunction outTransferFunction = new RDTransferFunction();
            in.setTransferFunction(inTransferFunction);
            out.setTransferFunction(outTransferFunction);

            if (i == 0) {
                for (String var : variables) {
                    in.addResult(new Tuple<String, String>(var, "?"));
                    System.out.println("RDvar = " + var);
                }
                putInEquation(b.getId(), in);
            }

            switch (b.getInstructionType()) {
                case ASSIGNMENT:
                case READ:
                case DECLARATION:
                    String var = b.getLeftVar().getLeft();
                    outTransferFunction.addKills(killsMap.get(var));
                    outTransferFunction.setGen(new Tuple<String, String>(var, b.getId().toString()));
                    break;
                default:

            }

            putInEquation(b.getId(), in);
            putOutEquation(b.getId(), out);
            i++;
        }

        setInflowingEquations();
        setOutflowingEquations();
        printKillsAndGens();
        System.out.println("buildEquation end - inEquations size = " + inEquations.size());

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

    public boolean isNumeric(String s)
    {
        try {
            int constant = Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

    private HashSet<String> getAllVariables() {
        HashSet<String> set = new HashSet<String>();
        for (Block b : fg.getBlocks()) {
            if (b.getLeftVar() != null) {
                String lv = b.getLeftVar().getLeft();
                if (lv != null && !isNumeric(lv)) {
                    set.add(lv);
                }
                for (Tuple<String, String> trv : b.getRightValues()) {
                    String rv = trv.getLeft();
                    if(!isNumeric(rv)) {
                        set.add(rv);
                    }
                }

                switch (b.getInstructionType()) {
                    case READ:
                    case ASSIGNMENT:
                    case DECLARATION:
                        String var = b.getLeftVar().getLeft();
                        if (!killsMap.containsKey(var)) {
                            killsMap.put(var, new ArrayList<Tuple<String, String>>());
                        }
                        killsMap.get(var).add(new Tuple<String, String>(var, b.getId().toString()));
                        break;
                    default:

                }
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
            if(tf.getGen() != null) {
                System.out.print(tf.getGen().toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple<String, String> t : tf.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            //it1.remove();
        }
        System.out.println("____ EXIT ____");
        Iterator it2 = getOutEquations().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            Equation eq = (Equation) pair.getValue();
            RDTransferFunction tf = (RDTransferFunction) eq.getTransferFunction();
            System.out.print(((Integer) pair.getKey()).toString() + "-gen: ");
            if(tf.getGen() != null) {
                System.out.print(tf.getGen().toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple<String, String> t : tf.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            //it2.remove();
        }
    }

    private void setSignEquations() {
        initSAEquations();
        for(Block b : fg.getBlocks()) {
            SATransferFunction tf = new SATransferFunction();
            tf.setInstructionNode(b.getInstructionNode());
            outEquations.get(b.getId()).setTransferFunction(tf);
            System.out.println(b.getInstruction());
        }
        setInflowingEquations();
    }

    public void initSAEquations() {


        for (Block b : fg.getBlocks()) {
            Equation in = new Equation();
            Equation out = new Equation();

            putInEquation(b.getId(), in);
            putOutEquation(b.getId(), out);
        }
    }

    public Map<Integer, Equation> getInEquations() {
        System.out.println("getInEquations() called - size = " + inEquations.size());
        return this.inEquations;
    }

    public Map<Integer, Equation> getOutEquations() {
        return outEquations;
    }

    public void putInEquation(Integer i, Equation a) {
        this.inEquations.put(i, a);
        System.out.println("putInEquations called - size = " + inEquations.size());
    }

    public void putOutEquation(Integer i, Equation a) {
        this.outEquations.put(i, a);
    }

}