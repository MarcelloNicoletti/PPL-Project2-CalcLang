public class Token {
    String kind, data, line;
    int lineNum;

    public Token (String id, String line, int lineNum) {
        kind = "string";
        data = id;
        this.line = line;
        this.lineNum = lineNum;
    }

    public Token (double value, String line, int lineNum) {
        kind = "number";
        data = Double.toString(value);
        this.line = line;
        this.lineNum = lineNum;
    }

    @Override
    public String toString () {
        return "Kind: " + kind + "token: " + data;
    }
}
