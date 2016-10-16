import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.io.PrintWriter;
import java.io.File;
import models.*;

/**
 * Created by Ema on 26/9/2016.
 */
public class Walker extends MicroCBaseListener {
	public Node currentNode;

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr(MicroCParser.ExprContext ctx) {
		Node expressionNode = new ExpressionNode(currentNode);		
		currentNode.addChild(expressionNode);
		currentNode = (Node) expressionNode;
		System.out.println("Enter Expression Current Node: " + currentNode);
 	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr(MicroCParser.ExprContext ctx) {
		boolean hasBinaryOperator = false;
		for (Node c : currentNode.getChildren()) {
			if (c.getLabel().equals("BinaryOperatorNode")) {
				hasBinaryOperator = true;
				break;
			}
		}
		if (hasBinaryOperator) {
			currentNode.setLabel("BinaryExpressionNode");
			currentNode.isBlock(true);
		}
		currentNode = currentNode.getParent();
		System.out.println("Exit Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr1(MicroCParser.Expr1Context ctx) {
		Node expressionNode = new ExpressionNode(currentNode);		
		currentNode.addChild(expressionNode);
		currentNode = (Node) expressionNode;
		System.out.println("Enter Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr1(MicroCParser.Expr1Context ctx) {
		boolean hasBinaryOperator = false;
		for (Node c : currentNode.getChildren()) {
			if (c.getLabel().equals("BinaryOperatorNode")) {
				hasBinaryOperator = true;
				break;
			}
		}
		if (hasBinaryOperator) {
			currentNode.setLabel("BinaryExpressionNode");
			currentNode.isBlock(true);
		}
		currentNode = currentNode.getParent();
		System.out.println("Exit Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr2(MicroCParser.Expr2Context ctx) {
		Node expressionNode = new ExpressionNode(currentNode);		
		currentNode.addChild(expressionNode);
		currentNode = (Node) expressionNode;
		System.out.println("Enter Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr2(MicroCParser.Expr2Context ctx) {
		boolean hasBinaryOperator = false;
		for (Node c : currentNode.getChildren()) {
			if (c.getLabel().equals("BinaryOperatorNode")) {
				hasBinaryOperator = true;
				break;
			}
		}
		if (hasBinaryOperator) {
			currentNode.setLabel("BinaryExpressionNode");
			currentNode.isBlock(true);
		}
		currentNode = currentNode.getParent();
		System.out.println("Exit Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExprnegate(MicroCParser.ExprnegateContext ctx) {
		Node unaryExpressionNode = new UnaryExpressionNode(currentNode);
		currentNode.addChild(unaryExpressionNode);
		currentNode = (Node) unaryExpressionNode;
		System.out.println("Enter Unary Expression Current Node: " + currentNode);

		System.out.println("Enter Unary operator Current Node: " + currentNode);
		Node unaryOperatorNode = new UnaryOperatorNode(currentNode);
		((UnaryOperatorNode)unaryOperatorNode).setOp(ctx.getChild(0).getText());
		currentNode.addChild(unaryOperatorNode);
		System.out.println("Exit Unary operator Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExprnegate(MicroCParser.ExprnegateContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Unary Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr3(MicroCParser.Expr3Context ctx) {
		Node expressionNode = new ExpressionNode(currentNode);		
		currentNode.addChild(expressionNode);
		currentNode = (Node) expressionNode;
		System.out.println("Enter Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr3(MicroCParser.Expr3Context ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterOpr(MicroCParser.OprContext ctx) {
		if(currentNode.getParent() instanceof UnaryExpressionNode) {
			Node unaryOperatorNode = new UnaryOperatorNode(currentNode);
			currentNode.addChild(unaryOperatorNode);
			currentNode = (Node) unaryOperatorNode;
			System.out.println("Enter unary Operator Current Node: " + currentNode);
		}
		else {
			Node binaryOperatorNode = new BinaryOperatorNode(currentNode);
			currentNode.addChild(binaryOperatorNode);
			currentNode = (Node) binaryOperatorNode;
			System.out.println("Enter Binary Operator Current Node: " + currentNode);
		}

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitOpr(MicroCParser.OprContext ctx) {
		if(currentNode instanceof UnaryOperatorNode)
			((UnaryOperatorNode) currentNode).setOp(ctx.getChild(0).getText());
		else
			((BinaryOperatorNode) currentNode).setOp(ctx.getChild(0).getText());
		currentNode = currentNode.getParent();
		System.out.println("Exit Binary Operator Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterDecl(MicroCParser.DeclContext ctx) {
		Node declarationNode = new DeclarationNode(currentNode);		
		currentNode.addChild(declarationNode);
		currentNode = (Node) declarationNode;
		System.out.println("Enter Declaration Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitDecl(MicroCParser.DeclContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Declaration Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterType(MicroCParser.TypeContext ctx) {
		Node typeNode = new TypeNode(currentNode);
		currentNode.addChild(typeNode);
		currentNode = (Node) typeNode;
		System.out.println("Enter Type Current Node: " + currentNode);
 	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitType(MicroCParser.TypeContext ctx) {
		System.out.println("---WALKER--- exitType. NodeType = " + ctx.getChild(0).getText());
		((TypeNode) currentNode).setNodeType(ctx.getChild(0).getText());
		currentNode = currentNode.getParent();
		System.out.println("Exit Type Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterStmt(MicroCParser.StmtContext ctx) { 
		Node statementNode = new StatementNode(currentNode);		
		currentNode.addChild(statementNode);
		currentNode = (Node) statementNode;
		System.out.println("Enter Statement Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitStmt(MicroCParser.StmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Statement Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAssignStmt(MicroCParser.AssignStmtContext ctx) {
		Node assignmentNode = new AssignmentNode(currentNode);
		currentNode.addChild(assignmentNode);
		currentNode = (Node) assignmentNode;
		System.out.println("Enter Assignment Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAssignStmt(MicroCParser.AssignStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Assignment Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterContinueStmt(MicroCParser.ContinueStmtContext ctx) {
		Node continueNode = new ContinueNode(currentNode);
		currentNode.addChild(continueNode);
		currentNode = (Node) continueNode;
		System.out.println("Enter Continue Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitContinueStmt(MicroCParser.ContinueStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Continue Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterReadStmt(MicroCParser.ReadStmtContext ctx) {
		Node readNode = new ReadNode(currentNode);
		currentNode.addChild(readNode);
		currentNode = (Node) readNode;
		System.out.println("Enter Read Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitReadStmt(MicroCParser.ReadStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Read Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBreakStmt(MicroCParser.BreakStmtContext ctx) {
		Node breakNode = new BreakNode(currentNode);
		currentNode.addChild(breakNode);
		currentNode = (Node) breakNode;
		System.out.println("Enter Break Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBreakStmt(MicroCParser.BreakStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Break Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWriteStmt(MicroCParser.WriteStmtContext ctx) {
		Node writeNode = new WriteNode(currentNode);
		currentNode.addChild(writeNode);
		currentNode = (Node) writeNode;
		System.out.println("Enter Write Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWriteStmt(MicroCParser.WriteStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Write Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIfelseStmt(MicroCParser.IfelseStmtContext ctx) {
		Node ifElseNode = new IfElseNode(currentNode);
		currentNode.addChild(ifElseNode);
		currentNode = (Node) ifElseNode;
		System.out.println("Enter IfElse Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIfelseStmt(MicroCParser.IfelseStmtContext ctx) {
		boolean hasElse = false;
		for (Node c : currentNode.getChildren()) {
			if (c.getLabel().equals("SymbolNode") && ((SymbolNode) c).getOp() == SymbolNode.operators.ELSE) {
				hasElse = true;
				break;
			}
		}
		if (!hasElse) {
			currentNode.setLabel("IfNode");
		}
		currentNode = currentNode.getParent();
		System.out.println("Exit IfElse Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWhileStmt(MicroCParser.WhileStmtContext ctx) {
		Node whileNode = new WhileNode(currentNode);
		currentNode.addChild(whileNode);
		currentNode = (Node) whileNode;
		System.out.println("Enter While Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWhileStmt(MicroCParser.WhileStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit While Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBlockStmt(MicroCParser.BlockStmtContext ctx) {
		Node blockNode = new BlockNode(currentNode);
		currentNode.addChild(blockNode);
		currentNode = (Node) blockNode;
		System.out.println("Enter Block Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBlockStmt(MicroCParser.BlockStmtContext ctx) {
		currentNode = currentNode.getParent();
		System.out.println("Exit Block Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterProgram(MicroCParser.ProgramContext ctx) {
		Node programNode = new ProgramNode(null);
		currentNode = (Node) programNode;
		System.out.println("Enter Program Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitProgram(MicroCParser.ProgramContext ctx) {
		// We shouldn't do anything here, as the current node holds the entire
		// abstract syntax tree

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIdentifier(MicroCParser.IdentifierContext ctx) {
		Node variableNode = new VariableNode(currentNode);
		currentNode.addChild(variableNode);
		currentNode = (Node) variableNode;
		System.out.println("Enter Variable Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIdentifier(MicroCParser.IdentifierContext ctx) {
		((VariableNode) currentNode).setName(ctx.getChild(0).getText());
		currentNode = currentNode.getParent();
		System.out.println("Exit Variable Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterInteger(MicroCParser.IntegerContext ctx) {
		Node constantNode = new ConstantNode(currentNode);
		currentNode.addChild(constantNode);
		currentNode = (Node) constantNode;
		System.out.println("Enter Constant Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitInteger(MicroCParser.IntegerContext ctx) {
		((ConstantNode) currentNode).setNumber(Integer.parseInt(ctx.getChild(0).getText()));
		currentNode = currentNode.getParent();
		System.out.println("Exit Constant Current Node: " + currentNode);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitTerminal(TerminalNode node) {

		//System.out.println("-------------- TerminalNODE: " + node.getText());

		String text = node.getText();
		if (text.equals("+") || text.equals("-") || text.equals("|") || text.equals("*") || text.equals("/") || text.equals("&")) {
			Node operatorNode = new BinaryOperatorNode(currentNode);
			((BinaryOperatorNode) operatorNode).setOp(text);
			currentNode.addChild(operatorNode);
		}

		if (text.equals("[") || text.equals("]") || text.equals("=") || text.equals(";")
				|| text.equals("break") || text.equals("continue") || text.equals("read") || text.equals("write")
				|| text.equals("int") || text.equals("void") || text.equals("else")) {
			Node symbolNode = new SymbolNode(currentNode);
			((SymbolNode) symbolNode).setOp(text);
			currentNode.addChild(symbolNode);
		}

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitErrorNode(ErrorNode node) { }
 
}
