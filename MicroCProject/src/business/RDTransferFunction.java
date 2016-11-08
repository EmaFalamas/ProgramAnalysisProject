package business;

import java.util.*;
import models.*;

public class RDTransferFunction extends TransferFunction {

    private Map<String, ArrayList<Tuple>> rdKills;
    private ArrayList<Tuple> kills;
    private ArrayList<Tuple> gens;



    public RDTransferFunction(FlowGraph _fg) {
        super(_fg);
        this.rdKills = new HashMap<String, ArrayList<Tuple>>();
        this.kills = new ArrayList<Tuple>();
        this.gens = new ArrayList<Tuple>();
    }

    public void addKill(Tuple kill) { this.kills.add(kill); }

    public void addKills(ArrayList<Tuple> _kills) { this.kills.addAll(_kills); }

    public ArrayList<Tuple> getKills() { return kills; }

    public void addGen(Tuple gen) { this.gens.add(gen); }

    public ArrayList<Tuple> getGens() { return gens; }

    public Map<String, ArrayList<Tuple>> getRdKills() {
        return rdKills;
    }

    public void setRdKills(Map<String, ArrayList<Tuple>> rdKills) {
        this.rdKills = rdKills;
    }
}