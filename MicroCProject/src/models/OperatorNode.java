package models;

/**
 * Created by Ema on 19/9/2016.
 */
public class OperatorNode extends Node {
    public String operator;

	public OperatorNode(Node node) {
		super(node);
        setLabel("OperatorNode");
	}

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
