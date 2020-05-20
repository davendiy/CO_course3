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
 * 
 * Oridinal java code author: Robert Sedgewick
 * Oridinal java code author: Kevin Wayne
 */

#ifndef COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H
#define COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H

#include "Digraph.h"
#include "IndexMinPriorityQueue.h"
#include "Bag.h"
#include <stdlib.h>
#include "limits.h"

typedef struct DijkstraRet {
    int*  distTo;    // array[i] with values of the shortest path to i-th vertex
    DirectedEdge** edgeTo;  // wich edge was the last in the shortest path
    IndexMinPq * pq;        // internal min priority queue that allows
    int maxDistance;     // value of the maximum non-INF distance in the distTo
} DijkstraRet;


/**
 * Computes a shortest-paths tree from the source vertex {source} to every other
 * vertex in the edge-weighted digraph {G}.
 *
 * @param  G      - the edge-weighted digraph
 * @param  source - the source vertex
 * @return DijkstraRet structure.
 */
DijkstraRet* DijkstraAlgo(Digraph* G, VERTEX_TYPE source);

// relax edge e and update pq if changed
static void relax(DijkstraRet* self, DirectedEdge* e);

/**
 * Returns true if there is a path from the source vertex to vertex {v}.
 *
 * @param self - resulf of DijkstraAlgo
 * @param      - v the destination verte
 * @return 1 if there is a path from the source vertex
 *         to vertex {v}; 0 otherwise
 */
int hasPathTo(DijkstraRet* self, VERTEX_TYPE v);

/**
 * Returns a shortest path from the source vertex to vertex {v}.
 *
 * @param self - a resulf of the DijkstraAlgo
 * @param  v   - the destination vertex
 * @return a shortest path from the source vertex to vertex {v}
 *         as an iterable of edges, and null if no such path
 */
Bag* pathTo(DijkstraRet* self, VERTEX_TYPE v);

/**
 * Returns the amount of vertices that have the max shortest path.
 *
 * @param G    - Directed graph
 * @param self - a result of the DijkstraAlgo
 *
 * NOTE: you should use {maxDistance} before.
 */
VERTEX_TYPE mostDistantAmount(Digraph* G, DijkstraRet* self);

/**
 * Finds the biggest value in the DijkstraRet.distTo array, i.e. the maximum
 * shortest path from the source vertex to any other.
 * Set the result into res->maxDistance.
 */
void maxDistance(Digraph* G, DijkstraRet* res);

//static void check(DijkstraRet* object, Digraph G, VERTEX_TYPE source);

#endif //COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H
