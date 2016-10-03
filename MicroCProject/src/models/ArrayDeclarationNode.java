package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class ArrayDeclarationNode extends DeclarationNode {
    private TypeNode typeNode;
    private ArrayNode arrayNode;

	public ArrayDeclarationNode(Node node) {
		super(node);
        setLabel("ArrayDeclarationNode");
	}

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public void setTypeNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    public ArrayNode getArrayNode() {
        return arrayNode;
    }

    public void setArrayNode(ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }
}
