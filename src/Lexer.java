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

    public ArrayList<Token> scan () {
        ArrayList<Token> list = new ArrayList<Token>();

        int state = 0;
        String id = "";
        double value = 0;

        while (input.hasNext()) {
            String s = input.nextLine();
            for (int k = 0; k < s.length(); k++) {
                // process next individual char in the file
                char x = s.charAt(k);

                if (state == 0) {
                    if (isWhiteSpace(x)) {
                        // stay in state 0
                    } else if (isLetter(x)) {
                        state = 1;
                        id += x;
                    } else if (isDigit(x)) {
                        state = 3;
                        value = 10 * value + x - '0';
                    }
                } else if (state == 1) {
                } else if (state == 2) {
                } else if (state == 3) {
                } else if (state == 4) {
                }
            }
        }

        // TODO: Incomplete
        return null;
    }

    private boolean isWhiteSpace (char x) {
        String whitespace = " \t\r\f\n";
        return whitespace.indexOf(x) != -1;
    }

    private boolean isLetter (char x) {
        boolean isLower = x >= 'a' && x <= 'z';
        boolean isUpper = x >= 'A' && x <= 'Z';

        return isLower || isUpper;
    }

    private boolean isDigit (char x) {
        return x >= '0' && x <= '9';
    }

}