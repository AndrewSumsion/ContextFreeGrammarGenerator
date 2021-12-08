package assign11;

import java.io.IOException;
import java.util.Random;

/**
 * Main class of our program which contains our main method
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public class RandomPhraseGenerator {
    /**
     * Loads the grammar file at args[0] and uses it to generate args[1] many random phrases
     * @param args command line arguments
     * @throws IOException if an error occurred while reading the grammar file
     */
    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("Invalid input!");
            System.out.println("Usage: RandomPhraseGenerator <grammar-path> <num-iterations>");
            return;
        }
        Grammar grammar = GrammarReader.readGrammar(args[0]);
        StringBuilder builder = new StringBuilder();

        int N = Integer.parseInt(args[1]);
        for(int i = 0; i < N; i++) {
            grammar.generateString(builder, new Random());
            builder.append('\n');
        }
        System.out.println(builder);
    }
}
