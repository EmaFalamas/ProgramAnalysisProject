package business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import models.*;

public class EquationBuilder {

    private FlowGraph fg;

    public enum AnalysisType

    {
        REACHING_DEFINITIONS, SIGN_ANALYSIS
    }

    public EquationBuilder(FlowGraph _fg) {
        this.fg = _fg;
    }

    public Equation buildEquation(AnalysisType at) {
        Equation eq = new Equation();
        TransferFunction tf;

        switch (at) {

            case REACHING_DEFINITIONS:
                tf = new RDTransferFunction(fg);
                tf.transferFunctions();
                //buildReachingDefinitionsEquations();
                eq.setTransferFunction(tf);
                break;
            case SIGN_ANALYSIS:

                break;
            default:

        }
        return eq;
    }

}