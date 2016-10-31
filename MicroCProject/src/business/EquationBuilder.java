package business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import models.*;

public class EquationBuilder {

    private Map<Integer, Analysis> inEquations;
    private Map<Integer, Analysis> outEquations;
    private FlowGraph fg;
    private Map<String, ArrayList<Tuple>> rdKills;



    public EquationBuilder(FlowGraph _fg) {
        this.inEquations = new HashMap<Integer, Analysis>();
        this.outEquations = new HashMap<Integer, Analysis>();
        this.fg = _fg;
        this.rdKills = new HashMap<String, ArrayList<Tuple>>();
    }

    public void buildReachingDefinitionsEquations() {

        HashSet<String> variables = getAllVariables();

        for (int i=0; i<fg.getBlocks().size(); i++) {
            Block b = fg.getBlocks().get(i);
            Analysis in = new Analysis();
            Analysis out = new Analysis();

            if (i == 0) {
                for (String var : variables) {
                    in.addGen(new Tuple(var, "?"));
                }
                inEquations.put(b.getId(), in);
            }

            switch (b.getInstructionType()) {
                case ASSIGNMENT:
                case READ:
                case DECLARATION:
                    for (String var : b.getLeftVar()) {
                        out.addKills(rdKills.get(var));
                        out.addGen(new Tuple(var, b.getId().toString()));
                    }
                    break;
                default:

            }

            inEquations.put(b.getId(), in);
            outEquations.put(b.getId(), out);
        }

        printKillsAndGens();

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

            switch (b.getInstructionType()) {
                case READ:
                case ASSIGNMENT:
                case DECLARATION:
                    for (String var : b.getLeftVar()) {
                        if (!rdKills.containsKey(var)) {
                            rdKills.put(var, new ArrayList<Tuple>());
                        }
                        rdKills.get(var).add(new Tuple(var, b.getId().toString()));
                    }
                    break;
                default:

            }

        }
        return set;
    }

    private void printKillsAndGens() {
        System.out.println("____ ENTRY ____");
        Iterator it1 = inEquations.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry) it1.next();
            Analysis a = (Analysis) pair.getValue();
            System.out.print(((Integer) pair.getKey()).toString() + "-gen: ");
            for (Tuple t : a.getGens()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple t : a.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            it1.remove();
        }
        System.out.println("____ EXIT ____");
        Iterator it2 = outEquations.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            Analysis a = (Analysis) pair.getValue();
            System.out.print(((Integer) pair.getKey()).toString() + "-gen: ");
            for (Tuple t : a.getGens()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            System.out.print(((Integer) pair.getKey()).toString() + "-kills: ");
            for (Tuple t : a.getKills()) {
                System.out.print(t.toString() + "; ");
            }
            System.out.println();
            it2.remove();
        }
    }

}