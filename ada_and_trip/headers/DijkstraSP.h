// Created by David Zashkolny on 02.05.2020.
// 2 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

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

int8_t hasPathTo(DijkstraRet* self, VERTEX_TYPE v);

Bag* pathTo(DijkstraRet* self, VERTEX_TYPE v);

VERTEX_TYPE mostDistantAmount(Digraph* G, DijkstraRet* self);

//static void check(DijkstraRet* object, Digraph G, VERTEX_TYPE source);

#endif //COMBINATORICAL_OPTIMIZATIONAL_DIJKSTRASP_H
