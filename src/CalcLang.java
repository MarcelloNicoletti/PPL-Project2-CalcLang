import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CalcLang {
    public static void main (String[] args) {
        Lexer lexer;
        Parser parser;
        Scanner stdin = new Scanner(System.in);

        // Initialize Lexer.
        try {
            lexer = new Lexer(new File(args[0]));
        } catch (FileNotFoundException ex) {
            System.err.print("Source file '" + args[0] + "' not found.");
            System.exit(-1);
            return;
        }

        // Try lexing test file
        try {
            lexer.tokenize();
        } catch (LexingException ex) {
            System.err.println(ex.getMessage());
            System.exit(-2);
            return;
        }


//         ===== TEMPORARY DEBUGGING =====

        int count = 0;
        while (lexer.hasNextToken()) {
            Token token = lexer.nextToken();
            System.out.println("Line " + token.getLineNumber() + ": Token " + (count++) + ": " + token.toString());
            System.out.println(token.getSourceLine());
            for (int i = 0; i < token.getStartChar(); i++) {
                System.out.print(" ");
            }
            System.out.println("^");
        }
        lexer.resetAllTokens();

        // Initialize Parser
        parser = new Parser(lexer);

        // Try parsing tokens.
        try {
            parser.parseTokens();
        } catch (ParsingException ex) {
            System.err.println(ex.getMessage());
            System.exit(-2);
            return;
        }

        // Get root node and initialize executor.
        Node root = parser.getRootNode();
        Executor executor = new Executor(parser.getIdentifierSet(), stdin);

        displayTree(root, 2, 0);

        // Execute parse tree
        executor.execute(root);
    }

    private static void displayTree (Node node, int indentSize, int indentLevel) {
        for (int i = 0; i < indentSize * indentLevel; i++) {
            System.out.print(" ");
        }
        System.out.println("[" + node.getNodeType() + ": " + node.getData() + "]");
        for (Node child : node.getChildren()) {
            displayTree(child, indentSize, indentLevel + 1);
        }
    }
}
