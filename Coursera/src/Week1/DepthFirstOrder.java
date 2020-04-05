package Week1;

import edu.princeton.cs.algs4.Stack;

/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class DepthFirstOrder {

    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G){
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++){
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int cur_vertex){
        marked[cur_vertex] = true;
        for (int next_vertext : G.adj(cur_vertex)){
            if (!marked[next_vertext]) dfs(G, next_vertext);
        }
        reversePost.push(cur_vertex);
    }

    public Iterable<Integer> reversePost(){
        return  reversePost;
    }
}
