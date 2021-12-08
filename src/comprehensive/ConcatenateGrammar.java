package comprehensive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a node in the graph representing a grammar.
 * This class, specifically, has a list of children, and passes its generateString operation
 * to each of its children in order.
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public class ConcatenateGrammar implements Grammar {
    private final List<Grammar> children;

    public ConcatenateGrammar() {
        children = new ArrayList<Grammar>();
    }

    /**
     * Returns the list of children of this node
     * @return a list of this node's children
     */
    public List<Grammar> getChildren() {
        return children;
    }

    /**
     * Passes the generateString operation down to each child in order
     * @param builder the StringBuilder to write to
     * @param random the RNG needed if any children are ChoiceGrammars
     */
    @Override
    public void generateString(StringBuilder builder, Random random) {
        for(Grammar grammar : children) {
            grammar.generateString(builder, random);
        }
    }
}
