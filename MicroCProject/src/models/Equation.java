package models;

import java.util.*;
import business.*;

public class Equation {

    private ArrayList<Equation> equations;
    private TransferFunction tf;
    private ArrayList<Tuple<String, String>> result;

    public Equation() {
        this.equations = new ArrayList<Equation>();
        this.result = new ArrayList<Tuple<String, String>>();
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

    public ArrayList<Tuple<String, String>> getResult() {
        return result;
    }

    public void setResult(ArrayList<Tuple<String, String>> result) {
        this.result = result;
    }


    public void addResult(Tuple<String, String> t) {
        this.result.add(t);
    }

}