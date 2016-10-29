package models;

import java.util.ArrayList;

public class Analysis {

    private ArrayList<Analysis> analyses;
    private ArrayList<String> kills;
    private ArrayList<String> gens;

    public Analysis() {
        this.analyses = new ArrayList<Analysis>();
        this.kills = new ArrayList<String>();
        this.gens = new ArrayList<String>();
    }

    public void addAnalysis(Analysis _analysis) { this.analyses.add(_analysis); }

    public ArrayList<Analysis> getAnalyses() { return analyses; }

    public void addKill(String kill) { this.kills.add(kill); }

    public ArrayList<String> getKills() { return kills; }

    public void addGen(String gen) { this.gens.add(gen); }

    public ArrayList<String> getGens() { return gens; }

}