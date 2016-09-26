package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class DeclarationAndStatementNode extends StatementNode {
    private DeclarationNode declarationNode;
    private StatementNode statementNode;

	public DeclarationAndStatementNode(Node node) {
		super(node);
	}

    public DeclarationNode getDeclarationNode() {
        return declarationNode;
    }

    public void setDeclarationNode(DeclarationNode declarationNode) {
        this.declarationNode = declarationNode;
    }

    public StatementNode getStatementNode() {
        return statementNode;
    }

    public void setStatementNode(StatementNode statementNode) {
        this.statementNode = statementNode;
    }
}
