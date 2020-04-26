package Week4;

import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * created: 26.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */

public class BoggleSolver {

    private final BoggleTrieSt trie;
    private final int dictSize;
    private boolean[] found;
    private Queue<String> foundQueue;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new BoggleTrieSt();
        int i = 0;
        for (String word : dictionary)   trie.put(word, i++);
        dictSize = i;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        foundQueue = new Queue<>();
        found = new boolean[dictSize];
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                dfs(i, j, marked, board, resString);
            }
        }
        return foundQueue;
    }

    private void dfs(int i, int j, boolean[][] marked, BoggleBoard board, StringBuilder resString) {
        char curSymbol = board.getLetter(i, j);
        boolean suc = trie.stepDown(curSymbol);
        boolean flagDelete = false;
        if (suc) {
            resString.append(curSymbol);

            if (curSymbol == 'Q') {
                suc = trie.stepDown('U');
                if (!suc) {
                    resString.deleteCharAt(resString.length()-1);
                    trie.stepUp();
                    return;
                }
                flagDelete = true;
                resString.append('U');
            }

            marked[i][j] = true;
            Integer value = trie.getCurValue();
            if (value != null && !found[value]){
                found[value] = true;
                if (resString.length() > 2)  foundQueue.enqueue(resString.toString());
            }
            for (int i_offset = -1; i_offset <= 1; i_offset++) {
                for (int j_offset = -1; j_offset <= 1; j_offset++) {
                    if (i + i_offset >= 0 && i + i_offset < board.rows() &&
                            j + j_offset >= 0 && j + j_offset < board.cols())
                        if (!marked[i + i_offset][j + j_offset]) {
                            dfs(i + i_offset, j + j_offset, marked, board, resString);
                        }
                }
            }
            resString.deleteCharAt(resString.length()-1);
            if (flagDelete) resString.deleteCharAt(resString.length()-1);
            trie.stepUp();
            if (flagDelete) trie.stepUp();
            marked[i][j] = false;
        }

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if      (!trie.contains(word))                     return 0;
        if      (word.length() == 3 || word.length() == 4) return 1;
        else if (word.length() == 5)                       return 2;
        else if (word.length() == 6)                       return 3;
        else if (word.length() == 7)                       return 5;
        else if (word.length() >= 8)                       return 11;
        else                                               return 0;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            args = new String[2];
            args[0] = "files/week4/boggle/dictionary-yawl.txt";
            args[1] = "files/week4/test.txt";
        }
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        StdOut.println("Score of UBA:" + solver.scoreOf("UBA"));
    }
}
