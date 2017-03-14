import java.io.FileNotFoundException;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex;

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

        int count = 0;
        while (lex.hasNextToken()) {
            Token token = lex.nextToken();
            System.out.println("Line " + token.getLineNumber() + ": Token " + (count++) + ": " + token.toString());
            System.out.println(token.getSourceLine());
            for (int i = 0; i < token.getStartChar(); i++) {
                System.out.print(" ");
            }
            System.out.println("^");
        }
    }
}
