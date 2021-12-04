package assign11;

import java.util.Random;

public class LeafGrammar implements Grammar {
    private final String string;

    public LeafGrammar(String string) {
        this.string = string;
    }

    @Override
    public void generateString(StringBuilder builder, Random random) {
        builder.append(string);
    }
}
