

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import models.*;


public class MicroC {

    static FlowGraph flowGraph;
	static int currentBlockId;
	static Hashtable<Node, Block> nodeBlocks;
	static Hashtable<Integer, ArrayList<Integer>> outFlows;
	static Stack<Block> conditionStack;
	static Stack<Node> whileStack;
	static Block lastBreakBlock = null;

	static boolean searchCondition;
    static boolean searchElse;
    static boolean foundElse;
	static boolean exitSingleIf;
	static boolean exitIfElse;
	static boolean exitWhile;
	static boolean exitBreak;
	static boolean exitStandardBlock;

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

		nodeBlocks = new Hashtable<Node, Block>();
		conditionStack = new Stack<Block>();
		whileStack = new Stack<Node>();
		outFlows = new Hashtable<Integer, ArrayList<Integer>>();
		searchCondition = false;
        searchElse = false;
		exitSingleIf = false;
		exitIfElse = false;
		exitWhile = false;
		exitStandardBlock = false;
		exitBreak = false;
		constructFlowGraph2(abstractSyntaxTree);

	}

	public static void constructFlowGraph2(Node abstractSyntaxTree) {
		System.out.println("_____________ Flow graph construction _____________");
		flowGraph = new FlowGraph();
		currentBlockId = 0;
		constructBlock(abstractSyntaxTree);
		includeFlowGraphOutFlows();
		printFlowGraph();
	}

	public static void constructBlock(Node currentNode) {

		String label = currentNode.getLabel();
		ArrayList<Node> children = currentNode.getChildren();

		//System.out.println("label = " + label + "; exitStandardBlock = " + (new Boolean(exitStandardBlock)).toString());

		switch (label) {
			case "IfNode":
			case "WhileNode":
				searchCondition = true;
				whileStack.push(currentNode);
				break;
			case "IfElseNode":
			    searchCondition = true;
                searchElse = true;
		}

		if (!currentNode.isBlock()) {
			Node newNode = null;
			for (Node c : children) {
				if (!c.isVisited()) {
					newNode = c;
					break;
				}
			}

			while (newNode != null) {
				constructBlock(newNode);
				newNode = null;
				for (Node c : children) {
					if (!c.isVisited()) {
						newNode = c;
						break;
					}
				}
			}

			System.out.println("Exiting node " + label);

			switch (label) {
				case "IfNode":
					exitSingleIf = true;
					exitStandardBlock = false;
					break;
				case "WhileNode":
					exitWhile = true;
					exitStandardBlock = false;
					if (!whileStack.empty()) {
						whileStack.pop();
					}
					ArrayList<Block> whileBlocks = currentNode.getBlocks();
					int numWhileBlocks = whileBlocks.size();
                    System.out.println("numWhileBlocks = " + numWhileBlocks + "; last instruction = " + whileBlocks.get(numWhileBlocks-1).getInstruction());
					if (numWhileBlocks > 1) {
						whileBlocks.get(0).addInFlow(whileBlocks.get(numWhileBlocks-1).getId());
					}
					break;
				case "IfElseNode":
					exitIfElse = true;
					exitStandardBlock = false;
					break;
				case "BreakNode":
					exitBreak = true;
					exitStandardBlock = false;
					lastBreakBlock = nodeBlocks.get(currentNode);
					break;
			}

			currentNode.isVisited(true);
		} else {
			Block b = new Block();
			if (exitStandardBlock) {
				b.addInFlow(currentBlockId);
				exitStandardBlock = false;
			}
			if (exitBreak && exitWhile && lastBreakBlock != null) {
				b.addInFlow(lastBreakBlock.getId());
				lastBreakBlock = null;
				exitBreak = false;
			}
			if (b.getId() == 17) {
                System.out.println("Condition stack is " + (conditionStack.empty()?"":"not") + " empty");
            }
			if ((exitSingleIf || exitWhile) && !conditionStack.empty()) {
				b.addInFlow(conditionStack.pop().getId());
				exitSingleIf = false;
				exitWhile = false;
			}
			if (!exitIfElse && !conditionStack.empty()) {

                exitIfElse = false;
            }
            if (foundElse) {
                b.addInFlow(conditionStack.pop().getId());
                System.out.println("Found else - current block = " + b.getId());
                foundElse = false;
            }
			currentBlockId++;
			b.setId(currentBlockId);
			String instruction = getText(currentNode);
			b.setInstruction(instruction);
			flowGraph.addBlock(b);
			nodeBlocks.put(currentNode, b);
			if (searchCondition) {
				conditionStack.push(b);
				searchCondition = false;
			}
			if (!whileStack.empty()) {
				whileStack.peek().addBlock(b);
				System.out.println("Added block " + b.getId() + "; While blocks = " + whileStack.peek().getBlocks().size());
			}

			if ((!currentNode.getLabel().equals("BreakNode")) || (!currentNode.getLabel().equals("ContinueNode"))
					) {
				exitStandardBlock = true;

			}

			boolean blockFoundInNode = false;
			for (Block b1 : currentNode.getBlocks()) {
				if (b1.equals(b)) {
					blockFoundInNode = true;
					break;
				}
			}
			if (!blockFoundInNode) {
				currentNode.addBlock(b);
			}

			System.out.println("Block created " + b.getId());

			currentNode.isVisited(true);
		}

	}

	public static String getText(Node currentNode) {
		String label = currentNode.getLabel();
		ArrayList<Node> children = currentNode.getChildren();
		String txt = "";
		if (children.size() > 0) {
			for (Node c : children) {
				txt += getText(c);
				switch (c.getLabel()) {
					case "SymbolNode":
						switch (((SymbolNode) c).getOp()) {
							case READ:
							case WRITE:
								txt += " ";
						}
						break;
					case "TypeNode":
						txt += " ";
				}
			}
		} else {
			switch(label) {
				case "BinaryOperatorNode":
					txt += ((BinaryOperatorNode) currentNode).getOpStr(((BinaryOperatorNode) currentNode).getOp());
					break;
				case "ConstantNode":
					txt += Integer.toString(((ConstantNode) currentNode).getNumber());
					break;
				case "SymbolNode":
				    System.out.println("SymbolNode = " + ((SymbolNode) currentNode).getOp().toString());
					txt += ((SymbolNode) currentNode).getOpStr(((SymbolNode) currentNode).getOp());
                    if (searchElse && ((SymbolNode) currentNode).getOp() == SymbolNode.operators.ELSE) {
                        foundElse = true;
                        searchElse = false;
                    }
					break;
				case "UnaryOperatorNode":
					txt += ((UnaryOperatorNode) currentNode).getOpStr(((UnaryOperatorNode) currentNode).getOp());
					break;
				case "VariableNode":
					txt += ((VariableNode) currentNode).getName();
					break;
				default:
					System.out.println("getText2() - DEFAULT. Label = " + label);
					txt += "df";
			}
		}
		return txt;
	}

	public static void includeFlowGraphOutFlows() {
		for (Block b : flowGraph.getBlocks()) {
			ArrayList<Integer> inFlows = b.getInFlows();
			for (Integer i : inFlows) {
				if (!outFlows.containsKey(i)) {
					outFlows.put(i, new ArrayList<Integer>());
				}
				outFlows.get(i).add(b.getId());
			}
		}

		for (Block b : flowGraph.getBlocks()) {
			if (outFlows.containsKey(b.getId())) {
				b.addOutFlows(outFlows.get(b.getId()));
			}
		}

	}

	public static void printFlowGraph() {
        ArrayList<Block> blocks = flowGraph.getBlocks();
        StringBuilder builder;
        for (Block b : blocks) {
            builder = new StringBuilder();
            builder.append("ID = " + b.getId());
            builder.append("; In-flows = ");
			builder.append(b.getInFlows().toString());
            builder.append("; Out-flows = ");
            builder.append(b.getOutFlows().toString());
            builder.append("; Instruction = " + b.getInstruction());
            System.out.println(builder.toString());
        }
    }

}
