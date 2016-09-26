package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class BinaryOperatorNode extends Node {

	public BinaryOperatorNode(Node node) {
		super(node);
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
}
