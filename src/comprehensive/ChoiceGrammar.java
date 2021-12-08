package comprehensive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a node in the graph representing a grammar.
 * This class, specifically, has a list of children, and passes its generateString operation
 * to a random selection of its children
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public class ChoiceGrammar implements Grammar {
    private final List<Grammar> options;

    public ChoiceGrammar() {
        options = new ArrayList<Grammar>();
    }

    /**
     * Returns the list of children of this node
     * @return a list of this node's children
     */
    public List<Grammar> getOptions() {
        return options;
    }

    /**
     * Passes the generateString operation down to a random child
     * @param builder the StringBuilder to write to
     * @param random the RNG used to select a random child
     */
    @Override
    public void generateString(StringBuilder builder, Random random) {
        options.get(random.nextInt(options.size())).generateString(builder, random);
    }
}
