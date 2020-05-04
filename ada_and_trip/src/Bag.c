// Created by David Zashkolny on 02.05.2020.
// 2 course, comp math
// Taras Shevchenko National University of Kyiv
// email: davendiy@gmail.com
//

#include "../headers/Bag.h"


Bag* getNext(Bag *bag) {
    if (bag == NULL)
        return NULL;
    else
        return bag->next;
}


Bag* addElement(Bag *bag, void *element) {
    Bag *newElement = (Bag*) malloc(sizeof(Bag));
    newElement->element = element;
    newElement->next = bag;
    return newElement;
}

void deleteBag(Bag *bag) {
    while (bag != NULL) {
        Bag *tmp = bag;
        bag = bag->next;
        free(tmp);
    }
}