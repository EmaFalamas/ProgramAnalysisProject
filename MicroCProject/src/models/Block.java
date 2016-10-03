package models;

import java.util.ArrayList;

/**
 * Created by Ema on 24/9/2016.
 */
public class Block {
    private int id;
    private ArrrayList<int> inFlows;
    private ArrayList<int> outFlows;

    public Block()
    {
        this.inFlows = new ArrayList<int>();
        this.outFlows = new ArrayList<int>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrrayList<int> getInFlows() {
        return inFlows;
    }

    public void setInFlows(ArrrayList<int> inFlows) {
        this.inFlows = inFlows;
    }

    public ArrayList<int> getOutFlows() {
        return outFlows;
    }

    public void setOutFlows(ArrayList<int> outFlows) {
        this.outFlows = outFlows;
    }

    public addInFlow(int id)
    {
        inFlows.add(id);
    }

    public addOutFlow(int id)
    {
        outFlows.add(id);
    }
}
