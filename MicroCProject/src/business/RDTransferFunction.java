package business;

import java.util.*;
import models.*;

public class RDTransferFunction extends TransferFunction {

    private ArrayList<Tuple> kills;
    private ArrayList<Tuple> gens;



    public RDTransferFunction() {
        this.kills = new ArrayList<Tuple>();
        this.gens = new ArrayList<Tuple>();
    }

    public void addKill(Tuple kill) { this.kills.add(kill); }

    public void addKills(ArrayList<Tuple> _kills) { this.kills.addAll(_kills); }

    public ArrayList<Tuple> getKills() { return kills; }

    public void addGen(Tuple gen) { this.gens.add(gen); }

    public ArrayList<Tuple> getGens() { return gens; }

}