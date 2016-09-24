package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode firstExpressionNode;
    private ExpressionNode secondExpressionNode;
    private BinaryOperatorNode operatorNode;

    public ExpressionNode getFirstExpressionNode() {
        return firstExpressionNode;
    }

    public void setFirstExpressionNode(ExpressionNode firstExpressionNode) {
        this.firstExpressionNode = firstExpressionNode;
    }

    public ExpressionNode getSecondExpressionNode() {
        return secondExpressionNode;
    }

    public void setSecondExpressionNode(ExpressionNode secondExpressionNode) {
        this.secondExpressionNode = secondExpressionNode;
    }

    public BinaryOperatorNode getOperatorNode() {
        return operatorNode;
    }

    public void setOperatorNode(BinaryOperatorNode operatorNode) {
        this.operatorNode = operatorNode;
    }
}
