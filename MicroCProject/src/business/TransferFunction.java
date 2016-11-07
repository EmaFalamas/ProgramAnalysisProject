package business;

import java.util.*;
import models.*;

public abstract class TransferFunction {

    private Map<Integer, Analysis> inEquations;
    private Map<Integer, Analysis> outEquations;
    FlowGraph fg;

    public TransferFunction(FlowGraph _fg) {
        this.fg = _fg;
        this.inEquations = new HashMap<Integer, Analysis>();
        this.outEquations = new HashMap<Integer, Analysis>();
    }

    public void transferFunctions(){

    }

    public Map<Integer, Analysis> getInEquations() {
        return inEquations;
    }

    public Map<Integer, Analysis> getOutEquations() {
        return outEquations;
    }

    public void putInEquation(Integer i, Analysis a) {
        this.inEquations.put(i, a);
    }

    public void putOutEquation(Integer i, Analysis a) {
        this.outEquations.put(i, a);
    }

}