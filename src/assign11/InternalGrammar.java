package assign11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InternalGrammar implements Grammar {
    private final List<Grammar> options;

    public InternalGrammar() {
        options = new ArrayList<Grammar>();
    }

    public List<Grammar> getOptions() {
        return options;
    }

    @Override
    public void generateString(StringBuilder builder, Random random) {
        options.get(random.nextInt(options.size())).generateString(builder, random);
    }
}
