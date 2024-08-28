// Generated from MyLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MyLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MyLangParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(MyLangParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#scanStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScanStmt(MyLangParser.ScanStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MyLangParser.ExprContext ctx);
}