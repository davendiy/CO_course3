// Created by David Zashkolny on 02.05.2020.
// 2 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#ifndef COMBINATORICAL_OPTIMIZATIONAL_DIGRAPH_H
#define COMBINATORICAL_OPTIMIZATIONAL_DIGRAPH_H

#include "Bag.h"
#include <stdlib.h>
#include <stdio.h>

typedef unsigned int VERTEX_TYPE;
typedef int WEIGHT_TYPE;

typedef struct DirectedEdge {
    VERTEX_TYPE from;
    VERTEX_TYPE to;
    WEIGHT_TYPE weight;
} DirectedEdge;

// get another adjacent vertix
unsigned int getOther(DirectedEdge *edge, VERTEX_TYPE vertex);

typedef struct Digraph {
    VERTEX_TYPE V;    // amount of vertices
    VERTEX_TYPE E;    // amount of edges
    Bag **adj;         // array of bags of adjacent vertices

} Digraph;


// initialise a graph
Digraph* createDigraph(VERTEX_TYPE V);

void addEdge(Digraph *graph, VERTEX_TYPE from, VERTEX_TYPE to, WEIGHT_TYPE weight);

void deleteGraph(Digraph *graph);

#endif //COMBINATORICAL_OPTIMIZATIONAL_DIGRAPH_H
