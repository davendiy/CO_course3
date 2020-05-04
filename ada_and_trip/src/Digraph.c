// Created by David Zashkolny on 02.05.2020.
// 2 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/Digraph.h"


unsigned int getOther(DirectedEdge *edge, VERTEX_TYPE vertex) {
    if (vertex == edge->from) return edge->to;
    else                   return edge->from;
}


Digraph* createDigraph(VERTEX_TYPE V) {
    Digraph *graph = (Digraph*) malloc(sizeof(Digraph));
    graph->V = V;
    graph->E = 0;
    graph->adj = (Bag**) malloc(V * sizeof(Bag*));
    return graph;
}


void addEdge(Digraph *graph, VERTEX_TYPE from, VERTEX_TYPE to, WEIGHT_TYPE weight) {
    if (from >= graph->V || to >= graph->V) {
        if (from >= graph->V) fprintf(stderr, "Bad vertex number: %d\n", from);
        else               fprintf(stderr, "Bad vertex number: %d\n", to);
        return;
    }

    DirectedEdge *edge = (DirectedEdge*) malloc(sizeof(DirectedEdge));
    edge->from = from;
    edge->to = to;
    edge->weight = weight;

    graph->adj[from] = addElement(graph->adj[from], (void *) edge);
    graph->E++;
}


void deleteGraph(Digraph *graph) {
    for (unsigned int i = 0; i < graph->V; i++) {
        deleteBag(graph->adj[i]);
    }
    free(graph);
}