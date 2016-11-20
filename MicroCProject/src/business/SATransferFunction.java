package business;

import java.util.*;
import models.*;
import util.*;

public class SATransferFunction extends TransferFunction {

    private Node instructionNode;

    public SATransferFunction() {
        SAUtils.initializeTransferFunctions();
    }

    public Node getInstructionNode() {
        return instructionNode;
    }

    public void setInstructionNode(Node instructionNode) {
        this.instructionNode = instructionNode;
    }



}