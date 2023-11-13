package SyntaxAnalysis.Grammar.Expressions.Unary;

import LexicalAnalysis.Tokens.Token;
import SyntaxAnalysis.Grammar.Expressions.Expression;
import SyntaxAnalysis.Grammar.SyntaxError;

import java.util.List;

public class UnaryExpression extends Expression {
    public UnaryExpression(int startToken, int tokensCount) {
        super(startToken, tokensCount);
    }

    public static UnaryExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        UnaryExpression unaryExpression;
        unaryExpression = UnaryReferenceExpression.findNext(tokens, startToken);
        if (unaryExpression != null) return unaryExpression;
        unaryExpression = UnaryPrimaryExpression.findNext(tokens, startToken);
        return unaryExpression;
    }
}