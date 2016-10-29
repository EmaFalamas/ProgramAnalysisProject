

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
    static Stack<Block> ifLastBlockStack;
	static Block lastBreakContinueBlock = null;
    static Block lastBlockCreated = null;

	static boolean searchCondition;
    static boolean searchElse;
    static boolean foundElse;
	static boolean exitSingleIf;
	static boolean exitIfElse;
	static boolean exitWhile;
	static boolean exitBreak;
    static boolean exitContinue;
	static boolean exitStandardBlock;
    static boolean exitIfBlockIfElse;
	static boolean assignmentFound;

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
        ifLastBlockStack = new Stack<Block>();
		outFlows = new Hashtable<Integer, ArrayList<Integer>>();
		searchCondition = false;
        searchElse = false;
		exitSingleIf = false;
		exitIfElse = false;
		exitWhile = false;
		exitStandardBlock = false;
		exitBreak = false;
        exitContinue = false;
        exitIfBlockIfElse = false;
		assignmentFound = false;
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

		switch (label) {
			case "IfNode":
			case "WhileNode":
				searchCondition = true;
				whileStack.push(currentNode);
				break;
			case "IfElseNode":
			    searchCondition = true;
                searchElse = true;
                break;
            case "SymbolNode":
                if (searchElse && ((SymbolNode) currentNode).getOp() == SymbolNode.operators.ELSE) {
                    searchElse = false;
                    foundElse = true;
                    exitStandardBlock = false;
                    exitIfBlockIfElse = true;
                    ifLastBlockStack.push(lastBlockCreated);
                }
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
                    if (numWhileBlocks > 1) {
						whileBlocks.get(0).addInFlow(whileBlocks.get(numWhileBlocks-1).getId());
					}
					break;
				case "IfElseNode":
					exitIfElse = true;
					exitStandardBlock = false;
					break;
			}

            currentNode.isVisited(true);
		} else {
			Block b = new Block();
			assignmentFound = false;
			if (exitStandardBlock) {
				b.addInFlow(currentBlockId);
				exitStandardBlock = false;
			}
            if (exitBreak && exitWhile && lastBreakContinueBlock != null) {
				b.addInFlow(lastBreakContinueBlock.getId());
				lastBreakContinueBlock = null;
				exitBreak = false;
			}
			if (exitContinue && lastBreakContinueBlock != null && !whileStack.empty()) {
                whileStack.peek().getBlocks().get(0).addInFlow(lastBreakContinueBlock.getId());
                lastBreakContinueBlock = null;
                exitContinue = false;
            }
			if ((exitSingleIf || exitWhile) && !conditionStack.empty()) {
				b.addInFlow(conditionStack.pop().getId());
				exitSingleIf = false;
				exitWhile = false;
			}
            if (exitIfElse && exitIfBlockIfElse && !ifLastBlockStack.empty()) {
                b.addInFlow(ifLastBlockStack.pop().getId());
                exitIfBlockIfElse = false;
            }
			currentBlockId++;
			b.setId(currentBlockId);
            if (foundElse) {
                b.addInFlow(conditionStack.pop().getId());
                foundElse = false;
            }
			String instruction = getText(b, currentNode);
			b.setInstruction(instruction);
			flowGraph.addBlock(b);
            lastBlockCreated = b;
			nodeBlocks.put(currentNode, b);
			if (searchCondition) {
				conditionStack.push(b);
				searchCondition = false;
			}
			if (!whileStack.empty()) {
				whileStack.peek().addBlock(b);
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

            switch (label) {
                case "BreakNode":
                    exitBreak = true;
                    exitStandardBlock = false;
                    lastBreakContinueBlock = lastBlockCreated;
					b.setInstructionType(Block.InstructionType.BREAK);
                    break;
                case "ContinueNode":
                    exitContinue = true;
                    exitStandardBlock = false;
                    lastBreakContinueBlock = lastBlockCreated;
					b.setInstructionType(Block.InstructionType.CONTINUE);
					break;
				case "AssignmentNode":
					b.setInstructionType(Block.InstructionType.ASSIGNMENT);
					break;
				case "ReadNode":
					b.setInstructionType(Block.InstructionType.READ);
					break;
				case "WriteNode":
					b.setInstructionType(Block.InstructionType.WRITE);
					break;
				case "DeclarationNode":
					b.setInstructionType(Block.InstructionType.DECLARATION);
					break;
				case "UnaryExpressionNode":
				case "BinaryExpressionNode":
					b.setInstructionType(Block.InstructionType.CONDITION);
					break;
                default:
                    exitStandardBlock = true;
            }

			currentNode.isVisited(true);
		}

	}

	public static String getText(Block b, Node currentNode) {
		String label = currentNode.getLabel();
		ArrayList<Node> children = currentNode.getChildren();
		String txt = "";
		if (children.size() > 0) {
			for (Node c : children) {
				txt += getText(b, c);
				switch (c.getLabel()) {
					case "SymbolNode":
						switch (((SymbolNode) c).getOp()) {
							case READ:
							case WRITE:
								txt += " ";
								break;
							case ASSIGN:
								assignmentFound = true;
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
					txt += ((SymbolNode) currentNode).getOpStr(((SymbolNode) currentNode).getOp());
					break;
				case "UnaryOperatorNode":
					txt += ((UnaryOperatorNode) currentNode).getOpStr(((UnaryOperatorNode) currentNode).getOp());
					break;
				case "VariableNode":
					txt += ((VariableNode) currentNode).getName();
					if (!assignmentFound) {
						b.addLeftVar(txt);
					} else {
						b.addRightVar(txt);
					}
					break;
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
			builder.append("; Left variables = " + b.getLeftVar().toString());
			builder.append("; Right Variables = " + b.getRightVar().toString());
			builder.append("; Instruction Type = " + b.getInstructionType());
            System.out.println(builder.toString());
        }
    }

}
