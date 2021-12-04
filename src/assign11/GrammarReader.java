package assign11;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GrammarReader {

    public static Grammar readGrammar(String path) throws IOException {
        return readGrammar(new File(path));
    }

    public static Grammar readGrammar(File file) throws IOException {
        return readGrammar(file, "<start>");
    }

    public static Grammar readGrammar(File file, String grammarName) throws IOException {
        return readGrammar(new FileInputStream(file), grammarName);
    }

    public static Grammar readGrammar(InputStream in, String grammarName) throws IOException {
        return readGrammar(new BufferedReader(new InputStreamReader(in)), grammarName);
    }

    public static Grammar readGrammar(BufferedReader in, String grammarName) throws IOException {
        Map<String, Grammar> internalGrammars = new HashMap<String, Grammar>();
        while(true) {
            String line = in.readLine();
            if(line == null) {
                break; // eof
            }
            if(line.startsWith("{")) {
                readSingleGrammar(in, internalGrammars);
            }
        }

        return internalGrammars.get(grammarName);
    }

    /**
     * Reads a grammar line by line until it reads a line starting with }.
     * Assumes a { has already been read.
     * @param in
     */
    private static void readSingleGrammar(BufferedReader in, Map<String, Grammar> internalGrammars) throws IOException {
        String grammarName = in.readLine();
        InternalGrammar grammar = new InternalGrammar();
        String line = in.readLine();
        while (!line.startsWith("}")) {
            // add a PhraseGrammar option for every line until end
            grammar.getOptions().add(createPhraseGrammar(line, internalGrammars));
            line = in.readLine();
        }

        // add this grammar to the operation-global map of grammar names to grammars
        internalGrammars.put(grammarName, grammar);
    }

    private static PhraseGrammar createPhraseGrammar(String phrase, Map<String, Grammar> internalGrammars) {
        PhraseGrammar out = new PhraseGrammar();

        // keep track of two pointers for the beginning and end of the segment
        int startIndex = 0;
        int index = 0;
        while(true) {
            // move right pointer forward until start of grammar name, or end of string
            while (index < phrase.length() && phrase.charAt(index) != '<') {
                index++;
            }
            // if we moved at all, add a new child to the phrase with the substring
            if(index > startIndex) {
                out.getChildren().add(new LeafGrammar(phrase.substring(startIndex, index)));
            }
            // if we've reached the end of the string, return what we've got
            if(index >= phrase.length()) {
                return out;
            }

            // move left pointer up to right pointer to start searching for new string
            startIndex = index;

            // move right pointer forward until end of grammar name, or end of string
            while (index < phrase.length() && phrase.charAt(index) != '>') {
                index++;
            }
            // increment one more time to include > (substring is right-exclusive)
            index++;

            // if we moved at all, add a new child using a GrammarWrapper, which wraps a grammar in a map found by the given substring
            // for example: new GrammarWrapper("<test>", grammars)
            //    grammars: {"<test>": <some grammar object>}

            // a reference to the internalGrammars map is maintained by each one of these GrammarWrappers, so it won't disappear until it's not needed.
            if(index > startIndex) {
                out.getChildren().add(new GrammarWrapper(phrase.substring(startIndex, index), internalGrammars));
            }
            // if we've reached the end of the string, return what we've got
            if(index >= phrase.length()) {
                return out;
            }

            // move left pointer up
            startIndex = index;
        }
    }
}
