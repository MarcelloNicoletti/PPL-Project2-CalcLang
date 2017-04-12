public enum TokenType {
    KEY,
    ID,
    NUM,
    MATH,
    STR,
    BIFN,
    EOF,
    ;

    @Override
    public String toString() {
        switch (this) {
            case KEY:
                return "keyword";
            case ID:
                return "identifier";
            case NUM:
                return "number";
            case MATH:
                return "math symbol";
            case STR:
                return "string literal";
            case BIFN:
                return "built in function";
            case EOF:
                return "end of file";
            default:
                return "how did this happen?";
        }
    }
}
