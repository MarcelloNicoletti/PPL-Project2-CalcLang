import java.io.FileNotFoundException;
import java.util.List;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex;
        List<Token> tokens;

        try {
            lex = new Lexer(args[0]);
        } catch (FileNotFoundException ex) {
            System.err.print("Source file '" + args[0] + "' not found.");
            System.exit(-1);
            return;
        } catch (LexingException ex) {
            System.err.println(ex.getMessage());
            System.exit(-2);
            return;
        }

        tokens = lex.getTokens();

        System.out.println("Printing tokens: ");
        int count = 0;
        for (Token token : tokens) {
            System.out.println("Token " + (count++) + ": " + token);
        }

        System.out.println("\nToken locations inline:");

        for (Token token : tokens) {
            System.out.println("\"" + token.getValue() + "\" in line " + token.getLineNumber());
            System.out.println(token.getSourceLine());
            for (int i = 0; i < token.getStartChar(); i++) {
                System.out.print(" ");
            }
            System.out.println("^");
        }
    }
}
