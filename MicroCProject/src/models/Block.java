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
    private ArrayList<String> leftVar;
    private ArrayList<String> rightVar;
    private InstructionType it;

    public enum InstructionType { ASSIGNMENT, READ, WRITE, CONDITION, CONTINUE, BREAK, DECLARATION }

    public Block()
    {
        this.inFlows = new ArrayList<Integer>();
        this.outFlows = new ArrayList<Integer>();
        this.leftVar = new ArrayList<String>();
        this.rightVar = new ArrayList<String>();
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

    public void addOutFlows(ArrayList<Integer> ids) { outFlows.addAll(ids); }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void addLeftVar(String var) { this.leftVar.add(var); }

    public void addRightVar(String var) { this.rightVar.add(var); }

    public ArrayList<String> getLeftVar() { return leftVar; }

    public ArrayList<String> getRightVar() { return rightVar; }

    public void setInstructionType(InstructionType _it) { this.it = _it; }

    public InstructionType getInstructionType() { return it; }

}
