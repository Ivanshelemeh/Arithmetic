package org.itstep;

import java.util.Deque;

public final class LexicalParser {
    public static Deque<Token> lexicalParserRec(CharSequence expression, Deque<Token> tokens)  throws UnknownLexicalSequenceException {
        expression = skipSpace(expression);
        if (0 == expression.length()) return tokens;
        Token tok = parseNextToken(expression);
        tokens.offer(tok);
        return lexicalParserRec(expression.subSequence(tok.data.length(),expression.length()),tokens);
    }

    private static CharSequence skipSpace(CharSequence expression) {
        int pos = 0, len = expression.length();
        while (pos < len && Character.isWhitespace(expression.charAt(pos))) ++pos;
        return expression.subSequence(pos,len);
    }

    private static Token numberChecker(CharSequence expression) {
        if (Character.isDigit(expression.charAt(0))) {
            int pos = 0; int len = expression.length();
            while (++pos < len && Character.isDigit(expression.charAt(pos)));
            return new Token(TokenType.NUMBER,expression.subSequence(0,pos));
        }
        return null;
    }

    private static Token symbolChecker(CharSequence expression) {
        switch(expression.charAt(0)) {
            case '+':
                return new Token(TokenType.PLS,expression.subSequence(0,1));
            case '-':
                return new Token(TokenType.MNS,expression.subSequence(0,1));
            case '*':
                return new Token(TokenType.MUL,expression.subSequence(0,1));
            case '/':
                return new Token(TokenType.DIV,expression.subSequence(0,1));

            case '(':
                return new Token(TokenType.OPEN,expression.subSequence(0,1));
            case ')':
                return new Token(TokenType.CLOSE,expression.subSequence(0,1));


            default:
                return null;
        }
    }

    private static Token parseNextToken(CharSequence expression) throws UnknownLexicalSequenceException {
        Token tok = null;
        if (null != (tok = numberChecker(expression))) return tok;
        if (null != (tok = symbolChecker(expression))) return tok;
        throw new UnknownLexicalSequenceException(expression);
    }

    private LexicalParser() { }
}
