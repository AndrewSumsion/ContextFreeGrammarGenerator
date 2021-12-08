package comprehensive;

import java.util.Random;

/**
 * This is a simple interface that is an abstraction of a context-free grammar.
 * It simply adds whatever its value is to the given StringBuilder, using the given RNG if needed.
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public interface Grammar {
    void generateString(StringBuilder builder, Random random);
}
