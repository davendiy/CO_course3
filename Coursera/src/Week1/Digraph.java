package Week1;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */

public class Digraph {

    private final int V;
    private int E = 0;
    private final Bag<Integer>[] adj;

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public Digraph(int V) {
        this.V = V;

        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Digraph(In in){
        this.V = in.readInt();
        this.E = in.readInt();
        adj = (Bag<Integer>[]) new Bag[this.V];
        for (int v = 0; v < V; v++){
            adj[v] = new Bag<Integer>();
        }
        int from;
        int to;
        for (int e = 0; e < E; e++){
            from = in.readInt();
            to = in.readInt();
            addEdge(from, to);
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse(){
        Digraph rev = new Digraph(this.V);
        for (int from = 0; from < this.V; from++){
            for (int to : this.adj(from)){
                rev.addEdge(to, from);
            }
        }
        return rev;
    }

    public boolean isCyclic(){
        boolean[] marked = new boolean[V];
        boolean res = false;

        for (int v = 0; v < V; v++){

            marked[v] = true;
            res = isCyclic(v, marked);
            if (res){
                break;
            }
            Arrays.fill(marked, false);
        }
        return res;
    }

    private boolean isCyclic(int vertex, boolean[] marked){
        boolean res = false;
        for (int next_vertex : adj(vertex)){
            if (marked[next_vertex]){
                res = true;
                break;
            }
            marked[next_vertex] = true;
            res = isCyclic(next_vertex, marked);
            if (res){
                break;
            }
        }
        return res;
    }

    public static void main(String[] args){
        In in;
        if (args.length > 0) {
            in = new In(args[0]);
        } else {
            in = new In("files/test_graph.txt");
        }
        Digraph G = new Digraph(in);
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + "->" + w);
        StdOut.print("Is cyclic: " + G.isCyclic());
    }
}
