package Grammar.References;

import Grammar.Grammar;
import Grammar.SyntaxError;
import Symbols.SymbolTable;
import Symbols.SymbolValue;
import Symbols.SymbolsError;
import Tokens.Token;

import java.util.List;

public abstract class Reference extends Grammar {
    public Reference(int startToken, int tokensCount) {
        super(startToken, tokensCount);
    }

    public static Reference findNext(List<Token> tokens, int startToken) throws SyntaxError {
        Reference reference;
        reference = IdentifierReference.findNext(tokens, startToken);
        if (reference != null) {
            return findNext(tokens, startToken, reference);
        }
        return null;
    }

    public static Reference findNext(List<Token> tokens, int startToken, Reference object) throws SyntaxError {
        Reference reference;
        reference = ArrayElementReference.findNext(tokens, startToken, object);
        if (reference != null) return findNext(tokens, startToken, reference);
        reference = NamedTupleElementReference.findNext(tokens, startToken, object);
        if (reference != null) return findNext(tokens, startToken, reference);
        reference = UnnamedTupleElementReference.findNext(tokens, startToken, object);
        if (reference != null) return findNext(tokens, startToken, reference);
        reference = FunctionCallReference.findNext(tokens, startToken, object);
        if (reference != null) return findNext(tokens, startToken, reference);
        return object;
    }

    public abstract SymbolValue getSymbolValue(SymbolTable symbolTable) throws SymbolsError;
}
