// Created by David Zashkolny on 03.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//
/**
 *  This module represents an indexed priority queue of generic keys.
 *  It supports the usual __insert__ and __delete-the-minimum__
 *  operations, along with __delete__ and __change-the-key__
 *  methods. In order to let the client refer to keys on the priority queue,
 *  an integer between {0} and {maxN - 1}
 *  is associated with each key—the client uses this integer to specify
 *  which key to delete or change.
 *  It also supports methods for peeking at the minimum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *
 *  This implementation uses a binary heap along with an array to associate
 *  keys with integers in the given range.
 *  The __insert__, __delete-the-minimum__, __delete__,
 *  __change-key__, __decrease-key__, and __increase-key__
 *  operations take &Theta(log n) time in the worst case,
 *  where n is the number of elements in the priority queue.
 *  Construction takes time proportional to the specified capacity.
 *
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Oridinal java code author: Robert Sedgewick
 *  Oridinal java code author: Kevin Wayne
 */

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

/**
 * Initializes an empty indexed priority queue with indices between {0}
 * and {maxN - 1}.
 * @param  maxN - the keys on this priority queue are index from {@code 0}
 *         {@code maxN - 1}
 */
IndexMinPq* createIndexMinPq(PQ_INDEX_TYPE maxN);

/* Returns true if this priority queue is empty.*/
char isEmptyPQ(IndexMinPq* minPq);

/* Is {i} an index on this priority queue? */
char contains(IndexMinPq* minPq, PQ_INDEX_TYPE i);

/* Returns the number of keys on this priority queue. */
PQ_INDEX_TYPE size(IndexMinPq* minPq);

/**
 * Associates key with index {@code i}.
 *
 * @param minPq - a min Priority queue
 * @param i     - an index
 * @param key   - the key to associate with index code i
 */
void insert(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);

/* Removes a minimum key and returns its associated index. */
PQ_INDEX_TYPE delMin(IndexMinPq* minPq);

/* Returns a minimum key. */
PQ_KEY_TYPE minKey(IndexMinPq* minPq);

/* Returns an index associated with a minimum key.*/
PQ_INDEX_TYPE minIndex(IndexMinPq* minPq);

/* Returns the key associated with index {i}. */
PQ_KEY_TYPE keyOf(IndexMinPq* minPq, PQ_INDEX_TYPE i);

/* Change the key associated with index {i} to the specified value. */
void changeKey(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);

/* Decrease the key associated with index {@code i} to the specified value. */
void decreaseKey(IndexMinPq* minPq, PQ_INDEX_TYPE i, PQ_KEY_TYPE key);


#endif //COMBINATORICAL_OPTIMIZATIONAL_INDEXMINPRIORITYQUEUE_H
