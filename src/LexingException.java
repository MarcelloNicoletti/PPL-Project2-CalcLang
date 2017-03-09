public class LexingException extends CalcLangException {
    public LexingException () {
        super();
    }

    public LexingException (String message) {
        super(message);
    }

    public static LexingException newUnexpectedSymbol (char symbol, int charNum, int lineNum, String line) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected symbol '").append(symbol).append("' on line ").append(lineNum).append(" at carrot:\n");
        sb.append(lineNum).append(": ").append(line).append("\n");
        final int leadingSpaceSize = charNum + Integer.toString(lineNum).length() + 2;
        for (int i = 0; i < leadingSpaceSize; i++) {
            sb.append(" ");
        }
        sb.append("^");
        return new LexingException(sb.toString());
    }
}
