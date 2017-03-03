import java.util.ArrayList;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex = new Lexer(args[0]);
        ArrayList<Token> tokens;
        try {
            tokens = lex.scan();
        } catch (LexingException ex){
            System.err.println(ex.getMessage());
            System.exit(-2);
            return;
        }

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
