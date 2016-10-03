package models;

import java.util.ArrayList;

/**
 * Created by Ema on 24/9/2016.
 */
public class ProgramNode extends Node {
    private ArrayList<DeclarationNode> declarations;
    private ArrayList<StatementNode> statements;

	public ProgramNode(Node node) {
		super(node);
        setLabel("ProgramNode");
	}

    public ArrayList<DeclarationNode> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(ArrayList<DeclarationNode> declarations) {
        this.declarations = declarations;
    }

    public ArrayList<StatementNode> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementNode> statements) {
        this.statements = statements;
    }
}
