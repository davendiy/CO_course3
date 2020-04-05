package Week1;

/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class DirectedDFS {

    private boolean[] marked;

    public DirectedDFS(Digraph G, int start){
        marked = new boolean[G.V()];
        dfs(G, start);
    }

    private void dfs(Digraph G, int cur_vertex){
        marked[cur_vertex] = true;
        for (int next_vertex : G.adj(cur_vertex)){
            if (!marked[next_vertex])
                dfs(G, next_vertex);
        }
    }

    public boolean visited(int vertex){
        return marked[vertex];
    }
}
