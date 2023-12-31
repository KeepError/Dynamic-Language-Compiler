package Grammar.Expressions.Unary;

import Symbols.SymbolTable;
import Symbols.SymbolsError;
import Symbols.Values.Value;
import Tokens.Token;
import Grammar.Grammar;
import Grammar.SyntaxError;

import java.util.List;

public abstract class UnaryExpression extends Grammar {
    public UnaryExpression(int startToken, int tokensCount) {
        super(startToken, tokensCount);
    }

    public static UnaryExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        UnaryExpression unaryExpression;
        unaryExpression = UnaryReferenceTypeIndicatorExpression.findNext(tokens, startToken);
        if (unaryExpression != null) return unaryExpression;
        unaryExpression = UnaryReferenceExpression.findNext(tokens, startToken);
        if (unaryExpression != null) return unaryExpression;
        unaryExpression = UnaryPrimaryExpression.findNext(tokens, startToken);
        return unaryExpression;
    }

    public abstract Value evaluate(SymbolTable symbolTable) throws SymbolsError;
}
