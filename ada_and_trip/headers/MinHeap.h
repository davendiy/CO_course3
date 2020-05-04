// Created by David Zashkolny on 02.05.2020.
// 2 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#ifndef COMBINATORICAL_OPTIMIZATIONAL_MINHEAP_H
#define COMBINATORICAL_OPTIMIZATIONAL_MINHEAP_H

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>

#define HEAP_INDEX_TYPE size_t

// -------------------- Abstract MinHeap implementation -----------------------

typedef void* MinHeapEl;

typedef struct MinHeap {
    MinHeapEl* harr;
    HEAP_INDEX_TYPE  capacity;
    HEAP_INDEX_TYPE  heap_size;
    MinHeapEl (*getMin)(void);
    MinHeapEl (*getMax)(void);
    int8_t (*compare)(MinHeapEl a, MinHeapEl b);
    void (*delete)(MinHeapEl* harr, HEAP_INDEX_TYPE heap_size);
} MinHeap;

// to heapify a subtree with the root at given index
void MinHeapify(MinHeap* minHeap, HEAP_INDEX_TYPE root);

HEAP_INDEX_TYPE parent(HEAP_INDEX_TYPE i);

// to get index of left child of node at index i
HEAP_INDEX_TYPE left(HEAP_INDEX_TYPE i);

// to get index of right child of node at index i
HEAP_INDEX_TYPE right(HEAP_INDEX_TYPE i);

// to extract the root which is the minimum element
MinHeapEl extractMin(MinHeap* minHeap);

int8_t isEmptyMinHeap(MinHeap* minHeap);

// Decreases key value of key at index i to new_val
void decreaseKeyMinHeap(MinHeap* minHeap, HEAP_INDEX_TYPE i, MinHeapEl new_val);

// Returns the minimum key (key at root) from min heap
MinHeapEl getMin(MinHeap* minHeap);

// Deletes a key stored at index i
void deleteKey(MinHeap* minHeap, HEAP_INDEX_TYPE index);

// Inserts a new key 'k'
void insertKey(MinHeap* minHeap, MinHeapEl key);

void deleteHeap(MinHeap* minHeap);


// ----------------------- MinHeap with integer pairs -------------------------

typedef struct IntPair {
    int priority;
    int data;
} IntPair;

static int8_t compareIntPair(MinHeapEl x, MinHeapEl y);

static MinHeapEl getMinIntPair();

static MinHeapEl getMaxIntPair();

static void deleteIntHeap(MinHeapEl* harr, HEAP_INDEX_TYPE heap_size) ;

MinHeapEl createIntPair(int priority, int el);

MinHeap* MinHeapIntPairs(HEAP_INDEX_TYPE capacity);

#endif //COMBINATORICAL_OPTIMIZATIONAL_MINHEAP_H
