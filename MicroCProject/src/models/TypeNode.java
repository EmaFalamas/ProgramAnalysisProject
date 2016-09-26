package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class TypeNode extends Node {

	public TypeNode(Node node) {
		super(node);
	}

    public enum types {
        INT,
        VOID
    }
}
