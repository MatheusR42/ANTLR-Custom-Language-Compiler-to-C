// Generated from MyLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyLangParser}.
 */
public interface MyLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MyLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MyLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(MyLangParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(MyLangParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(MyLangParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(MyLangParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#scanStmt}.
	 * @param ctx the parse tree
	 */
	void enterScanStmt(MyLangParser.ScanStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#scanStmt}.
	 * @param ctx the parse tree
	 */
	void exitScanStmt(MyLangParser.ScanStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MyLangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MyLangParser.ExprContext ctx);
}