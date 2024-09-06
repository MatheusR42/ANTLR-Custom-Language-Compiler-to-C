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

printStmt: 'WRITE' '(' STRING (',' expr)? ')' ';';

scanStmt: 'READ' '(' ID ')' ';';

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