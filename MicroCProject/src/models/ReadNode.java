package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class ReadNode extends StatementNode {
    private ExpressionNode expressionNode;

	public ReadNode(Node node) {
		super(node);
        setLabel("ReadNode");
        isBlock(true);
	}

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    public void setExpressionNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }
}
