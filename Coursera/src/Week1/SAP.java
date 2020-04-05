package Week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;

/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 *
 * Shortest ancestral path.
 * An ancestral path between two vertices v and w in a digraph is a directed
 * path from v to a common ancestor x, together with a directed path from w to
 * the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length.
 * We refer to the common ancestor in a shortest ancestral path as a shortest common ancestor.
 * Note also that an ancestral path is a path, but not a directed path.
 *
 * NOTE: Should take O(E + V) space and O(E + V) time per method.
 */
public class SAP {

    private final Digraph G;
    private int[] markedV;
    private int[] markedW;

    /** Creation.
     *
     * @param G - a digraph (not necessarily a DAG)
     * @throws IllegalArgumentException if G is null.
     */
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Null arguments.");
        }
        this.G = G;

        markedV = new int[G.V()];
        markedW = new int[G.V()];
    }

    private void clear() {
        for (int i = 0; i < G.V(); i++) {
            markedV[i] = -1;
            markedW[i] = -1;
        }
    }

    /** A common ancestor of v and w that participates
     * in a shortest ancestral path; -1 if no such path.
     *
     * @param v - vertex in the digraph G;
     * @param w - vertex in the digraph G;
     * @return - vertex in the digraph G or -1;
     * @throws IllegalArgumentException
     *              1) if any of parameters is null
     *              2) if any of vertex indexes is out of range of the Week1.Digraph
     */
    public int ancestor(int v, int w) {
        if (v >= G.V() || v < 0 || w >= G.V() || w < 0) {
            throw new IllegalArgumentException("Given vertices are out of range.");
        }
        clear();
        markedV[v] = 0;
        markedW[w] = 0;
        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();
        queueV.enqueue(v);
        queueW.enqueue(w);

        return queue2queueDFS(queueV, queueW);
    }

    /** Length of shortest ancestral path between v and w;
     * -1 if no such path
     *
     * @param v - vertex in the digraph G;
     * @param w - vertex in the digraph G;
     * @return - length of the path or -1;
     * @throws IllegalArgumentException
     *              1) if any of parameters is null
     *              2) if any of vertex indexes is out of range of the Week1.Digraph
     */
    public int length(int v, int w) {
        int anc = ancestor(v, w);
        if (anc >= 0) {
            return markedV[anc] + markedW[anc];
        } else {
            return -1;
        }

    }

    /** The first common ancestor of the sets v and w that participates
     * in a shortest ancestral path; -1 if no such path.
     *
     * @param v - a set of vertices in the digraph G;
     * @param w - a set of vertices in the digraph G;
     * @return - vertex in the digraph G or -1;
     * @throws IllegalArgumentException
     *              1) if any of parameters is null
     *              2) if any of vertex indexes is out of range of the Week1.Digraph
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Null arguments.");
        clear();
        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();

        for (int tmpV : v) {
            if (tmpV < 0 || tmpV >= G.V()) throw new IllegalArgumentException("Given vertices are out of range.");
            markedV[tmpV] = 0;
            queueV.enqueue(tmpV);
        }

        for (int tmpW : w) {
            if (tmpW < 0 || tmpW >= G.V()) throw new IllegalArgumentException("Given vertices are out of range.");
            markedW[tmpW] = 0;
            queueW.enqueue(tmpW);
        }

        return queue2queueDFS(queueV, queueW);
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int anc = ancestor(v, w);
        if (anc >= 0) {
            return markedV[anc] + markedW[anc];
        } else {
            return -1;
        }
    }


    private int queue2queueDFS(Queue<Integer> queueV, Queue<Integer> queueW) {
        int resV;
        int foundMinLen = -1;
        int foundMinAnc = -1;
        while (!(queueV.isEmpty() && queueW.isEmpty())) {
            if (!queueV.isEmpty()) {
                resV = queueStep(queueV, markedV, markedW);
                if (resV >= 0)
                    if (foundMinAnc == -1 ||
                            (foundMinLen > markedW[resV] + markedV[resV])) {
                        foundMinAnc = resV;
                        foundMinLen = markedV[resV] + markedW[resV];
                    }
            }
            if (!queueW.isEmpty()) {
                // in reverse order
                resV = queueStep(queueW, markedW, markedV);
                if (resV >= 0)
                    if (foundMinAnc == -1 ||
                            (foundMinLen > markedW[resV] + markedV[resV])) {
                        foundMinAnc = resV;
                        foundMinLen = markedV[resV] + markedW[resV];
                    }
            }
        }
        return foundMinAnc;
    }


    private int queueStep(Queue<Integer> queue, int[] curMarked, int[] otherMarked) {
        int curV = queue.dequeue();

        if (otherMarked[curV] >= 0) {
            return curV;
        }

        for (int nextV : G.adj(curV)) {
            if (curMarked[nextV] == -1) {
                curMarked[nextV] = curMarked[curV] + 1;
                queue.enqueue(nextV);
            }
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in;
        if (args.length > 0) {
            in = new In(args[0]);
        } else {
            StdOut.print("Enter the path to the input file:\n--> ");
            String name = StdIn.readLine();
            in = new In(name);
        }
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}