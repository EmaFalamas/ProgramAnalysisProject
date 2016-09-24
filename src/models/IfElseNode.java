package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class IfElseNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode ifBody;
    private StatementNode elseBody;

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public StatementNode getIfBody() {
        return ifBody;
    }

    public void setIfBody(StatementNode ifBody) {
        this.ifBody = ifBody;
    }

    public StatementNode getElseBody() {
        return elseBody;
    }

    public void setElseBody(StatementNode elseBody) {
        this.elseBody = elseBody;
    }
}
