package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class AssignmentNode extends StatementNode {
    private ExpressionNode leftSideNode;
    private ExpressionNode rightSideNode;

	public AssignmentNode(Node node) {
		super(node);
        setLabel("AssignmentNode");
	}

    public ExpressionNode getLeftSideNode() {
        return leftSideNode;
    }

    public void setLeftSideNode(ExpressionNode leftSideNode) {
        this.leftSideNode = leftSideNode;
    }

    public ExpressionNode getRightSideNode() {
        return rightSideNode;
    }

    public void setRightSideNode(ExpressionNode rightSideNode) {
        this.rightSideNode = rightSideNode;
    }
}
