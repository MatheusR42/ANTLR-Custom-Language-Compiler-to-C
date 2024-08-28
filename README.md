
# ANTLR Custom Language Compiler to C Example

This is a example of how to create a custom language that creates C output.

First install ANTLR:
https://github.com/antlr/antlr4/blob/master/doc/getting-started.md


## 1. Define the Grammar with ANTLR

Create a file named MyLang.g4:
```
grammar MyLang;

program: statement+;

statement: assignment
         | printStmt
         | scanStmt
         | expr ';' 
         ;

assignment: ID '=' expr ';';

printStmt: 'printf' '(' STRING ',' expr ')' ';';

scanStmt: 'scanf' '(' STRING ',' '&' ID ')' ';';

expr: expr op=('*'|'/') expr
    | expr op=('+'|'-') expr
    | '(' expr ')'
    | NUMBER
    | ID
    ;

ID: [a-zA-Z_][a-zA-Z_0-9]*;
NUMBER: [0-9]+('.'[0-9]+)?;
STRING: '"' .*? '"';
WS: [ \t\r\n]+ -> skip;
```

## 2. Generate the Java Code with ANTLR

```
antlr4 -visitor MyLang.g4
javac MyLang*.java
```

## 3. Create the Java Compiler Class

Create a class MyLangCompiler.java:

```
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
```

## 4. Compile and Run Your Compiler

```
javac MyLang*.java MyLangCompiler.java
```

## 5. Example Language Code

Create a file named input.mylang:
```
a = 5.5 + 4.5;
b = a * 2;
printf("Result: %f\n", b);
scanf("%lf", &a);
```

Then, run your compiler with a sample file:
```
java MyLangCompiler input.mylang
```

Running the above will generate output.c:

```
#include <stdio.h>

int main() {
double a = 5.5 + 4.5;
double b = a * 2;
printf("Result: %f\n", b);
scanf("%lf", &a);
return 0;
}
```

You can compile this C code with a C compiler:
```
gcc output.c -o output
./output
```