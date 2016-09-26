package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class VariableNode extends ExpressionNode {
    private String name;

	public VariableNode(Node node) {
		super(node);
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
