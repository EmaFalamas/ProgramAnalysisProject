package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class TypeNode extends Node {

    private types nodeType;

    public TypeNode(Node node) {
        super(node);
	}

    public enum types {
        INT,
        VOID
    }

    public models.TypeNode.types getNodeType() {
        return nodeType;
    }

    public void setNodeType(models.TypeNode.types nodeType) {
        this.nodeType = nodeType;
    }

    public void setNodeType(String str) {
        switch(str) {
            case "int":
                this.nodeType = models.TypeNode.types.INT;
                break;
            case "void":
                this.nodeType = models.TypeNode.types.VOID;
                break;
        }
    }

}
