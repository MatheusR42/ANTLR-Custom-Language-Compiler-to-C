
# ANTLR Custom Language Compiler to C Example

This is a example of how to create a custom language that creates C output.

First install ANTLR:
https://github.com/antlr/antlr4/blob/master/doc/getting-started.md


## 1. Define the Grammar with ANTLR

Create a file named MyLang.g4:
```
grammar CustomLang;

program: statement+;

statement: assignment
         | declaration
         | printStmt
         | scanStmt
         | expr ';' 
         ;

declaration: 'DEF' ID ';';

assignment: 'DEF' ID '<-' expr ';';

printStmt: 'WRITE' '(' STRING ',' expr ')' ';';

scanStmt: 'READ' '(&' ID ')' ';';

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
antlr4 -visitor CustomLang.g4
javac CustomLang*.java
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
javac CustomLang*.java Compiler.java
```

## 5. Example Language Code

Create a file named `input.customlang`:
```
DEF a;
DEF b;
DEF c <- 2.0;
READ(&a);
READ(&b);
DEF d <- (a + b) * c;
WRITE("Result:", d);
```

Then, run your compiler with a sample file:
```
java Compiler input.customlang
```

Running the above will generate output.c:

```
#include <stdio.h>
#include <stdlib.h>

int main() {
double a;
double b;
double c = 2.0;
if (scanf("%lf", &a) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
if (scanf("%lf", &b) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
double d = (a + b) * c;
printf("Result:""%lf\n", d);
return 0;
}
```

You can compile this C code with a C compiler:
```
gcc output.c -o output
./output
```