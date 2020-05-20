// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/DijkstraSP.h"


// TODO: add validation
/**
 * Computes a shortest-paths tree from the source vertex {source} to every other
 * vertex in the edge-weighted digraph {G}.
 *
 * @param  G      - the edge-weighted digraph
 * @param  source - the source vertex
 * @return DijkstraRet structure.
 */
DijkstraRet* DijkstraAlgo(Digraph* G, VERTEX_TYPE source) {
    DijkstraRet* res = (DijkstraRet*) malloc(sizeof(DijkstraRet));
    res->distTo = (int *) malloc(G->V * sizeof(int));
    res->edgeTo = (DirectedEdge**) malloc(G->V * sizeof(DirectedEdge*));
    res->maxDistance = 0;

    for (VERTEX_TYPE v = 0; v < G->V; v++)
        res->distTo[v] = INT_MAX;
    res->distTo[source] = 0;

    res->pq = createIndexMinPq(G->V);
    insert(res->pq, source, res->distTo[source]);

    while (!isEmptyPQ(res->pq)) {
        VERTEX_TYPE v = delMin(res->pq);
        for (Bag* edgeBag = G->adj[v]; edgeBag != NULL; edgeBag = getNext(edgeBag))
            relax(res, (DirectedEdge*)edgeBag->element);
    }

//    check(res, G, source);
    return res;
}

// relax edge e and update pq if changed
static void relax(DijkstraRet* self, DirectedEdge* e){
    VERTEX_TYPE v = e->from;
    VERTEX_TYPE w = e->to;
    if (self->distTo[w] > self->distTo[v] + e->weight) {
        self->distTo[w] = self->distTo[v] + e->weight;
        self->edgeTo[w] = e;
        if (contains(self->pq, w))  decreaseKey(self->pq, w, self->distTo[w]);
        else                        insert(self->pq, w, self->distTo[w]);
    }
};

/**
 * Returns true if there is a path from the source vertex to vertex {v}.
 *
 * @param self - resulf of DijkstraAlgo
 * @param      - v the destination verte
 * @return 1 if there is a path from the source vertex
 *         to vertex {v}; 0 otherwise
 */
int hasPathTo(DijkstraRet* self, VERTEX_TYPE v){
    return self->distTo[v] != INT_MAX;
}

/**
 * Returns a shortest path from the source vertex to vertex {v}.
 *
 * @param self - a resulf of the DijkstraAlgo
 * @param  v   - the destination vertex
 * @return a shortest path from the source vertex to vertex {v}
 *         as an iterable of edges, and null if no such path
 */
Bag* pathTo(DijkstraRet* self, VERTEX_TYPE v){
    if (!hasPathTo(self, v)) return NULL;
    Bag* path = (Bag*) malloc(sizeof(Bag));
    for (DirectedEdge* e = self->edgeTo[v]; e != NULL; e = self->edgeTo[e->from])
        path = addElement(path, e);
    return path;
}

/**
 * Returns the amount of vertices that have the max shortest path.
 *
 * @param G    - Directed graph
 * @param self - a result of the DijkstraAlgo
 *
 * NOTE: you should use {maxDistance} before.
 */
VERTEX_TYPE mostDistantAmount(Digraph *G, DijkstraRet *self) {
    VERTEX_TYPE res = 0;
    for (VERTEX_TYPE i = 0; i < G->V; i++) {
        if (self->distTo[i] == self->maxDistance) res++;
    }
    return res;
}

/**
 * Finds the biggest value in the DijkstraRet.distTo array, i.e. the maximum
 * shortest path from the source vertex to any other.
 * Set the result into res->maxDistance.
 */
void maxDistance(Digraph* G, DijkstraRet* res) {
    for (VERTEX_TYPE i = 0; i < G->V; i++) {
        if (res->distTo[i] > res->maxDistance && res->distTo[i] != INT_MAX)
            res->maxDistance = res->distTo[i];
    }
}

//static void check(DijkstraRet* object, Digraph G, VERTEX_TYPE source);
