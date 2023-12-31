package Grammar.Expressions.Expressions;

import Grammar.Expressions.Expression;
import Grammar.Expressions.Relation.RelationExpression;
import Grammar.SyntaxError;
import Symbols.SymbolTable;
import Symbols.SymbolsError;
import Symbols.Values.Value;
import Tokens.OperatorToken;
import Tokens.Token;

import java.util.List;
import java.util.Map;

public class FullExpression extends Expression {
    private final Expression left;
    private final String operator;
    private final RelationExpression right;

    public FullExpression(int startToken, int tokensCount, Expression left, String operator, RelationExpression right) {
        super(startToken, tokensCount);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public static FullExpression findNext(List<Token> tokens, int startToken, Expression left) throws SyntaxError {
        int currentToken = startToken + left.getTokensCount();
        if (currentToken >= tokens.size()) return null;

        Token token = tokens.get(currentToken);
        if (!(token instanceof OperatorToken)) return null;
        String operator = ((OperatorToken) token).getOperator();
        if (!(operator.equals("and") || operator.equals("or") || operator.equals("xor"))) return null;
        currentToken++;
        if (currentToken > tokens.size()) return null;

        RelationExpression right = RelationExpression.findNext(tokens, currentToken);
        if (right == null) return null;
        currentToken += right.getTokensCount();

        return new FullExpression(startToken, currentToken - startToken, left, operator, right);
    }

    @Override
    public void analyse(SymbolTable symbolTable) throws SymbolsError {
        left.analyse(symbolTable);
        right.analyse(symbolTable);
    }

    public Value evaluate(SymbolTable symbolTable) throws SymbolsError {
        Value leftValue = left.evaluate(symbolTable);
        Value rightValue = right.evaluate(symbolTable);
        return switch (operator) {
            case "and" -> leftValue.logicalAnd(rightValue);
            case "or" -> leftValue.logicalOr(rightValue);
            case "xor" -> leftValue.logicalXor(rightValue);
            default -> throw new SymbolsError("Invalid operator: " + operator);
        };
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
