package Week1;

/**
 * created: 04.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class KosarajuSharirSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSharirSCC(Digraph G){
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        for (int v : dfs.reversePost()){
            if (!marked[v]){
                this.dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v){
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)){
            if (!marked[w]){
                dfs(G, w);
            }
        }
    }

    public boolean stronglyConnected(int v, int w){
        return id[v] == id[w];
    }
}
