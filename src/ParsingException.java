public class ParsingException extends CalcLangException {
    public ParsingException () {
        super();
    }

    public ParsingException (String message) {
        super(message);
    }

    public static ParsingException newFromToken (String message, Token token) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append("\n");
        sb.append("from token '").append(token.getValue()).append("' on line ").append(token.getLineNumber()).append(" at carrot:\n");
        sb.append(token.getLineNumber()).append(": ").append(token.getSourceLine()).append("\n");
        final int leadingSpaceSize = token.getStartChar() + Integer.toString(token.getLineNumber()).length() + 2;
        for (int i = 0; i < leadingSpaceSize; i++) {
            sb.append(" ");
        }
        sb.append("^");
        return new ParsingException(sb.toString());
    }

    // TODO: Add factory methods here.

//    public static ParsingException factoryBlank () {
//        // Do special stuff here.
//    }
}
