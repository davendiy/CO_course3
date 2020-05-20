#include "headers/DijkstraSP.h"
#include <stdio.h>

int main() {
    int N, M, Q;
    scanf("%d", &N);
    scanf("%d", &M);
    scanf("%d", &Q);

    // create Digraph with N vertices
    Digraph* digraph = createDigraph(N);
    for (int i = 0; i < M; i++){
        int tmp_from, tmp_to, tmp_len;
        scanf("%d", &tmp_from);
        scanf("%d", &tmp_to);
        scanf("%d", &tmp_len);

        // add each edge twice with opposite directions
        addEdge(digraph, tmp_from, tmp_to, tmp_len);
        addEdge(digraph, tmp_to, tmp_from, tmp_len);
    }

    for (int i = 0; i < Q; i++){
        int source;
        scanf("%d", &source);

        // run Dijkstra algo. Then find the biggest value in
        // the result distance table and how many times it appears
        DijkstraRet *res = DijkstraAlgo(digraph, source);
        maxDistance(digraph, res);
        printf("%d %d\n", res->maxDistance, mostDistantAmount(digraph, res));
    }
}
