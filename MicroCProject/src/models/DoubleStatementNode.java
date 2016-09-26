package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class DoubleStatementNode extends StatementNode {
    private StatementNode firstStatementNode;
    private StatementNode secondStatementNode;

	public DoubleStatementNode(Node node) {
		super(node);
	}

    public StatementNode getFirstStatementNode() {
        return firstStatementNode;
    }

    public void setFirstStatementNode(StatementNode firstStatementNode) {
        this.firstStatementNode = firstStatementNode;
    }

    public StatementNode getSecondStatementNode() {
        return secondStatementNode;
    }

    public void setSecondStatementNode(StatementNode secondStatementNode) {
        this.secondStatementNode = secondStatementNode;
    }
}
