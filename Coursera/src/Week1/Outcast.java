package Week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class Outcast {

    private static final String[] test1 = {
            "files/synsets.txt", "files/hypernyms.txt", "files/outcast5.txt",
            "files/outcast8.txt", "files/outcast11.txt"
            };

    private static final String[] test2 = {"files/synsets2.txt", "files/hypernyms2.txt", "files/outcast2.txt"};
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {         // constructor takes a WordNet object
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int tmpRes;
        int maxRes = 0;
        String maxNoun = "";
        for (String noun1 : nouns) {
            tmpRes = 0;
            for (String noun2 : nouns) {
                tmpRes += wordNet.distance(noun1, noun2);
            }
            if (tmpRes > maxRes) {
                maxNoun = noun1;
            }
        }
        return maxNoun;
    }

    // see test client below
    public static void main(String[] args) {
        if (args.length == 0){
            args = test2;
        }
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
