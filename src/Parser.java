import java.util.*;

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
        // throw new RuntimeException("Not Implemented yet!");
    }

    public Node getRootNode () {
        return this.rootNode;
    }

    public Set<String> getIdentifierSet () {
        return this.identifierSet;
    }
}
