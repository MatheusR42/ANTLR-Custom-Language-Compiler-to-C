
# ANTLR Custom Language Java Compiler to C Example

This repos contains code and tutorial of an example of how to create a custom language that creates C output using ANTLR and Java

## Example:

`CustomLang_input.customlang`:
```
DEF a;
DEF b;
DEF c <- 2.0;
READ(&a);
READ(&b);
DEF d <- (a + b) * c;
WRITE("Result:", d);
```

Result:
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

## Features

- Declare double variables (`DEF a;`)
- Assignment to double variables (`DEF c <- 2.0;`)
- Read variables (`READ(&a);`)
- Make calculations (`DEF d <- (a + b) * c;`)
- Print (`WRITE("Result:", d);`)


## How to create from zero:

First install ANTLR:
https://github.com/antlr/antlr4/blob/master/doc/getting-started.md


## 1. Define the Grammar with ANTLR

Create a file named `CustomLang.g4`:
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

Create a class `Compiler.java`:

```
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
        String format = "\"%lf\\n\"";
        String expr = visit(ctx.expr());
        cCode.append("printf(").append(message).append(format).append(", ").append(expr).append(");\n");
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
```

## 4. Compile and Run Your Compiler

```
javac CustomLang*.java Compiler.java
```

## 5. Example Language Code

Create a file named `CustomLang_input.customlang`:
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
java Compiler CustomLang_input.customlang
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