package business;

import java.util.*;
import models.*;

public abstract class TransferFunction {

    Map<Integer, Analysis> inEquations;
    Map<Integer, Analysis> outEquations;
    FlowGraph fg;

    public TransferFunction(FlowGraph _fg) {
        this.fg = _fg;
        this.inEquations = new HashMap<Integer, Analysis>();
        this.outEquations = new HashMap<Integer, Analysis>();
    }

    public void transferFunctions(){

    }

}