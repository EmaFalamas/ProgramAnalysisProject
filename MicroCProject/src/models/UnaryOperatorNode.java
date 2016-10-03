package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class UnaryOperatorNode extends Node {

    private operators op;

	public UnaryOperatorNode(Node node) {
		super(node);
        setLabel("UnaryOperatorNode");
	}

    public enum operators {
        MINUS,
        NOT
    }

    public models.UnaryOperatorNode.operators getOp() {
        return op;
    }

    public void setOp(models.UnaryOperatorNode.operators op) {
        this.op = op;
    }

    public void setOp(String str) {
        switch(str) {
            case "-":
                this.op = operators.MINUS;
                break;
            case "!":
                this.op = operators.NOT;
                break;
        }

    }
}
