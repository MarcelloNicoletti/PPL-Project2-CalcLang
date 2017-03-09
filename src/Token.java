/**
 * Immutable class for storing tokens from the lexing phase
 */
public class Token {
    private final TokenType type;
    private final String value, sourceLine;
    private final int lineNumber, startChar;

    public Token (String id, int lineNumber, int startChar, String line, TokenType type) {
        value = id;
        this.type = type;
        this.sourceLine = line;
        this.startChar = startChar;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString () {
        return getType() + ": \"" + getValue() + "\"";
    }

    public String getValue () {
        return value;
    }

    public String getSourceLine () {
        return sourceLine;
    }

    public TokenType getType () {
        return type;
    }

    public int getLineNumber () {
        return lineNumber;
    }

    public int getStartChar () {
        return startChar;
    }
}
