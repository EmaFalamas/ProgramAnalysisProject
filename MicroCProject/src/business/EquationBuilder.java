package business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;

import models.*;

public class EquationBuilder {

    private ArrayList<Analysis> inEquations;
    private ArrayList<Analysis> outEquations;
    private FlowGraph fg;
    private Map<String, ArrayList<String>> rdKills;


    public EquationBuilder(FlowGraph _fg) {
        this.inEquations = new ArrayList<Analysis>();
        this.outEquations = new ArrayList<Analysis>();
        this.fg = _fg;
        this.rdKills = new HashMap<String, ArrayList<String>>();
    }

    public void buildReachingDefinitionsEquations() {

        HashSet<String> variables = getAllVariables();
        Block firstBlock = fg.getBlocks().get(0);
        Analysis firstIn = new Analysis();
        Analysis firstOut = new Analysis();



        for (String var : variables) {
            firstIn.addKill(new Tuple(var, "?"));
        }



        for (int i=1; i<fg.getBlocks().size(); i++) {
            Block b = fg.getBlocks().get(i);
            Analysis in = new Analysis();
            Analysis out = new Analysis();

            switch (b.getInstructionType()) {
                case ASSIGNMENT:

                    break;
                case READ:

                    break;
                default:

            }

            inEquations.add(in);
            outEquations.add(out);
        }

    }

    private HashSet<String> getAllVariables() {
        HashSet<String> set = new HashSet<String>();
        for (Block b : fg.getBlocks()) {
            for (String lv : b.getLeftVar()) {
                set.add(lv);
            }
            for (String rv : b.getRightVar()) {
                set.add(rv);
            }
        }
        return set;
    }

    private void getReachingDefinitionsKills(Block b) {
        switch (b.getInstructionType()) {
            case ASSIGNMENT:
            case READ:
            case DECLARATION:
                for (String lv : b.getLeftVar()) {
                    rdKills.get(lv).add((new Integer(b.getId())).toString());
                }
                break;
            default:
        }
    }


}