public class Token {
    String kind, data;

    public Token (String id) {
        kind = "string";
        data = id;
    }

    public Token (double value) {
        kind = "number";
        data = Double.toString(value);
    }

    @Override
    public String toString() {
        return "Kind: " + kind + "token: " + data;
    }
}
