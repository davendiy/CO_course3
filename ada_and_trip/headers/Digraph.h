// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com

/* Implementation of the Directed Graph structure as a list of adjacent
 * vertices.
 */

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
    VERTEX_TYPE V;     // amount of vertices
    VERTEX_TYPE E;     // amount of edges
    Bag **adj;         // array of bags of adjacent vertices

} Digraph;


// initialise a graph with V vertices.
Digraph* createDigraph(VERTEX_TYPE V);

/* Add a new Directed Edge to the Digraph. Complexity = O(1)
 * @param from: index of the vertex-source
 * @param to:   index of the vertex-destination
 * @param weight: obviously
 */
void addEdge(Digraph *graph, VERTEX_TYPE from, VERTEX_TYPE to, WEIGHT_TYPE weight);

/* Delete the entire Digraph and free all its allocated memory.
 * Complexity - O(V + E)
 */
void deleteGraph(Digraph *graph);

#endif //COMBINATORICAL_OPTIMIZATIONAL_DIGRAPH_H
