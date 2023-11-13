package SyntaxAnalysis.Grammar.Expressions.Factor;

import LexicalAnalysis.Tokens.Token;
import SyntaxAnalysis.Grammar.Expressions.Term.TermExpression;
import SyntaxAnalysis.Grammar.SyntaxError;

import java.util.List;
import java.util.Map;

public class MinimalFactorExpression extends FactorExpression {
    private final TermExpression termExpression;

    public MinimalFactorExpression(int startToken, int tokensCount, TermExpression termExpression) {
        super(startToken, tokensCount);
        this.termExpression = termExpression;
    }

    public static MinimalFactorExpression findNext(List<Token> tokens, int startToken) throws SyntaxError {
        TermExpression termExpression = TermExpression.findNext(tokens, startToken);
        if (termExpression == null) return null;
        return new MinimalFactorExpression(startToken, termExpression.getTokensCount(), termExpression);
    }

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = super.getJSONFields();
        fields.put("termExpression", termExpression);
        return fields;
    }
}
