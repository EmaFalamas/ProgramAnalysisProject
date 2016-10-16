package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class BreakNode extends StatementNode {

	public BreakNode(Node node) {
		super(node);
		setLabel("BreakNode");
        isBlock(true);
	}

}
