package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class UnaryExpressionNode extends ExpressionNode {
    private UnaryOperatorNode operatorNode;
    private ExpressionNode expressionNode;

	public UnaryExpressionNode(Node node) {
		super(node);
        setLabel("UnaryExpressionNode");
        isBlock(true);
	}

    public UnaryOperatorNode getOperatorNode() {
        return operatorNode;
    }

    public void setOperatorNode(UnaryOperatorNode operatorNode) {
        this.operatorNode = operatorNode;
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    public void setExpressionNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }
}
