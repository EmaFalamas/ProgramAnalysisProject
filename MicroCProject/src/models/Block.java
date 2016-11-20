package models;

import java.util.ArrayList;

/**
 * Created by Ema on 24/9/2016.
 */
public class Block {
    private Integer id;
    private ArrayList<Integer> inFlows;
    private ArrayList<Integer> outFlows;
    private String instruction;
    private String leftVar;
    private ArrayList<String> rightValues;
    private Operand operand;
    private InstructionType it;
    private Node instructionNode;

    public enum InstructionType { ASSIGNMENT, READ, WRITE, CONDITION, CONTINUE, BREAK, DECLARATION }

    public Block()
    {
        this.inFlows = new ArrayList<Integer>();
        this.outFlows = new ArrayList<Integer>();
        //this.leftVar = "";
        this.rightValues = new ArrayList<String>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Integer> getInFlows() {
        return inFlows;
    }

    public Node getInstructionNode() {
        return instructionNode;
    }

    public void setInstructionNode(Node instructionNode) {
        this.instructionNode = instructionNode;
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

    public void setLeftVar(String var) { this.leftVar = var; }

    public void addRightValue(String val) { this.rightValues.add(val); }

    public String getLeftVar() { return leftVar; }

    public ArrayList<String> getRightValues() { return rightValues; }

    public void setInstructionType(InstructionType _it) { this.it = _it; }

    public InstructionType getInstructionType() { return it; }

    public Operand getOperand() { return operand; }

    public void setOperand(Operand operand) { this.operand = operand; }


}
