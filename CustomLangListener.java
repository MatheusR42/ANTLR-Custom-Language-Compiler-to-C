// Generated from CustomLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CustomLangParser}.
 */
public interface CustomLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CustomLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CustomLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CustomLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CustomLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CustomLangParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CustomLangParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CustomLangParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CustomLangParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(CustomLangParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(CustomLangParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#scanStmt}.
	 * @param ctx the parse tree
	 */
	void enterScanStmt(CustomLangParser.ScanStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#scanStmt}.
	 * @param ctx the parse tree
	 */
	void exitScanStmt(CustomLangParser.ScanStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CustomLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CustomLangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CustomLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CustomLangParser.ExprContext ctx);
}