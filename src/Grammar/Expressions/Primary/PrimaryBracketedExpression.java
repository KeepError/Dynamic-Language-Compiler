package Grammar.Expressions.Primary;

import Grammar.Expressions.Expression;
import Grammar.SyntaxError;
import Symbols.SymbolTable;
import Symbols.SymbolsError;
import Symbols.Values.Value;
import Tokens.SeparatorToken;
import Tokens.Token;

import java.util.List;
import java.util.Map;

public class PrimaryBracketedExpression extends PrimaryExpression {
    private final Expression expression;

    public PrimaryBracketedExpression(int startToken, int tokensCount, Expression expression) {
        super(startToken, tokensCount);
        this.expression = expression;
    }

    public static PrimaryBracketedExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        int currentToken = startToken;
        Token token = tokens.get(currentToken);
        if (!(token instanceof SeparatorToken && ((SeparatorToken) token).getSeparator().equals("("))) {
            return null;
        }
        currentToken++;
        if (currentToken >= tokens.size()) return null;

        Expression expression = Expression.findNext(tokens, currentToken);
        if (expression == null) return null;
        currentToken += expression.getTokensCount();
        if (currentToken > tokens.size()) return null;

        token = tokens.get(currentToken);
        if (!(token instanceof SeparatorToken && ((SeparatorToken) token).getSeparator().equals(")"))) {
            return null;
        }
        currentToken++;

        return new PrimaryBracketedExpression(startToken, currentToken - startToken, expression);
    }

    @Override
    public Value evaluate(SymbolTable symbolTable) throws SymbolsError {
        return expression.evaluate(symbolTable);
    }

    @Override
    public void analyse(SymbolTable symbolTable) throws SymbolsError {
        expression.analyse(symbolTable);
    }

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = super.getJSONFields();
        fields.put("expression", expression);
        return fields;
    }
}
