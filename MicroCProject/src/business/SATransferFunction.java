package business;

import java.util.*;
import models.*;

public class SATransferFunction extends TransferFunction {

    private Node instructionNode;

    public SATransferFunction() {

    }

    public Node getInstructionNode() {
        return instructionNode;
    }

    public void setInstructionNode(Node instructionNode) {
        this.instructionNode = instructionNode;
    }
}