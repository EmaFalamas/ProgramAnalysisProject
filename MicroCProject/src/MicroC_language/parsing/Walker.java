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
		currentNode = (Node) expressionNode.clone();
		System.out.println("Enter Expression Current Node: " + currentNode);
 	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr(MicroCParser.ExprContext ctx) {
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
		currentNode = (Node) expressionNode.clone();
		System.out.println("Enter Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr1(MicroCParser.Expr1Context ctx) {
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
		currentNode = (Node) expressionNode.clone();
		System.out.println("Enter Expression Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr2(MicroCParser.Expr2Context ctx) {
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
		currentNode = (Node) unaryExpressionNode.clone();
		System.out.println("Enter Unary Expression Current Node: " + currentNode);
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
		currentNode = (Node) expressionNode.clone();
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
		Node binaryOperatorNode = new BinaryOperatorNode(currentNode);		
		currentNode.addChild(binaryOperatorNode);
		currentNode = (Node) binaryOperatorNode.clone();
		System.out.println("Enter Binary Operator Current Node: " + currentNode);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitOpr(MicroCParser.OprContext ctx) {
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
		currentNode = (Node) declarationNode.clone();
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
		currentNode = (Node) typeNode.clone();
		System.out.println("Enter Type Current Node: " + currentNode);
 	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitType(MicroCParser.TypeContext ctx) { 
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
		currentNode = (Node) statementNode.clone();
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
	@Override public void enterAssignStmt(MicroCParser.AssignStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAssignStmt(MicroCParser.AssignStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterContinueStmt(MicroCParser.ContinueStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitContinueStmt(MicroCParser.ContinueStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterReadStmt(MicroCParser.ReadStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitReadStmt(MicroCParser.ReadStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBreakStmt(MicroCParser.BreakStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBreakStmt(MicroCParser.BreakStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWriteStmt(MicroCParser.WriteStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWriteStmt(MicroCParser.WriteStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIfelseStmt(MicroCParser.IfelseStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIfelseStmt(MicroCParser.IfelseStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWhileStmt(MicroCParser.WhileStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWhileStmt(MicroCParser.WhileStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBlockStmt(MicroCParser.BlockStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBlockStmt(MicroCParser.BlockStmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterProgram(MicroCParser.ProgramContext ctx) {
		Node programNode = new ProgramNode(null);
		currentNode = (Node) programNode.clone();
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
	@Override public void enterIdentifier(MicroCParser.IdentifierContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIdentifier(MicroCParser.IdentifierContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterInteger(MicroCParser.IntegerContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitInteger(MicroCParser.IntegerContext ctx) { }

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
	@Override public void visitTerminal(TerminalNode node) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitErrorNode(ErrorNode node) { }
 
}
