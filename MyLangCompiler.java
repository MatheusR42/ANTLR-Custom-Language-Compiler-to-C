import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileWriter;
import java.io.IOException;

public class MyLangCompiler extends MyLangBaseVisitor<String> {

    private StringBuilder cCode = new StringBuilder();
    
    @Override
    public String visitProgram(MyLangParser.ProgramContext ctx) {
        cCode.append("#include <stdio.h>\n\nint main() {\n");
        visitChildren(ctx);
        cCode.append("return 0;\n}");
        return cCode.toString();
    }

    @Override
    public String visitAssignment(MyLangParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        String expr = visit(ctx.expr());
        cCode.append("double ").append(id).append(" = ").append(expr).append(";\n");
        return null;
    }

    @Override
    public String visitPrintStmt(MyLangParser.PrintStmtContext ctx) {
        String format = ctx.STRING().getText();
        String expr = visit(ctx.expr());
        cCode.append("printf(").append(format).append(", ").append(expr).append(");\n");
        return null;
    }

    @Override
    public String visitScanStmt(MyLangParser.ScanStmtContext ctx) {
        String format = ctx.STRING().getText();
        String id = ctx.ID().getText();
        cCode.append("scanf(").append(format).append(", &").append(id).append(");\n");
        return null;
    }

    @Override
    public String visitExpr(MyLangParser.ExprContext ctx) {
        if (ctx.op != null) {
            String left = visit(ctx.expr(0));
            String right = visit(ctx.expr(1));
            return left + " " + ctx.op.getText() + " " + right;
        } else if (ctx.NUMBER() != null) {
            return ctx.NUMBER().getText();
        } else if (ctx.ID() != null) {
            return ctx.ID().getText();
        } else {
            return "(" + visit(ctx.expr(0)) + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromFileName(args[0]);
        MyLangLexer lexer = new MyLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.program();

        MyLangCompiler compiler = new MyLangCompiler();
        String cCode = compiler.visit(tree);
        
        try (FileWriter fileWriter = new FileWriter("output.c")) {
            fileWriter.write(cCode);
        }
    }
}
