// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

/* Dijkstra's algorithm. Computes the shortest path tree.
 * Assumes all weights are nonnegative.
 *
 * Takes \Theta(E logV) time in the worst case, where E is number of edges in
 * the graph and V is number of vertices. Also uses \Theta(V) extra space (not
 * including edge-weighted graph).
 */

#ifndef COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H
#define COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H

#include "Digraph.h"
#include "IndexMinPriorityQueue.h"
#include "Bag.h"
#include <stdlib.h>
#include "limits.h"

typedef struct DijkstraRet {
    int*  distTo;
    DirectedEdge** edgeTo;
    IndexMinPq * pq;
    int maxDistance;
} DijkstraRet;


DijkstraRet* DijkstraAlgo(Digraph* G, VERTEX_TYPE source);

static void relax(DijkstraRet* self, DirectedEdge* e);

char hasPathTo(DijkstraRet* self, VERTEX_TYPE v);

Bag* pathTo(DijkstraRet* self, VERTEX_TYPE v);

VERTEX_TYPE mostDistantAmount(Digraph* G, DijkstraRet* self);

//static void check(DijkstraRet* object, Digraph G, VERTEX_TYPE source);

#endif //COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H
