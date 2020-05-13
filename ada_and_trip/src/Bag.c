// Created by David Zashkolny on 02.05.2020.
// 3 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/Bag.h"

/* Get next element of the Bag.
 * Complexity - O(1)
 */
Bag* getNext(Bag *bag) {
    if (bag == NULL)
        return NULL;
    else
        return bag->next;
}

/* Add a new element to the bag. Complexity - O(1)
 * @param bag: pointer to the bag. Could be null which means bag with no elements.
 * @param element: any pointer to the any type of data
 */
Bag* addElement(Bag *bag, void *element) {
    Bag *newElement = (Bag*) malloc(sizeof(Bag));
    newElement->element = element;
    newElement->next = bag;
    return newElement;
}

/* Delete entire Bag and free all the allocated memory.
 */
void deleteBag(Bag *bag) {
    while (bag != NULL) {
        Bag *tmp = bag;
        bag = bag->next;
        free(tmp);
    }
}
