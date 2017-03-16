import java.util.*;

public class Parser {
    private final Lexer lex;
    private final Node rootNode;

    public Parser (Lexer lex) {
        this.lex = lex;
        this.rootNode = new Node();
    }

    public void parseTokens () throws ParsingException {
        // throw new RuntimeException("Not Implemented yet!");
    }

    public Node getRootNode () {
        return rootNode;
    }
}
