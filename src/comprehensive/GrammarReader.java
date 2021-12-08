package comprehensive;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for reading context-free grammars from a file using this
 * assignment's custom format.
 *
 * @author Andrew Sumsion & Dillon Otto
 */
public class GrammarReader {

    /**
     * Reads a grammar file and returns a Grammar object for the <start> grammar
     *
     * @param path Path of the grammar file to be read
     * @return The grammar defined by <start> in the file
     * @throws IOException On error reading file
     */
    public static Grammar readGrammar(String path) throws IOException {
        return readGrammar(new File(path));
    }

    /**
     * Reads a grammar file and returns a Grammar object for the <start> grammar
     *
     * @param file The grammar file to be read
     * @return The grammar defined by <start> in the file
     * @throws IOException On error reading file
     */
    public static Grammar readGrammar(File file) throws IOException {
        return readGrammar(file, "<start>");
    }

    /**
     * Reads a grammar file and returns a Grammar object
     *
     * @param file The grammar file to be read
     * @param grammarName The name of the grammar to return, ex: <start>
     * @return The grammar defined by grammarName in the file
     * @throws IOException On error reading file
     */
    public static Grammar readGrammar(File file, String grammarName) throws IOException {
        return readGrammar(new FileInputStream(file), grammarName);
    }

    /**
     * Reads a grammar file and returns a Grammar object
     *
     * @param in Input stream of the grammar file to be read
     * @param grammarName The name of the grammar to return, ex: <start>
     * @return The grammar defined by grammarName in the file
     * @throws IOException On error reading file
     */
    public static Grammar readGrammar(InputStream in, String grammarName) throws IOException {
        return readGrammar(new BufferedReader(new InputStreamReader(in)), grammarName);
    }

    /**
     * Reads a grammar file and returns a Grammar object
     *
     * @param in BufferedReader of the grammar file to be read
     * @param grammarName The name of the grammar to return, ex: <start>
     * @return The grammar defined by grammarName in the file
     * @throws IOException On error reading file
     */
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
     *
     * @param in The BufferedReader to read from
     * @param internalGrammars The resulting grammar is added to this map with a key of its name, ex: <start>
     */
    private static void readSingleGrammar(BufferedReader in, Map<String, Grammar> internalGrammars) throws IOException {
        // the first line after the { is the grammar name
        String grammarName = in.readLine();
        ChoiceGrammar grammar = new ChoiceGrammar();

        // parse each subsequent line as a ConcatenateGrammar until } reached
        String line = in.readLine();
        while (!line.startsWith("}")) {
            grammar.getOptions().add(createPhraseGrammar(line, internalGrammars));
            line = in.readLine();
        }

        // add this grammar to the operation-global map of grammar names to grammars
        internalGrammars.put(grammarName, grammar);
    }

    /**
     * This method takes one line, ie one option, of a grammar, and parses it into a ConcatenateGrammar.
     * It does this by keeping two pointers to locations in the string.
     * It increments the right pointer until the start of a grammar expression is found ('<'), adds the substring
     * between the left and right pointers as a TerminalGrammar to the children of the resulting ConcatenateGrammar,
     * moves the left grammar up, and repeats the process, this time looking for a '>'. A new WrappedGrammar is then
     * added to the child list that references the grammar in the given map with the name between the pointers ie <start>.
     * It repeats this process until it reaches the end of the string.
     *
     * Example:
     *
     * String: "Hello <gram> World"
     * Left:    ^
     * Right    ^
     *
     * Read until '<'
     *
     * String: "Hello <gram> World"
     * Left:    ^
     * Right          ^
     *
     * "Hello " added as a TerminalGrammar, move left pointer up
     *
     * String: "Hello <gram> World"
     * Left:          ^
     * Right          ^
     *
     * Read until '>' + 1
     *
     * String: "Hello <gram> World"
     * Left:          ^
     * Right                ^
     *
     * WrappedGrammar added with key "<gram>", move left pointer up
     *
     * String: "Hello <gram> World"
     * Left:                ^
     * Right                ^
     *
     * ... And so on until the end of the string ...
     *
     * @param phrase The phrase to be parsed
     * @param internalGrammars The map to be referenced by generated WrappedGrammars
     * @return A new ConcatenateGrammar
     */
    private static ConcatenateGrammar createPhraseGrammar(String phrase, Map<String, Grammar> internalGrammars) {
        ConcatenateGrammar out = new ConcatenateGrammar();

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
                out.getChildren().add(new TerminalGrammar(phrase.substring(startIndex, index)));
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
                out.getChildren().add(new WrappedGrammar(phrase.substring(startIndex, index), internalGrammars));
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
