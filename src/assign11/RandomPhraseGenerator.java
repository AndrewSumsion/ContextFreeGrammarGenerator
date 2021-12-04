package assign11;

import java.io.IOException;
import java.util.Random;

public class RandomPhraseGenerator {
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
            builder.setLength(0);
            grammar.generateString(builder, new Random());
            System.out.println(builder);
        }
    }
}
