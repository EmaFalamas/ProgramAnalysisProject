package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class WhileNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode body;

	public WhileNode(Node node) {
		super(node);
	}

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public StatementNode getBody() {
        return body;
    }

    public void setBody(StatementNode body) {
        this.body = body;
    }
}
