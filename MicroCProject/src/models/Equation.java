package models;

import java.util.*;
import business.*;

public class Equation {

    private ArrayList<Equation> equations;
    private TransferFunction tf;


    public Equation() {
        this.equations = new ArrayList<Equation>();
    }

    public ArrayList<Equation> getEquations() {
        return equations;
    }

    public void addEquation(Equation eq) {
        this.equations.add(eq);
    }

    public TransferFunction getTransferFunction() {
        return tf;
    }

    public void setEquations(ArrayList<Equation> eqs) { this.equations = eqs; }

    public void setTransferFunction(TransferFunction _tf) {
        this.tf = _tf;
    }



}