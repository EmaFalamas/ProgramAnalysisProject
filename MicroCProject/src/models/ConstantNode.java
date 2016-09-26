package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class ConstantNode extends ExpressionNode {
    private int number;

	public ConstantNode(Node node) {
		super(node);
	}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
