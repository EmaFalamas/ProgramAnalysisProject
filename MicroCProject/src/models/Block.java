package models;

import java.util.ArrayList;

/**
 * Created by Ema on 24/9/2016.
 */
public class Block {
    private int id;
    private ArrayList<Integer> inFlows;
    private ArrayList<Integer> outFlows;
    private String instruction;

    public Block()
    {
        this.inFlows = new ArrayList<Integer>();
        this.outFlows = new ArrayList<Integer>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getInFlows() {
        return inFlows;
    }

    public void setInFlows(ArrayList<Integer> inFlows) {
        this.inFlows = inFlows;
    }

    public ArrayList<Integer> getOutFlows() {
        return outFlows;
    }

    public void setOutFlows(ArrayList<Integer> outFlows) {
        this.outFlows = outFlows;
    }

    public void addInFlow(int id)
    {
        inFlows.add(id);
    }

    public void addOutFlow(int id)
    {
        outFlows.add(id);
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
