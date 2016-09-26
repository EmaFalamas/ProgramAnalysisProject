package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class UnaryOperatorNode extends Node {

	public UnaryOperatorNode(Node node) {
		super(node);
	}

    public enum operators {
        MINUS,
        NOT
    }
}
