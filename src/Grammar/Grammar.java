package Grammar;

import JSON.JSONSerializable;
import Symbols.SymbolTable;
import Symbols.SymbolsError;

import java.util.LinkedHashMap;
import java.util.Map;

abstract public class Grammar implements JSONSerializable {
    private final int startToken;
    private final int tokensCount;

    public Grammar(int startToken, int tokensCount) {
        this.startToken = startToken;
        this.tokensCount = tokensCount;
    }

    public int getStartToken() {
        return startToken;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void analyse(SymbolTable symbolTable) throws SymbolsError {}

    public void optimise(SymbolTable symbolTable) {}

    @Override
    public Map<String, Object> getJSONFields() {
        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("_type", this.getClass().getSimpleName());
        return fields;
    }
}
