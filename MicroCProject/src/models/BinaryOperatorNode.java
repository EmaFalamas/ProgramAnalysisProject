package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class BinaryOperatorNode extends Node {

    private operators op;

	public BinaryOperatorNode(Node node) {
		super(node);
        setLabel("BinaryOperatorNode");
	}

    public enum operators {
        PLUS,
        MINUS,
        TIMES,
        DIV,
        LT,
        GT,
        LTE,
        GTE,
        EQ,
        NEQ,
        AND,
        OR
    }

    public models.BinaryOperatorNode.operators getOp() {
        return op;
    }

    public void setOp(models.BinaryOperatorNode.operators op) {
        this.op = op;
    }

    public void setOp(String str) {
        switch(str) {
            case "+":
                this.op = operators.PLUS;
                break;
            case "-":
                this.op = operators.MINUS;
                break;
            case "*":
                this.op = operators.TIMES;
                break;
            case "/":
                this.op = operators.DIV;
                break;
            case "<":
                this.op = op.LT;
                break;
            case ">":
                this.op = operators.GT;
                break;
            case "<=":
                this.op = operators.LTE;
                break;
            case ">=":
                this.op = operators.GTE;
                break;
            case "==":
                this.op = operators.EQ;
                break;
            case "!=":
                this.op = operators.NEQ;
                break;
            case "&":
                this.op = operators.AND;
                break;
            case "|":
                this.op = operators.OR;
                break;
        }

    }
}
