package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class VariableDeclarationNode extends DeclarationNode {
    private TypeNode typeNode;
    private VariableNode variableNode;

	public VariableDeclarationNode(Node node) {
		super(node);
        setLabel("VariableDeclarationNode");
	}

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public void setTypeNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    public VariableNode getVariableNode() {
        return variableNode;
    }

    public void setVariableNode(VariableNode variableNode) {
        this.variableNode = variableNode;
    }
}
