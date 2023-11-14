package Grammar.Expressions.Relation;

import Symbols.SymbolTable;
import Symbols.SymbolsError;
import Tokens.OperatorToken;
import Tokens.Token;
import Grammar.Expressions.Factor.FactorExpression;
import Grammar.SyntaxError;

import java.util.List;
import java.util.Map;

public class FullRelationExpression extends RelationExpression {
    private final FactorExpression left;
    private final String operator;
    private final FactorExpression right;

    public FullRelationExpression(int startToken, int tokensCount, FactorExpression left, String operator, FactorExpression right) {
        super(startToken, tokensCount);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public static FullRelationExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        int currentToken = startToken;
        FactorExpression left = FactorExpression.findNext(tokens, currentToken);
        if (left == null) return null;
        currentToken += left.getTokensCount();
        if (currentToken >= tokens.size()) return null;

        Token token = tokens.get(currentToken);
        if (!(token instanceof OperatorToken)) return null;
        String operator = ((OperatorToken) token).getOperator();
        if (!(operator.equals("<") || operator.equals("<=") || operator.equals(">") || operator.equals(">=") || operator.equals("=") || operator.equals("/="))) return null;
        currentToken++;
        if (currentToken > tokens.size()) return null;

        FactorExpression right = FactorExpression.findNext(tokens, currentToken);
        if (right == null) return null;
        currentToken += right.getTokensCount();

        return new FullRelationExpression(startToken, currentToken - startToken, left, operator, right);
    }

    @Override
    public void analyse(SymbolTable symbolTable) throws SymbolsError {
        left.analyse(symbolTable);
        right.analyse(symbolTable);
    }

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = super.getJSONFields();
        fields.put("left", left);
        fields.put("operator", operator);
        fields.put("right", right);
        return fields;
    }
}