package business;

import java.util.*;
import models.*;

public class RDTransferFunction extends TransferFunction {

    private ArrayList<Tuple<String, String>> kills;
    private Tuple<String, String> gen;

    public RDTransferFunction() {
        this.kills = new ArrayList<Tuple<String, String>>();
        //this.gen = new Tuple<String, String>();
    }

    public void addKill(Tuple<String, String> kill) { this.kills.add(kill); }

    public void addKills(ArrayList<Tuple<String, String>> _kills) { this.kills.addAll(_kills); }

    public ArrayList<Tuple<String, String>> getKills() { return kills; }

    public void setGen(Tuple<String, String> gen) { this.gen = gen; }

    public Tuple<String, String> getGen() { return gen; }

}