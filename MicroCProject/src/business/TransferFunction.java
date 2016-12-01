package business;

import java.util.*;
import models.*;

public abstract class TransferFunction {

    private Node instructionNode;

    public Node getInstructionNode() {
        return instructionNode;
    }

    public void setInstructionNode(Node instructionNode) {
        this.instructionNode = instructionNode;
    }
}