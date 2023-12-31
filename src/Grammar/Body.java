package Grammar;

import Grammar.Statements.DeclarationStatement;
import Grammar.Statements.Statement;
import Symbols.SymbolTable;
import Symbols.SymbolsError;
import Symbols.Values.Value;
import Tokens.SeparatorToken;
import Tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Body extends Grammar {
    List<Statement> statements;

    public Body(int startToken, int tokensCount, List<Statement> statements) {
        super(startToken, tokensCount);
        this.statements = statements;
    }

    public static Body findNext(List<Token> tokens, int startToken) throws SyntaxError {
        List<Statement> statements = new ArrayList<>();
        int currentToken = startToken;
        Statement statement;
        do {
            statement = Statement.findNext(tokens, currentToken);
            if (statement != null) {
                statements.add(statement);
                currentToken += statement.getTokensCount();
                Token token = tokens.get(currentToken);
                if (token instanceof SeparatorToken && ((SeparatorToken) token).getSeparator().equals(";")) {
                    currentToken++;
                } else {
                    return null;
                }
            }
        } while (currentToken < tokens.size() && statement != null);
        return new Body(startToken, currentToken - startToken, statements);
    }

    @Override
    public void analyse(SymbolTable symbolTable) throws SymbolsError {
        for (Statement statement : statements) {
            statement.analyse(symbolTable);
        }
    }

    @Override
    public void optimise(SymbolTable symbolTable) {
        statements.removeIf(statement -> {
            statement.optimise(symbolTable);
            if (statement instanceof DeclarationStatement declarationStatement) {
                return declarationStatement.getVariableDefinitions().isEmpty();
            }
            return false;
        });
    }

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = super.getJSONFields();
        fields.put("statements", statements);
        return fields;
    }

    public Value execute(SymbolTable symbolTable) throws SymbolsError {
        for (Statement statement : statements) {
            Value value = statement.execute(symbolTable);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
