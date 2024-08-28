// Generated from CustomLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CustomLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CustomLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CustomLangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CustomLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CustomLangParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(CustomLangParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(CustomLangParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#scanStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScanStmt(CustomLangParser.ScanStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CustomLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CustomLangParser.ExprContext ctx);
}