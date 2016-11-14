package business;

import java.util.*;
import models.*;

public class RDTransferFunction extends TransferFunction {

    private ArrayList<Tuple> kills;
    private Tuple gen;

    public RDTransferFunction() {
        this.kills = new ArrayList<Tuple>();
        //this.gen = new Tuple();
    }

    public void addKill(Tuple kill) { this.kills.add(kill); }

    public void addKills(ArrayList<Tuple> _kills) { this.kills.addAll(_kills); }

    public ArrayList<Tuple> getKills() { return kills; }

    public void setGen(Tuple gen) { this.gen = gen; }

    public Tuple getGen() { return gen; }

}