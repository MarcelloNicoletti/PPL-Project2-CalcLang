/*
  lexical phase for
  simple calculator language
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {

    private Scanner input;

    public Lexer (String fileName) {
        try {
            input = new Scanner(new File(fileName));
        } catch (Exception e) {
            System.out.println("Source file: " + fileName + " not found.");
        }
    }

    public ArrayList<Token> scan () throws LexingException {
        ArrayList<Token> tokens = new ArrayList<>();

        int state = 0;
        int lineNum = 0;

        String id = "";
        boolean isId = false;
        double value = 0;
        boolean isValue = false;

        while (input.hasNext()) {
            String s = input.nextLine();
            lineNum++;
            for (int k = 0; k < s.length(); k++) {
                // process next individual char in the file
                char x = s.charAt(k);

                if (state == 0) { // Start token: Strip beginning whitespace before token.
                    if (isWhiteSpace(x)) {
                        // Stripping whitespace
                    } else if (isLetter(x)) {
                        state = 1;
                        id += x;
                        isId = true;
                    } else if (isDigit(x)) {
                        state = 3;
                        value = 10 * value + (x - '0');
                        isValue = true;
                    } else if (isMathSymbol(x)) {
                        state = 2;
                        // Do anything?
                    } else if (isMinusSymbol(x)) {
                        state = 5;
                    } else {
                        throw new LexingException("Unexpected symbol: " + x + " encountered on line" + lineNum + ".");
                    }
                } else if (state == 1) { // Identifier token: add letters and digits to running name `id`
                    if (isLetter(x) || isDigit(x)) {
                        id += x;
                    } else {
                        throw new UnsupportedOperationException(); // TODO: handle end of token
                    }
                } else if (state == 2) { // Math token: math symbol alone
                } else if (state == 3) { // Number token (integer part): Consume digits to running total `value`
                } else if (state == 4) { // Number token (decimal part): Consume digits to running total `value`
                } else if (state == 5) { // Minus state: Decide negative number or math symbol.
                }
            }
        }

        if (isId) {
            tokens.add(new Token(id));
        } else if (isValue) {
            tokens.add(new Token(value));
        } else {
            // Stay empty
        }

        return tokens;
    }

    private boolean isWhiteSpace (char x) {
        return " \t\r\f\n".indexOf(x) != -1;
    }

    private boolean isLetter (char x) {
        return x >= 'a' && x <= 'z' || x >= 'A' && x <= 'Z';
    }

    private boolean isDigit (char x) {
        return x >= '0' && x <= '9';
    }

    private boolean isMathSymbol (char x) {
        return "=+/*()".indexOf(x) != -1;
    }

    private boolean isMinusSymbol (char x) {
        return x == '-';
    }

}