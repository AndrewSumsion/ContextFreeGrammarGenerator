package assign11;

import java.util.Map;
import java.util.Random;

public class GrammarWrapper implements Grammar {
    private Grammar grammar;
    private final Map<String, Grammar> map;
    private final String grammarName;

    public GrammarWrapper(String grammarName, Map<String, Grammar> map) {
        grammar = null;
        this.map = map;
        this.grammarName = grammarName;
    }

    @Override
    public void generateString(StringBuilder builder, Random random) {
        if(grammar == null) {
            grammar = map.get(grammarName);
            if(grammar == null) {
                throw new RuntimeException("No grammar found with name " + grammarName);
            }
        }
        grammar.generateString(builder, random);
    }
}
