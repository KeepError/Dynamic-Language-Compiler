package SyntaxAnalysis.Grammar.Expressions.Expressions;

import LexicalAnalysis.Tokens.Token;
import SyntaxAnalysis.Grammar.Expressions.Relation.RelationExpression;
import SyntaxAnalysis.Grammar.Expressions.Expression;
import SyntaxAnalysis.Grammar.SyntaxError;

import java.util.List;
import java.util.Map;

public class MinimalExpression extends Expression {
    private final RelationExpression relationExpression;

    public MinimalExpression(int startToken, int tokensCount, RelationExpression relationExpression) {
        super(startToken, tokensCount);
        this.relationExpression = relationExpression;
    }

    public static MinimalExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        RelationExpression relationExpression = RelationExpression.findNext(tokens, startToken);
        if (relationExpression == null) return null;
        return new MinimalExpression(startToken, relationExpression.getTokensCount(), relationExpression);
    }

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = super.getJSONFields();
        fields.put("relationExpression", relationExpression);
        return fields;
    }
}