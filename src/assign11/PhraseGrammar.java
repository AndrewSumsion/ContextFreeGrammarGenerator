package assign11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhraseGrammar implements Grammar {
    private final List<Grammar> children;

    public PhraseGrammar() {
        children = new ArrayList<Grammar>();
    }

    public List<Grammar> getChildren() {
        return children;
    }

    @Override
    public void generateString(StringBuilder builder, Random random) {
        for(Grammar grammar : children) {
            grammar.generateString(builder, random);
        }
    }
}
