import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex;
        ArrayList<Token> tokens;

        try {
            lex = new Lexer(args[0]);
        } catch (FileNotFoundException ex) {
            System.err.print("Source file '" + args[0] + "' not found.");
            System.exit(-1);
            return;
        }

        try {
            tokens = lex.tokenize();
        } catch (LexingException ex) {
            System.err.println(ex.getMessage());
            System.exit(-2);
            return;
        }

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
