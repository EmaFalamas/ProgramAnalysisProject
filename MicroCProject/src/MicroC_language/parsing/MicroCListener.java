// Generated from src/MicroC_language/parsing/MicroC.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MicroCParser}.
 */
public interface MicroCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MicroCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MicroCParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MicroCParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterExpr1(MicroCParser.Expr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitExpr1(MicroCParser.Expr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(MicroCParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(MicroCParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#exprnegate}.
	 * @param ctx the parse tree
	 */
	void enterExprnegate(MicroCParser.ExprnegateContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#exprnegate}.
	 * @param ctx the parse tree
	 */
	void exitExprnegate(MicroCParser.ExprnegateContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterExpr3(MicroCParser.Expr3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitExpr3(MicroCParser.Expr3Context ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#opr}.
	 * @param ctx the parse tree
	 */
	void enterOpr(MicroCParser.OprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#opr}.
	 * @param ctx the parse tree
	 */
	void exitOpr(MicroCParser.OprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(MicroCParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(MicroCParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MicroCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MicroCParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MicroCParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MicroCParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(MicroCParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(MicroCParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(MicroCParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(MicroCParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#readStmt}.
	 * @param ctx the parse tree
	 */
	void enterReadStmt(MicroCParser.ReadStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#readStmt}.
	 * @param ctx the parse tree
	 */
	void exitReadStmt(MicroCParser.ReadStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MicroCParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MicroCParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#writeStmt}.
	 * @param ctx the parse tree
	 */
	void enterWriteStmt(MicroCParser.WriteStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#writeStmt}.
	 * @param ctx the parse tree
	 */
	void exitWriteStmt(MicroCParser.WriteStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#ifelseStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfelseStmt(MicroCParser.IfelseStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#ifelseStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfelseStmt(MicroCParser.IfelseStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MicroCParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MicroCParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MicroCParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MicroCParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MicroCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MicroCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(MicroCParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(MicroCParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroCParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(MicroCParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroCParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(MicroCParser.IntegerContext ctx);
}