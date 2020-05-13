// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#ifndef COMBINATORICAL_OPTIMIZATIONAL_BAG_H
#define COMBINATORICAL_OPTIMIZATIONAL_BAG_H
#include <malloc.h>

// Simple iterable stack-like structure
typedef struct Bag {
    void        *element;      // pointer to an element of Bag
    struct Bag  *next;         // pointer to the next element
} Bag;


/* Get next element of the Bag.
 * Complexity - O(1)
 */
Bag* getNext(Bag *bag);

/* Add a new element to the bag. Complexity - O(1)
 * @param bag: pointer to the bag. Could be null which means bag with no elements.
 * @param element: any pointer to the any type of data
 */
Bag* addElement(Bag *bag, void *element);

/* Delete entire Bag and free all the allocated memory.
 * Complexity - O(n) where n is amount of the elements
 *        (if elements could be deleted in O(1) time)
 */
void deleteBag(Bag *bag);

#endif //COMBINATORICAL_OPTIMIZATIONAL_BAG_H
