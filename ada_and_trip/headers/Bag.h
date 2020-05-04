// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#ifndef COMBINATORICAL_OPTIMIZATIONAL_BAG_H
#define COMBINATORICAL_OPTIMIZATIONAL_BAG_H
#include <malloc.h>


typedef struct Bag {
    void        *element;      // pointer to an element of Bag
    struct Bag  *next;         // pointer to the next element
} Bag;


Bag* getNext(Bag *bag);

Bag* addElement(Bag *bag, void *element);

void deleteBag(Bag *bag);

#endif //COMBINATORICAL_OPTIMIZATIONAL_BAG_H
