package Week5;

import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.StdOut;

/**
 * created: 04.05.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class CircularSuffixArray {

    private final int len;
    private final int[] index;
//    private final String[] suffixArray;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("Null pointer as a parameter.");
        StringBuilder sb = new StringBuilder(s);
        len = s.length();
        String[] suffixArray = new String[len];
        index = new int[len];
        for (int i = 0; i < len; i++) {
            suffixArray[i] = sb.toString();
            char tmp = sb.charAt(0);
            sb.deleteCharAt(0);
            sb.append(tmp);
            index[i] = i;
        }

        sort(suffixArray, s.length());
    }

    private void sort(String[] a, int w) {
        int n = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[n];
        int[] aux2 = new int[n];
        for (int d = w-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < n; i++) {
                aux[count[a[i].charAt(d)]] = a[i];
                aux2[count[a[i].charAt(d)]] = index[i];
                count[a[i].charAt(d)]++;
            }

            // copy back
            for (int i = 0; i < n; i++){
                a[i] = aux[i];
                index[i] = aux2[i];
            }
        }
    }

    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= len) throw new IllegalArgumentException("Index is out of its precised bounds.");
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String test = "ABRACADABRA!";
        CircularSuffixArray testSA = new CircularSuffixArray(test);
        for (int i = 0; i < test.length(); i++) {
            StdOut.println(testSA.index(i));
        }
    }

}