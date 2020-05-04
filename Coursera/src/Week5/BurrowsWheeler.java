package Week5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;

/**
 * created: 04.05.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray suffixArr = new CircularSuffixArray(s);
        char[] res = new char[suffixArr.length()];
        int first = -1;

        for (int i = 0; i < suffixArr.length(); i++) {
            int pos = suffixArr.index(i);
            if (pos == 0)  first = i;
            pos = (pos - 1 + suffixArr.length()) % (suffixArr.length());
            res[i] = s.charAt(pos);
        }

        BinaryStdOut.write(first);
        for (char re : res)  BinaryStdOut.write(re);

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void inverseTransform() {

        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] arr = s.toCharArray();
        if (first > arr.length) return;

        char[] front = new char[arr.length];
        System.arraycopy(arr, 0, front, 0, arr.length);
        Arrays.sort(front);

        //  store result for next array
        int[] next = new int[arr.length];
        int cur = 0;
        HashMap<Character, Queue<Integer>> dict = new HashMap<>();
        for (int i=0; i<arr.length; i++) {
            if (dict.containsKey(arr[i])) {
                dict.get(arr[i]).enqueue(i);
            }
            else {
                Queue<Integer> q = new Queue<Integer>();
                q.enqueue(i);
                dict.put(arr[i], q);
            }
        }
        for (int i=0; i<front.length; i++) {
            int ans = dict.get(front[i]).dequeue();
            next[i] = ans;
        }

        char[] result = new char[arr.length];
        cur = first;
        for (int i=0; i<arr.length; i++) {
            result[i] = front[cur];
            cur = next[cur];
            BinaryStdOut.write(result[i]);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args.length == 0) {
            transform();
        } else if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else StdOut.println("Usage: {BurrowsWheeler -} for encoding and {BurrowsWheeler +} for decoding.");
    }

}
