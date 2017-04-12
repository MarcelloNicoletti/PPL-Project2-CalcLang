import java.util.HashSet;
import java.util.Set;

public class Parser {
    private final Lexer lex;
    private final Node rootNode;
    private Set<String> identifierSet; // Build a set of identifiers up while parsing. This helps on execution.

    {
        this.identifierSet = new HashSet<>();
    }

    public Parser (Lexer lex) {
        this.lex = lex;
        this.rootNode = new Node();
    }

    public void parseTokens () throws ParsingException {
        rootNode.addChildNode(parseStatements(rootNode));
    }

    private Node parseStatements (Node parent) throws ParsingException {
        Node statements = new Node(NodeType.STATEMENTS, parent);
        statements.addChildNode(parseStatement(statements));
        Token maybeEOF = lex.nextToken();
        if (maybeEOF != null && maybeEOF.getType() != TokenType.EOF) {
            lex.putTokenBack(maybeEOF);
            statements.addChildNode(parseStatements(statements));
        }
        return statements;
    }

    private Node parseStatement (Node parent) throws ParsingException {
        Token token = lex.nextToken();
        Node statement = null;
        if (token.getType() == TokenType.KEY) {
            // TODO: Pull these keywords out to new file.
            switch (token.getValue()) {
                case "newline":
                    statement = new Node(NodeType.NEWLINE, parent);
                    break;
                case "show":
                    statement = new Node(NodeType.SHOW, parent);
                    statement.addChildNode(parseExpression(statement));
                    break;
                case "msg":
                    Token msg = lex.nextToken();
                    if (msg.getType() == TokenType.STR) {
                        statement = new Node(NodeType.MESSAGE, parent);
                    } else {
                        throw ParsingException.newFromToken("Expected string but got " + msg.getType().toString(), msg);
                    }
                    break;
                case "input":
                    Token input = lex.nextToken();
                    if (input.getType() == TokenType.STR) {
                        statement = new Node(NodeType.INPUT, parent);
                        Token ident = lex.nextToken();
                        if (ident.getType() == TokenType.ID) {
                            Node variable = new Node(NodeType.VARIABLE, statement);
                            variable.setData(ident.getValue());
                            identifierSet.add(ident.getValue());
                            statement.addChildNode(variable);
                        } else {
                            throw ParsingException.newFromToken("Expected variable but got " + ident.getType().toString(), ident);
                        }
                    }
                    break;
                default:
                    throw ParsingException.newFromToken("Unknown Keyword " + token.getValue(), token);
            }
        } else if (token.getType() == TokenType.ID) {
            statement = new Node(NodeType.ASSIGNMENT, parent);
            Node variable = new Node(NodeType.VARIABLE, statement);
            variable.setData(token.getValue());
            identifierSet.add(token.getValue());
            statement.addChildNode(variable);
            Token equalSign = lex.nextToken();
            if (equalSign.getValue().equals("=")) {
                statement.addChildNode(parseExpression(statement));
            } else {
                throw ParsingException.newFromToken("Expected assignment", equalSign);
            }
        } else {
            throw ParsingException.newFromToken("Unexpected expression", token);
        }
        return statement;
    }

    private Node parseExpression (Node parent) throws ParsingException {
        Node expression = new Node(NodeType.EXPRESSION, parent);
        expression.addChildNode(parseTerm(expression));
        Token maybeMath = lex.nextToken();
        if (maybeMath.getType() == TokenType.MATH) {
            switch (maybeMath.getValue()) {
                case "+":
                case "-":
                    expression.setData(maybeMath.getValue());
                    expression.addChildNode(parseExpression(expression));
                    break;
                case ")":
                    lex.putTokenBack(maybeMath);
                    break;
                default:
                    throw ParsingException.newFromToken("Expected + or - got " + maybeMath.getValue(), maybeMath);
            }
        } else {
            lex.putTokenBack(maybeMath);
        }
        return expression;
    }

    private Node parseTerm (Node parent) throws ParsingException {
        Node term = new Node(NodeType.TERM, parent);
        term.addChildNode(parseFactor(term));
        Token maybeMath = lex.nextToken();
        if (maybeMath.getType() == TokenType.MATH) {
            switch (maybeMath.getValue()) {
                case "*":
                case "/":
                    term.setData(maybeMath.getValue());
                    term.addChildNode(parseTerm(term));
                    break;
                case ")":
                    lex.putTokenBack(maybeMath);
                    break;
                default:
                    lex.putTokenBack(maybeMath);
            }

        } else {
            lex.putTokenBack(maybeMath);
        }
        return term;
    }

    private Node parseFactor (Node parent) throws ParsingException {
        Node factor = new Node(NodeType.FACTOR, parent);
        Token token = lex.nextToken();
        if (token.getType() == TokenType.NUM) {
            Node number = new Node(NodeType.NUMBER, factor);
            number.setData(token.getValue());
            factor.addChildNode(number);
        } else if (token.getType() == TokenType.ID) {
            Node variable = new Node(NodeType.VARIABLE, parent);
            variable.setData(token.getValue());
            identifierSet.add(token.getValue());
            factor.addChildNode(variable);
        } else if (token.getType() == TokenType.MATH) {
            switch (token.getValue()) {
                case "(":
                    factor.addChildNode(parseExpression(factor));
                    if (lex.peekNextToken().getValue().equals(")")) {
                        lex.nextToken(); // pop it away
                    } else {
                        throw ParsingException.newFromToken("Parenthesis mismatch at ", lex.peekNextToken());
                    }
                    break;
                case "-":
                    factor.setData(token.getValue());
                    factor.addChildNode(parseFactor(factor));
                    break;
                default:
                    throw ParsingException.newFromToken("", token);
            }
        } else if (token.getType() == TokenType.BIFN) {
            Node bifn = new Node(NodeType.BIFN, parent);
            bifn.setData(token.getValue());
            Token paren = lex.nextToken();
            if (paren.getValue().equals("(")) {
                bifn.addChildNode(parseExpression(bifn));
                if (lex.peekNextToken().getValue().equals(")")) {
                    lex.nextToken(); // pop it away
                } else {
                    throw ParsingException.newFromToken("Parenthesis mismatch at ", lex.peekNextToken());
                }
            } else {
                throw ParsingException.newFromToken("Expected function arguments", paren);
            }
            factor.addChildNode(bifn);
        } else {
            throw ParsingException.newFromToken("Expected expression, got something else", token);
        }
        return factor;
    }

    public Node getRootNode () {
        return this.rootNode;
    }

    public Set<String> getIdentifierSet () {
        return this.identifierSet;
    }
}
