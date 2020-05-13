// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/MinHeap.h"

// -------------------- Abstract MinHeap implementation -----------------------
HEAP_INDEX_TYPE parent(HEAP_INDEX_TYPE i) {
    return (i-1)/2;
}

// to get index of left child of node at index i
HEAP_INDEX_TYPE left(HEAP_INDEX_TYPE i) { return (2 * i + 1); }

// to get index of right child of node at index i
HEAP_INDEX_TYPE right(HEAP_INDEX_TYPE i) { return (2 * i + 2); }


// Returns the minimum key (key at root) from min heap
MinHeapEl getMin(MinHeap* minHeap) {
    return minHeap->harr[0];
}


void swap(MinHeapEl* x, MinHeapEl* y){
    MinHeapEl tmp = (*x);
    (*x) = (*y);
    (*y) = tmp;
}


void bubbleUp(MinHeap* minHeap, HEAP_INDEX_TYPE i) {

    while (i != 0 && minHeap->compare(minHeap->harr[parent(i)],
                                          minHeap->harr[i]
                                      ) > 0) {
        swap(&(minHeap->harr[i]), &(minHeap->harr[parent(i)]));
        i = parent(i);
    }
}

void insertKey(MinHeap* minHeap, MinHeapEl key) {
    if (minHeap == NULL){
        fprintf(stderr, "Null pointer instead of minHeap.\n");
        return;
    }
    if (minHeap->heap_size == minHeap->capacity) {
        fprintf(stderr, "Overflow: could not insert the key.\n");
        return;
    }
    minHeap->heap_size++;
    HEAP_INDEX_TYPE i = minHeap->heap_size - 1;
    minHeap->harr[i] = key;

    // Fix the min heap property if it is violated
    bubbleUp(minHeap, i);
}


void decreaseKeyMinHeap(MinHeap* minHeap, HEAP_INDEX_TYPE i, MinHeapEl new_val) {
    minHeap->harr[i] = new_val;
    bubbleUp(minHeap, i);
}

MinHeapEl extractMin(MinHeap* minHeap) {
    if (minHeap->heap_size < 0) return minHeap->getMax();
    if (minHeap->heap_size == 1) {
        minHeap->heap_size--;
        return minHeap->harr[0];
    }

    MinHeapEl root = minHeap->harr[0];
    minHeap->harr[0] = minHeap->harr[minHeap->heap_size - 1];
    minHeap->heap_size--;
    MinHeapify(minHeap, 0);
    return root;
}

void deleteKey(MinHeap* minHeap, HEAP_INDEX_TYPE i) {
    decreaseKeyMinHeap(minHeap, i, minHeap->getMin());
    extractMin(minHeap);
}

void MinHeapify(MinHeap* minHeap, HEAP_INDEX_TYPE i) {
    HEAP_INDEX_TYPE left_i = left(i);
    HEAP_INDEX_TYPE right_i = right(i);
    HEAP_INDEX_TYPE smallest = i;
    if (left_i < minHeap->heap_size && minHeap->compare(minHeap->harr[left_i],
                                                        minHeap->harr[i]) < 0)
        smallest = left_i;
    if (right_i < minHeap->heap_size && minHeap->compare(minHeap->harr[right_i],
                                                         minHeap->harr[smallest]) < 0)
        smallest = right_i;
    if (smallest != i) {
        swap(&(minHeap->harr[i]), &(minHeap->harr[smallest]));
        MinHeapify(minHeap, smallest);
    }
}

void deleteHeap(MinHeap* minHeap) {
    minHeap->delete(minHeap->harr, minHeap->heap_size);
    free(minHeap);
}

char isEmptyMinHeap(MinHeap* minHeap) {
    return minHeap->heap_size == 0;
}


// ----------------------- MinHeap with integer pairs -------------------------

static char compareIntPair(MinHeapEl x, MinHeapEl y) {
    IntPair *_x = (IntPair*) x;
    IntPair *_y = (IntPair*) y;
    if (_x->priority > _y->priority) return 1;
    if (_x->priority < _y->priority) return -1;
    else                             return 0;
}

static MinHeapEl getMinIntPair() {
    IntPair *res = (IntPair*) malloc(sizeof(IntPair));
    res->priority = INT_MIN;
    res->data = 0;
    return res;
}

static MinHeapEl getMaxIntPair() {
    IntPair *res = (IntPair*) malloc(sizeof(IntPair));
    res->priority = INT_MAX;
    res->data = 0;
    return res;
}

MinHeapEl createIntPair(int priority, int el) {
    IntPair *res = (IntPair*) malloc(sizeof(IntPair));
    res->priority = priority;
    res->data = el;
    return res;
}

static void deleteIntHeap(MinHeapEl* harr, HEAP_INDEX_TYPE heap_size) {
    for (HEAP_INDEX_TYPE i = 0; i < heap_size; i++)
        free((IntPair*) harr[i]);

}


MinHeap* MinHeapIntPairs(HEAP_INDEX_TYPE capacity) {
    MinHeap* res = (MinHeap*) malloc(sizeof(MinHeap));
    res->capacity = capacity;
    res->heap_size = 0;
    res->compare = &compareIntPair;
    res->getMin = &getMinIntPair;
    res->getMax = &getMaxIntPair;
    res->harr = (MinHeapEl*) malloc(capacity * sizeof(MinHeapEl));
    res->delete = &deleteIntHeap;
    return res;
}
