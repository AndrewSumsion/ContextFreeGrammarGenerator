package comprehensive;

import java.util.Random;

/**
 * This class is a leaf node in a graph that represents a context-free grammar
 * It simply holds a string value, and adds that string value to the given StringBuilder
 */
public class TerminalGrammar implements Grammar {
    private final String string;

    public TerminalGrammar(String string) {
        this.string = string;
    }

    /**
     * Adds this grammar's fixed string value to the given StringBuilder
     *
     * @param builder The StringBuilder to add to
     * @param random Unused in this class
     */
    @Override
    public void generateString(StringBuilder builder, Random random) {
        builder.append(string);
    }
}
