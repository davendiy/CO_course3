// Created by David Zashkolny on 03.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/IndexMinPriorityQueue.h"
#include <stdio.h>

// -----------------------HELPER MINPQ FUNCS------------------------------------
static void swim(IndexMinPq* minPq, PQ_INDEX_TYPE k);

static void sink(IndexMinPq* minPq, PQ_INDEX_TYPE k);

static void exch(IndexMinPq * minPq, PQ_INDEX_TYPE i, PQ_INDEX_TYPE j);


void validateIndex(IndexMinPq* minPq, PQ_INDEX_TYPE i) {
    if (i < 0) fprintf(stderr, "Index is negative.");
    if (i > minPq->maxN) fprintf(stderr, "Index is greater than capacity of the minQueue.");
}
// -----------------------------------------------------------------------------

/**
 * Initializes an empty indexed priority queue with indices between {0}
 * and {maxN - 1}.
 * @param  maxN - the keys on this priority queue are index from {@code 0}
 *         {@code maxN - 1}
 */
IndexMinPq *createIndexMinPq(PQ_INDEX_TYPE capacity) {
    if (capacity < 0) fprintf(stderr, "Can't create Min Priority Queue with negative index.");
    IndexMinPq* res = (IndexMinPq*) malloc(sizeof(IndexMinPq));
    res->n = 0;
    res->maxN = capacity;
    res->keys = (PQ_KEY_TYPE*) malloc((capacity + 1) * sizeof(PQ_KEY_TYPE));
    res->pq = (PQ_INDEX_TYPE*) malloc((capacity + 1) * sizeof(PQ_INDEX_TYPE));
    res->qp = (PQ_INDEX_TYPE*) malloc((capacity + 1) * sizeof(PQ_INDEX_TYPE));
    for (PQ_INDEX_TYPE i = 0; i <= capacity; i++)
        res->qp[i] = -1;
    return res;
}

/**
 * Associates key with index {@code i}.
 *
 * @param minPq - a min Priority queue
 * @param i     - an index
 * @param key   - the key to associate with index code i
 */
void insert(IndexMinPq *minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key) {
    validateIndex(minPq, i);
    if (contains(minPq, i)) fprintf(stderr, "Index %ld is already in PQ", i);
    minPq->n++;
    minPq->qp[i] = minPq->n;
    minPq->pq[minPq->n] = i;
    minPq->keys[i] = key;
    swim(minPq, minPq->n);
}

/* Returns the number of keys on this priority queue. */
PQ_INDEX_TYPE size(IndexMinPq *minPq) {
    return minPq->n;
}

/* Is {i} an index on this priority queue? */
char contains(IndexMinPq *minPq, PQ_INDEX_TYPE i) {
    validateIndex(minPq, i);
    return minPq->qp[i] != -1;
}

/* Returns true if this priority queue is empty.*/
char isEmptyPQ(IndexMinPq *minPq) {
    return minPq->n == 0;
}

/* Returns an index associated with a minimum key.*/
PQ_INDEX_TYPE minIndex(IndexMinPq *minPq) {
    if (minPq->n == 0) fprintf(stderr, "Priority queue underflow.");
    return minPq->pq[1];
}

/* Returns a minimum key. */
PQ_KEY_TYPE minKey(IndexMinPq *minPq) {
    if (minPq->n == 0) fprintf(stderr, "Priority queue underflow.");
    return minPq->keys[minPq->pq[1]];
}

/* Removes a minimum key and returns its associated index. */
PQ_INDEX_TYPE delMin(IndexMinPq *minPq) {
    if (minPq->n == 0) fprintf(stderr, "Priority queue underflow.");
    PQ_INDEX_TYPE min = minPq->pq[1];
    exch(minPq, 1, minPq->n--);
    sink(minPq, 1);
    minPq->qp[min] = -1;
    minPq->keys[min] = -1;
    minPq->pq[minPq->n+1] = -1;
    return min;
}

/* Returns the key associated with index {i}. */
PQ_KEY_TYPE keyOf(IndexMinPq *minPq, PQ_INDEX_TYPE i) {
    validateIndex(minPq, i);
    return minPq->keys[i];
}

/* Change the key associated with index {i} to the specified value. */
void changeKey(IndexMinPq *minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key) {
    validateIndex(minPq, i);
    if (!contains(minPq, i)) fprintf(stderr, "Index is not in the priority queue to be changed.");
    minPq->keys[i] = key;
    swim(minPq, minPq->qp[i]);
    sink(minPq, minPq->qp[i]);
}

/* Decrease the key associated with index {@code i} to the specified value. */
void decreaseKey(IndexMinPq *minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key) {
    validateIndex(minPq, i);
    if (!contains(minPq, i)) fprintf(stderr, "Index is not in the priority queue to be changed.");
    if (minPq->keys[i] < key) fprintf(stderr, "Decreased index is bigger than the new key.");
    if (minPq->keys[i] == key) fprintf(stderr, "Decreased index is equal to the new key.");

    minPq->keys[i] = key;
    swim(minPq, minPq->qp[i]);
}

// -----------------------HELPER MINPQ FUNCS------------------------------------
char greater(IndexMinPq *minPq, PQ_INDEX_TYPE i, PQ_INDEX_TYPE j) {
    return minPq->keys[minPq->pq[i]] > minPq->keys[minPq->pq[j]];
}

static void swim(IndexMinPq *minPq, PQ_INDEX_TYPE k) {
    while (k > 1 &&  greater(minPq, k/2, k)) {
        exch(minPq, k, k/2);
        k = k/2;
    }
}

static void sink(IndexMinPq *minPq, PQ_INDEX_TYPE k) {
    while (2 * k <= minPq->n) {
        PQ_INDEX_TYPE j = 2 * k;
        if (j < minPq->n && greater(minPq, j, j+1)) j++;
        if (!greater(minPq, k, j)) break;
        exch(minPq, k, j);
        k = j;
    }
}


static void exch(IndexMinPq * minPq, PQ_INDEX_TYPE i, PQ_INDEX_TYPE j) {
    PQ_INDEX_TYPE swap = minPq->pq[i];
    minPq->pq[i] = minPq->pq[j];
    minPq->pq[j] = swap;
    minPq->qp[minPq->pq[i]] = i;
    minPq->qp[minPq->pq[j]] = j;
}
