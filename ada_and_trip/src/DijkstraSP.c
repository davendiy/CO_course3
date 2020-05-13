// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/DijkstraSP.h"


// TODO: add validation
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
    for (VERTEX_TYPE i = 0; i < G->V; i++) {
        if (res->distTo[i] > res->maxDistance && res->distTo[i] != INT_MAX)
            res->maxDistance = res->distTo[i];
    }

//    check(res, G, source);
    return res;
}


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

char hasPathTo(DijkstraRet* self, VERTEX_TYPE v){
    return self->distTo[v] != INT_MAX;
}

Bag* pathTo(DijkstraRet* self, VERTEX_TYPE v){
    if (!hasPathTo(self, v)) return NULL;
    Bag* path = (Bag*) malloc(sizeof(Bag));
    for (DirectedEdge* e = self->edgeTo[v]; e != NULL; e = self->edgeTo[e->from])
        path = addElement(path, e);
    return path;
}


VERTEX_TYPE mostDistantAmount(Digraph *G, DijkstraRet *self) {
    VERTEX_TYPE res = 0;
    for (VERTEX_TYPE i = 0; i < G->V; i++) {
        if (self->distTo[i] == self->maxDistance) res++;
    }
    return res;
}

//static void check(DijkstraRet* object, Digraph G, VERTEX_TYPE source);
