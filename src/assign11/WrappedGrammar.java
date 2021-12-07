package assign11;

import java.util.Map;
import java.util.Random;

/**
 * A simple wrapper for a Grammar object.
 *
 * This class is initialized with a Map of Strings and Grammars, and one specific String key
 * When generateString is called, it checks if it has already cached the result from the Map.
 * If it has, it simply forwards the generateString call to that object.
 * If it hasn't, it attempts to get the Grammar from the Map, caches it for future calls, and forwards the call.
 *
 * This allows us to create Grammars that reference a string grammar value, whose corresponding grammar may or
 * may not have already been generated. Our file format allows grammars to be defined that depend on other grammars
 * that are stored later in the file and haven't been parsed yet.
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public class WrappedGrammar implements Grammar {
    private Grammar grammar;
    private final Map<String, Grammar> map;
    private final String grammarName;

    public WrappedGrammar(String grammarName, Map<String, Grammar> map) {
        grammar = null;
        this.map = map;
        this.grammarName = grammarName;
    }

    /**
     * Forwards the generateString call to the Grammar object that this object wraps.
     * @param builder The StringBuilder to add to
     * @param random The RNG which may or may not be used in the wrapped Grammar
     */
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
