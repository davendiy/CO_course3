// Created by David Zashkolny on 03.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#ifndef COMBINATORICAL_OPTIMIZATIONAL_INDEXMINPRIORITYQUEUE_H
#define COMBINATORICAL_OPTIMIZATIONAL_INDEXMINPRIORITYQUEUE_H

#include <stdlib.h>

typedef size_t PQ_INDEX_TYPE;
typedef int PQ_KEY_TYPE;

typedef struct IndexMinPq {
    PQ_INDEX_TYPE maxN;        // maximum number of elements on PQ
    PQ_INDEX_TYPE n;           // number of elements on PQ
    PQ_INDEX_TYPE* pq;         // binary heap using 1-based indexing
    PQ_INDEX_TYPE* qp;         // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    PQ_KEY_TYPE* keys;         // keys[i] = priority of i

} IndexMinPq;


IndexMinPq* createIndexMinPq(PQ_INDEX_TYPE capacity);

char isEmptyPQ(IndexMinPq* minPq);

char contains(IndexMinPq* minPq, PQ_INDEX_TYPE i);

PQ_INDEX_TYPE size(IndexMinPq* minPq);

void insert(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);

static void swim(IndexMinPq* minPq, PQ_INDEX_TYPE k);

static void sink(IndexMinPq* minPq, PQ_INDEX_TYPE k);

static void exch(IndexMinPq * minPq, PQ_INDEX_TYPE i, PQ_INDEX_TYPE j);

PQ_INDEX_TYPE delMin(IndexMinPq* minPq);

PQ_KEY_TYPE minKey(IndexMinPq* minPq);

PQ_INDEX_TYPE minIndex(IndexMinPq* minPq);

PQ_KEY_TYPE keyOf(IndexMinPq* minPq, PQ_INDEX_TYPE i);

void changeKey(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);

void decreaseKey(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);


#endif //COMBINATORICAL_OPTIMIZATIONAL_INDEXMINPRIORITYQUEUE_H
