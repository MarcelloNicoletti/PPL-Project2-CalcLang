import java.util.ArrayList;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lex = new Lexer(args[0]);
        ArrayList<Token> list = lex.scan();
        for (int k = 0; k < list.size(); k++) {
            System.out.println(list.get(k));
        }
    }
}
