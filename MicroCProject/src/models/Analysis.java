package models;

import java.util.ArrayList;

public class Analysis {

    private ArrayList<Analysis> analyses;
    private ArrayList<Tuple> kills;
    private ArrayList<Tuple> gens;



    public Analysis() {
        this.analyses = new ArrayList<Analysis>();
        this.kills = new ArrayList<Tuple>();
        this.gens = new ArrayList<Tuple>();
    }

    public void addAnalysis(Analysis _analysis) { this.analyses.add(_analysis); }

    public ArrayList<Analysis> getAnalyses() { return analyses; }

    public void addKill(Tuple kill) { this.kills.add(kill); }

    public void addKills(ArrayList<Tuple> _kills) { this.kills.addAll(_kills); }

    public ArrayList<Tuple> getKills() { return kills; }

    public void addGen(Tuple gen) { this.gens.add(gen); }

    public ArrayList<Tuple> getGens() { return gens; }

}