package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class SymbolNode extends Node {

    private operators op;

    public SymbolNode(Node node) {
        super(node);
        setLabel("SymbolNode");
    }

    public enum operators {
        LBRACKET,
        RBRACKET,
        ASSIGN,
        SEMI,
        AND,
        OR,
        GT,
        GE,
        LT,
        LE,
        EQ,
        NEQ,
        PLUS,
        MINUS,
        MUL,
        DIV,
        NOT,
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        COLON,
        IF,
        ELSE,
        WHILE,
        CONTINUE,
        BREAK,
        WRITE,
        READ,
        INT,
        VOID
    }

    public operators getOp() {
        return op;
    }

    public void setOp(operators op) {
        this.op = op;
    }

    public void setOp(String str) {
        switch(str) {
            case "[":
                this.op = operators.LBRACKET;
                break;
            case "]":
                this.op = operators.RBRACKET;
                break;
            case "=":
                this.op = operators.ASSIGN;
                break;
            case ";":
                this.op = operators.SEMI;
                break;
            case "&":
                this.op = operators.AND;
                break;
            case "|":
                this.op = operators.OR;
                break;
            case ">":
                this.op = operators.GT;
                break;
            case ">=":
                this.op = operators.GE;
                break;
            case "<":
                this.op = operators.LT;
                break;
            case "<=":
                this.op = operators.LE;
                break;
            case "==":
                this.op = operators.EQ;
                break;
            case "!=":
                this.op = operators.NEQ;
                break;
            case "+":
                this.op = operators.PLUS;
                break;
            case "-":
                this.op = operators.MINUS;
                break;
            case "*":
                this.op = operators.MUL;
                break;
            case "/":
                this.op = operators.DIV;
                break;
            case "!":
                this.op = operators.NOT;
                break;
            case "(":
                this.op = operators.LPAREN;
                break;
            case ")":
                this.op = operators.RPAREN;
                break;
            case "{":
                this.op = operators.LBRACE;
                break;
            case "}":
                this.op = operators.RBRACE;
                break;
            case ":":
                this.op = operators.COLON;
                break;
            case "if":
                this.op = operators.IF;
                break;
            case "else":
                this.op = operators.ELSE;
                break;
            case "while":
                this.op = operators.WHILE;
                break;
            case "continue":
                this.op = operators.CONTINUE;
                break;
            case "break":
                this.op = operators.BREAK;
                break;
            case "write":
                this.op = operators.WRITE;
                break;
            case "read":
                this.op = operators.READ;
                break;
            case "int":
                this.op = operators.INT;
                break;
            case "void":
                this.op = operators.VOID;
                break;
        }
    }

    public String getOpStr(operators op) {
        String str = null;
        switch(op) {
            case LBRACKET:
                str = "[";
                break;
            case RBRACKET:
                str = "]";
                break;
            case ASSIGN:
                str = "=";
                break;
            case SEMI:
                str = ";";
                break;
            case AND:
                str = "&";
                break;
            case OR:
                str = "|";
                break;
            case GT:
                str = ">";
                break;
            case GE:
                str = ">=";
                break;
            case LT:
                str = "<";
                break;
            case LE:
                str = "<=";
                break;
            case EQ:
                str = "==";
                break;
            case NEQ:
                str = "!=";
                break;
            case PLUS:
                str = "+";
                break;
            case MINUS:
                str = "-";
                break;
            case MUL:
                str = "*";
                break;
            case DIV:
                str = "/";
                break;
            case NOT:
                str = "!";
                break;
            case LPAREN:
                str = "(";
                break;
            case RPAREN:
                str = ")";
                break;
            case LBRACE:
                str = "{";
                break;
            case RBRACE:
                str = "}";
                break;
            case COLON:
                str = ":";
                break;
            case IF:
                str = "if";
                break;
            case ELSE:
                str = "else";
                break;
            case WHILE:
                str = "while";
                break;
            case CONTINUE:
                str = "continue";
                break;
            case BREAK:
                str = "break";
                break;
            case WRITE:
                str = "write";
                break;
            case READ:
                str = "read";
                break;
            case INT:
                str = "int";
                break;
            case VOID:
                str = "void";
                break;
        }
        return str;
    }
}
