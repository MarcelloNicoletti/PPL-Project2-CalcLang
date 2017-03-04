/*
  lexical phase for
  simple calculator language
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {

    private Scanner input;

    public Lexer (String fileName) throws FileNotFoundException {
        input = new Scanner(new File(fileName));
    }

    public ArrayList<Token> tokenize () throws LexingException {
        ArrayList<Token> tokens = new ArrayList<>();

        int fsaState = 0;
        int lineNum = 0;

        String stringValue = "";
        boolean isStringToken = false;
        BigDecimal numberValue = BigDecimal.ZERO;
        int decimalPlaces = 0;
        boolean isNumberToken = false;

        while (input.hasNext()) {
            String sourceLine = input.nextLine();
            lineNum++;
            for (int charNum = 0; charNum < sourceLine.length(); charNum++) {
                // process next individual char in the file
                char currentChar = sourceLine.charAt(charNum);

                if (fsaState == 0) { // Start token: Strip beginning whitespace before token.
                    if (isWhiteSpace(currentChar)) {
                        // Stripping whitespace
                    } else if (isLetter(currentChar)) {
                        fsaState = 1;
                        stringValue += currentChar;
                        isStringToken = true;
                    } else if (isDigit(currentChar)) {
                        fsaState = 3;
                        numberValue = numberValue.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(currentChar - '0'));
                        isNumberToken = true;
                    } else if (isMathSymbol(currentChar)) {
                        fsaState = 2;
                        // TODO: handle end of token
                        throw new UnsupportedOperationException("Not Implemented Yet.");
                    } else {
                        throw LexingException.newUnexpectedSymbol(currentChar, charNum, lineNum, sourceLine);
                    }
                } else if (fsaState == 1) { // Identifier token: add letters and digits to running name `stringValue`
                    if (isLetter(currentChar) || isDigit(currentChar)) {
                        stringValue += currentChar;
                    } else {
                        // TODO: handle end of token
                        throw new UnsupportedOperationException("Not Implemented Yet.");
                    }
                } else if (fsaState == 3) { // Number token (integer part): Consume digits to running total `numberValue`
                    if (currentChar == '.') {
                        fsaState = 4;
                        decimalPlaces = 1;
                    } else if (isDigit(currentChar)) {
                        numberValue = numberValue.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(currentChar - '0'));
                    } else {
                        // TODO: handle ending token
                        throw new UnsupportedOperationException("Not Implemented Yet.");
                    }
                } else if (fsaState == 4) { // Number token (decimal part): Consume digits to running total `numberValue`
                    if (isDigit(currentChar)) {
                        numberValue = numberValue.add(BigDecimal.valueOf((currentChar - '0') / (10 * decimalPlaces)));
                        decimalPlaces++;
                    } else {
                        // TODO: handle ending token
                        throw new UnsupportedOperationException("Not Implemented Yet.");
                    }
                }
            }

            if (isStringToken) {
                tokens.add(new Token(stringValue, sourceLine, lineNum));
            } else if (isNumberToken) {
                tokens.add(new Token(numberValue, sourceLine, lineNum));
            }
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
        return "=+-/*()".indexOf(x) != -1;
    }

}