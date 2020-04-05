package Week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.HashMap;

/**
 * created: 03.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 *
 *
 * WordNet is a semantic lexicon for the English language that computational
 * linguists and cognitive scientists use extensively.
 * For example, WordNet was a key component in IBM’s Jeopardy-playing Watson computer system.
 * WordNet groups words into sets of synonyms called synsets.
 * For example, { AND circuit, AND gate } is a synset that represent a logical gate that fires
 * only when all of its inputs fire. WordNet also describes semantic relationships between synsets.
 * One such relationship is the is-a relationship, which connects a hyponym (more specific synset)
 * to a hypernym (more general synset). For example, the synset { gate, logic gate } is a hypernym
 * of { AND circuit, AND gate } because an AND gate is a kind of logic gate.
 *
 * The WordNet digraph. Your first task is to build the WordNet digraph: each vertex v is an integer
 * that represents a synset, and each directed edge v→w represents that w is a hypernym of v.
 * The WordNet digraph is a rooted DAG: it is acyclic and has one vertex—the root—that
 * is an ancestor of every other vertex. However, it is not necessarily a tree because a synset
 * can have more than one hypernym
 *
 *
 * List of hypernyms. The file hypernyms.txt contains the hypernym relationships.
 * Line i of the file (counting from 0) contains the hypernyms of synset i. The first field is the synset id,
 * which is always the integer i; subsequent fields are the id numbers of the synset’s hypernyms.
 */

public class WordNet {

    private final HashMap<String, Bag<Integer>> nounMap;
    private final HashMap<Integer, String> synMap;
    private final SAP hypSap;

    private final static int CYCLIC = -1;

    /** Create the new WordNet object. Should use O(E + V) space and
     * should take O(E + V) time for constructing.
     *
     * @param synsets - List of synsets.
     *      This file contains all noun synsets in WordNet, one per line.
     *      Line i of the file (counting from 0) contains the information for synset i. The first field is
     *      the synset id, which is always the integer i; the second field is the synonym set (or synset);
     *      and the third field is its dictionary definition (or gloss),
     *      which is not relevant to this assignment..
     *
     * @param hypernyms - List of hypernyms.
     *      This file contains the hypernym relationships.
     *      Line i of the file (counting from 0) contains the hypernyms of synset i.
     *      The first field is the synset id, which is always the integer i;
     *      subsequent fields are the id numbers of the synset’s hypernyms.
     *
     * @throws IllegalArgumentException
     *          1) if any of argument is null.
     *          2) if the input files does not correspond to a rooted DAG
     *                  (doesn't check whether file has required format)
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("Null arguments.");
        }
        nounMap = new HashMap<>();
        synMap = new HashMap<>();
        In synIn = new In(synsets);
        String line;
        String[] params;
        String[] nouns;
        int index;
        Bag<Integer> usedIndexes;

        while (!synIn.isEmpty()) {
            line = synIn.readLine();
            params = line.split(",");
            index = Integer.parseInt(params[0]);
            nouns = params[1].split(" ");
            for (String noun : nouns) {
                if (nounMap.containsKey(noun)) {
                    usedIndexes = nounMap.get(noun);
                    usedIndexes.add(index);
                } else {
                    usedIndexes = new Bag<>();
                    usedIndexes.add(index);
                    nounMap.put(noun, usedIndexes);
                }
            }
            synMap.put(index, params[1]);
        }

        Digraph hypGraph = new Digraph(synMap.size());
        In hypIn = new In(hypernyms);
        int from = 0, to;
        while (!hypIn.isEmpty()) {
            line = hypIn.readLine();
            params = line.split(",");
            for (int i = 1; i < params.length; i++) {
                to = Integer.parseInt(params[i]);
                hypGraph.addEdge(from, to);
            }
            from++;
        }

        if (!checkGraph(hypGraph)) {
            throw new IllegalArgumentException("Bad hypernyms graph.");
        }

        hypSap = new SAP(hypGraph);
    }

    private boolean checkGraph(Digraph G) {
        int roots = 0;
        int res;
        int[] marked = new int[G.V()];
        Arrays.fill(marked, 0);
        for (int v = 0; v < G.V(); v++){
            if (marked[v] == 0) {
                res = dfs(G, marked, v);
                if (res == CYCLIC){
                    return false;
                } else {
                    roots += res;
                    if (roots > 1){
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private int dfs(Digraph G, int[] marked, int vertex){
        marked[vertex] = 1;
        int neighbours = 0;
        int resCode = 0;
        for (int nextVertex : G.adj(vertex)) {
            neighbours++;
            if (marked[nextVertex] == 1) {
                return CYCLIC;
            } else if (marked[nextVertex] == 0) {
                int tmpRes = dfs(G, marked, nextVertex);
                if (tmpRes == CYCLIC) {
                    return CYCLIC;
                } else {
                    resCode += tmpRes;
                    if (resCode > 1) {
                        return resCode;
                    }
                }
            }
        }
        marked[vertex] = 2;
        if (neighbours == 0) {
            resCode = 1;
        }
        return resCode;
    }


    /** Get all the WordNet nouns.
     *
     * @return - iterable object of strings - all the nouns of the WordNet.
     */
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    /** Checks whether the given word is a WordNet noun.
     * Should take O(log(Nouns)) time. Nouns takes O(1) per synset,
     *
     * @param word - some word.
     * @return - true if the word belongs to WordNet.
     * @throws IllegalArgumentException if word is null.
     */
    public boolean isNoun(String word) {

        if (word == null) {
            throw new IllegalArgumentException("Null arguments.");
        }
        return nounMap.containsKey(word);
    }

    /** Distance between nounA and nounB.
     * Should take O(V + E) time.
     *
     * @param nounA - noun from WordNet
     * @param nounB - noun from WordNet
     * @return - Length of shortest ancestral path between v and w as vertices of hypernym graph
     *
     * @throws IllegalArgumentException
     *          1) if any of argument is null.
     *          2) if any of argument isn't noun from the WordNet
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("Null arguments.");
        }
        if ((!isNoun(nounA) || !isNoun(nounB)))
            throw new IllegalArgumentException("Given words don't exist in synsets.");
        Bag<Integer> v = nounMap.get(nounA);
        Bag<Integer> w = nounMap.get(nounB);
        return hypSap.length(v, w);
    }

    /** A synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path. Should take O(V + E) time.
     *
     * @param nounA - noun from WordNet
     * @param nounB - noun from WordNet
     * @return - synset.
     * @throws IllegalArgumentException
     *          1) if any of argument is null.
     *          2) if any of argument isn't noun from the WordNet
     *
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("Null arguments.");
        }
        if ((!isNoun(nounA) || !isNoun(nounB)))
            throw new IllegalArgumentException("Given words don't exist in synsets.");

        Bag<Integer> v = nounMap.get(nounA);
        Bag<Integer> w = nounMap.get(nounB);
        int resIndex = hypSap.ancestor(v, w);
        return synMap.get(resIndex);
    }

//    public static void main(String[] args) {
//
//    }
}

