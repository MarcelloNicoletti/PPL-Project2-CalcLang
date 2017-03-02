import java.util.ArrayList;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex = new Lexer(args[0]);
        ArrayList<Token> tokens = lex.scan();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
