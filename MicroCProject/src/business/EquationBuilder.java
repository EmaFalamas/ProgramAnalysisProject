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
        this.killsMap = getKillsMap();
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

        //for every block in the flow graph
        for (Block b : fg.getBlocks()) {
            //initialize a new in and out equation, and transfer functions for both of them
            Equation in = new Equation();
            Equation out = new Equation();
            RDTransferFunction inTransferFunction = new RDTransferFunction();
            RDTransferFunction outTransferFunction = new RDTransferFunction();
            in.setTransferFunction(inTransferFunction);
            out.setTransferFunction(outTransferFunction);

            //if it's the first equation, add a tuple of the form (var, ?) to the result of the in equation for
            //every variable var
            if (i == 0) {
                for (String var : variables) {
                    in.addResult(new Tuple<String, String>(var, "?"));
                }
                putInEquation(b.getId(), in);
            }

            //in case the current instruction is an assignment, a read or a declaration, set the transfer function of
            //its equation
            switch (b.getInstructionType()) {
                case ASSIGNMENT:
                case READ:
                case DECLARATION:
                    //get the variable on the left and set its kills to the entries that correspond to it in the kills
                    //map; the gen will be the tuple (var, block_label)
                    String var = b.getLeftVar().getLeft();
                    outTransferFunction.addKills(killsMap.get(var));
                    outTransferFunction.setGen(new Tuple<String, String>(var, b.getId().toString()));
                    break;
                default:

            }

            //store the in and out equations in their respective maps
            putInEquation(b.getId(), in);
            putOutEquation(b.getId(), out);
            i++;
        }

        setInflowingEquations();
        setOutflowingEquations();
        //printKillsAndGens();

    }

    private void setInflowingEquations() {
        //for all the in equations look for the block corresponding to them
        for (Integer k : inEquations.keySet()) {
            for (Block b : fg.getBlocks()) {
                if (b.getId() == k) {
                    //get the in flows of the block corresponding to the equation. For each in flow add the out equation
                    //corresponding to it to the current in equation's "Equations" attribute (basically set the
                    //equations that have an out flow to the current in equation)
                    ArrayList<Integer> inFlows = b.getInFlows();
                    ArrayList<Equation> outEQ = new ArrayList<Equation>();
                    for(Integer i : inFlows) {
                        outEQ.add(outEquations.get(i));
                    }
                    inEquations.get(k).setEquations(outEQ);
                    break;
                }
            }
        }
    }

    private void setOutflowingEquations(){
        //the out equations are only influenced by their corresponding in equations
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

        //for each block, get the left and the right side variables of the instruction. If they are not numeric, add
        //them to the set of all variables
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
            }
        }
        return set;
    }

    private HashMap<String, ArrayList<Tuple<String, String>>> getKillsMap()
    {
        HashMap<String, ArrayList<Tuple<String, String>>> kills = new HashMap<String, ArrayList<Tuple<String, String>>>();

        //for every block in the flow graph, if the current instruction is of type read, assignment or declaration
        //add this block's id to the left side variable's value in the kills map
        for (Block b : fg.getBlocks()) {
            switch (b.getInstructionType()) {
                case READ:
                case ASSIGNMENT:
                case DECLARATION:
                    String var = b.getLeftVar().getLeft();
                    if (!kills.containsKey(var)) {
                        kills.put(var, new ArrayList<Tuple<String, String>>());
                    }
                    kills.get(var).add(new Tuple<String, String>(var, b.getId().toString()));
                    break;
                default:
            }
        }

        return kills;
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
        }
    }

    private void setSignEquations() {
        initSAEquations();

        //for each block, set the transfer function of the out equation corresponding to this block (the out transfer
        //function of an out equation for sign analysis consists of the instruction itself)
        for(Block b : fg.getBlocks()) {
            SATransferFunction tf = new SATransferFunction();
            tf.setInstructionNode(b.getInstructionNode());
            outEquations.get(b.getId()).setTransferFunction(tf);
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
        return this.inEquations;
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