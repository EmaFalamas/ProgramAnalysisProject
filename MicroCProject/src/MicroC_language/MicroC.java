

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import models.*;


public class MicroC {

	public static void main(String args[]) throws Exception {
		if (args.length == 0) {
			System.out.println("Error: No program specified.");
			return;
		}
        MicroCLexer lex = new MicroCLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        MicroCParser parser = new MicroCParser(tokens);
		ParseTree tree = parser.program(); //This command parses the program.

	    ParseTreeWalker walker = new ParseTreeWalker();
		Walker w = new Walker();
		walker.walk(w, tree);
		
		Node abstractSyntaxTree = w.currentNode;
	}

	public void constructFlowGraph(Node abstractSyntaxTree)
	{
		FlowGraph flowGraph = new FlowGraph();

	}

	public Block constructBlock(Node currentNode, int refBlock, int latestId) {
		Block b = null;
		switch(currentNode.getLabel()) {
			case "AssignmentNode":
				b = new Block();
				b.setId(latestId+1);
				b.addInFlow(refBlock);
				Node leftNode = currentNode.getChildren().get(0);
				Node rightNode = currentNode.getChildren().get(1);

				String leftNodeText = getText(leftNode);
				String rightNodeText = getText(rightNode);

				b.setInstruction(leftNodeText + " = " + rightNodeText);
				break;
			case "":

				break;

			default:

				break;
		}
		return b;
	}

	public String getText(Node n) {
		String text = null;
		if (n.getChildren().size() > 0) {
			return getText(n.getChildren().get(0));
		} else {
			switch(n.getLabel()){
				case "ConstantNode":
					text = Integer.toString(((ConstantNode) n).getNumber());
					break;
				case "":

					break;
				default:

					break;
			}
		}
		return text;
	}

}
