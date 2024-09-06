import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileWriter;
import java.io.IOException;

public class Compiler extends CustomLangBaseVisitor<String> {

    private StringBuilder cCode = new StringBuilder();
    
    @Override
    public String visitDeclaration(CustomLangParser.DeclarationContext ctx) {
        String id = ctx.ID().getText();
        cCode.append("double ").append(id).append(";\n");
        return null;
    }
    
    @Override
    public String visitProgram(CustomLangParser.ProgramContext ctx) {
        cCode.append("#include <stdio.h>\n#include <stdlib.h>\n\nint main() {\n");
        visitChildren(ctx);
        cCode.append("return 0;\n}");
        return cCode.toString();
    }

    @Override
    public String visitAssignment(CustomLangParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        String expr = visit(ctx.expr());
        cCode.append("double ").append(id).append(" = ").append(expr).append(";\n");
        return null;
    }

    @Override
    public String visitPrintStmt(CustomLangParser.PrintStmtContext ctx) {
        String message = ctx.STRING().getText();
        if (ctx.expr() != null) {
            String format = "\"%lf\\n\"";
            String expr = visit(ctx.expr());
            cCode.append("printf(").append(message).append(format).append(", ").append(expr).append(");\n");
        } else {
            cCode.append("printf(").append(message).append(");\n");
        }
        return null;
    }

    @Override
    public String visitScanStmt(CustomLangParser.ScanStmtContext ctx) {
        String id = ctx.ID().getText();
        cCode.append("if (scanf(\"%lf\", &").append(id).append(") != 1) {\n");
        cCode.append("    fprintf(stderr, \"Error: Invalid input. Expected a decimal number.\\n\");\n");
        cCode.append("    exit(1);\n");
        cCode.append("}\n");
        return null;
    }

    @Override
    public String visitExpr(CustomLangParser.ExprContext ctx) {
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
        CustomLangLexer lexer = new CustomLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CustomLangParser parser = new CustomLangParser(tokens);
        ParseTree tree = parser.program();

        Compiler compiler = new Compiler();
        String cCode = compiler.visit(tree);
        
        try (FileWriter fileWriter = new FileWriter("output.c")) {
            fileWriter.write(cCode);
        }
    }
}