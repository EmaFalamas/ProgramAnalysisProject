package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class WriteNode extends StatementNode {
    private ExpressionNode expressionNode;

	public WriteNode(Node node) {
		super(node);
	}

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    public void setExpressionNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }
}
