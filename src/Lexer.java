/*
  lexical phase for
  simple calculator language
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Lexer {

    private static final String[] keywords = {"show", "msg", "newline"};
    private Scanner input;
    private List<Token> tokens;

    public Lexer (String fileName) throws FileNotFoundException, LexingException {
        input = new Scanner(new File(fileName));
        this.tokens = new LinkedList<>();
        this.tokenize();
    }

    private void tokenize () throws LexingException {
        int fsaState = 0;
        int lineNum = 0;
        int tokenStart = 0;

        StringBuilder runningToken = new StringBuilder();
        int decimalPlaces = 0;

        while (input.hasNext()) {
            final String sourceLine = input.nextLine();
            lineNum++;
            for (int charNum = 0; charNum < sourceLine.length(); charNum++) {
                // process next individual char in the file
                char currentChar = sourceLine.charAt(charNum);

                if (fsaState == 0) { // Start token: Strip beginning whitespace before token.
                    if (isWhiteSpace(currentChar)) {
                        // Stripping whitespace
                    } else if (isLetter(currentChar)) {
                        tokenStart = charNum;
                        fsaState = 1;
                        runningToken.append(currentChar);
                    } else if (isDigit(currentChar)) {
                        tokenStart = charNum;
                        fsaState = 3;
                        runningToken.append(currentChar);
                    } else if (isMathSymbol(currentChar)) {
                        // "STATE 2": Math symbol
                        tokens.add(new Token("" + currentChar, lineNum, charNum, sourceLine, TokenType.MATH));
                    } else if (isCommentStart(currentChar)) {
                        break; // for loop for this line. (throw the rest away)
                    } else if (isStringLimiter(currentChar)) {
                        tokenStart = charNum;
                        fsaState = 5;
                    } else {
                        throw LexingException.newUnexpectedSymbol(currentChar, charNum, lineNum, sourceLine);
                    }
                } else if (fsaState == 1) { // Identifier token: add letters and digits to running name `runningToken`
                    if (isLetter(currentChar) || isDigit(currentChar)) {
                        runningToken.append(currentChar);
                    } else {
                        this.tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine,
                                isIn(runningToken.toString(), keywords) ? TokenType.KEY : TokenType.ID));
                        charNum -= 1; // put back this char & return to state 0
                        runningToken = new StringBuilder();
                        // tokenStart = 0;
                        fsaState = 0;
                    }
                } else if (fsaState == 3) { // Number token (integer part): Consume digits to runningToken
                    if (currentChar == '.') {
                        fsaState = 4;
                        runningToken.append(currentChar);
                    } else if (isDigit(currentChar)) {
                        runningToken.append(currentChar);
                    } else {
                        tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.NUM));

                        charNum -= 1; // put back this char & return to state 0
                        runningToken = new StringBuilder();
                        // tokenStart = 0;
                        fsaState = 0;
                    }
                } else if (fsaState == 4) { // Number token (decimal part): Consume digits to runningToken
                    if (isDigit(currentChar)) {
                        decimalPlaces++;
                        runningToken.append(currentChar);
                    } else {
                        if (decimalPlaces == 0) {
                            throw LexingException.newUnexpectedSymbol('.', currentChar - 1, lineNum, sourceLine);
                        } else {
                            tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.NUM));
                            charNum -= 1; // put back this char & return to state 0
                            runningToken = new StringBuilder();
                            decimalPlaces = 0;
                            // tokenStart = 0;
                            fsaState = 0;
                        }
                    }
                } else if (fsaState == 5) {
                    if (isStringLimiter(currentChar)) {
                        tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.STR));
                        runningToken = new StringBuilder();
                        // tokenStart = 0;
                        fsaState = 0;
                    } else {
                        runningToken.append(currentChar);
                    }
                }
            }

            // Finalize tokens that end at the end of the line.
            if (fsaState == 1) {
                tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine,
                        isIn(runningToken.toString(), keywords) ? TokenType.KEY : TokenType.ID));
                runningToken = new StringBuilder();
                // tokenStart = 0;
                fsaState = 0;
            } else if (fsaState == 3) {
                tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.NUM));
                runningToken = new StringBuilder();
                // tokenStart = 0;
                fsaState = 0;
            } else if (fsaState == 4) {
                if (decimalPlaces == 0) {
                    throw LexingException.newUnexpectedSymbol('.', sourceLine.length() - 1, lineNum, sourceLine);
                } else {
                    tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.NUM));
                    runningToken = new StringBuilder();
                    decimalPlaces = 0;
                    // tokenStart = 0;
                    fsaState = 0;
                }
            } else if (fsaState == 5) {
                tokens.add(new Token(runningToken.toString(), lineNum, tokenStart, sourceLine, TokenType.STR));
                runningToken = new StringBuilder();
            }
        }
    }

    private boolean isIn (String str, String[] arr) {
        for (String test : arr) {
            if (str.equals(test)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCommentStart (char x) {
        return x == '#'; // Change this to do change the comment start
    }

    private boolean isStringLimiter (char x) {
        return x == '\'' || x == '"';
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

    public List<Token> getTokens () {
        return tokens;
    }
}