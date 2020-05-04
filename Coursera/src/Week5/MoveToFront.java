package Week5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

/**
 * created: 04.05.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] indexes = new char[256];
        for (char i = 0; i < 256; i++) {
            indexes[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char tmp = BinaryStdIn.readChar();
            char curIndex = indexes[tmp];
            BinaryStdOut.write(curIndex);
            if (curIndex != 0) {
                for (char i = 0; i < 256; i++) {
                    if (indexes[i] < curIndex) indexes[i]++;
                }
                indexes[tmp] = 0;
            }
        }
        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] indexes = new char[256];
        for (char i = 0; i < 256; i++) {
            indexes[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char curIndex = BinaryStdIn.readChar();
            char symb = indexes[curIndex];
            BinaryStdOut.write(symb);
            if (curIndex != 0) {
                char[] next = new char[256];
                next[0] = indexes[curIndex];
                System.arraycopy(indexes, 0, next, 1, curIndex);
                System.arraycopy(indexes, curIndex+1, next, curIndex+1, indexes.length - curIndex - 1);
                indexes = next;
            }
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length == 0) {
            encode();
        } else if (args[0].equals("-")) encode();
          else if (args[0].equals("+")) decode();
          else StdOut.println("Usage: {MoveToFront -} for encoding and {MoveToFront +} for decoding.");
    }

}
