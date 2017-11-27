/*
* Robert Loquinario
* rloquina
* pa2
* List.c
*/

#include <stdlib.h>
#include "List.h"

typedef struct NodeObj {
    int data;
    struct NodeObj* next;
    struct NodeObj* prev;
} NodeObj;

// private Node type
typedef NodeObj* Node;

typedef struct ListObj {
    Node front;
    Node back;
    Node cursor;
    int index;
    int length;

} ListObj;

void freeNode(Node* n) {
    if (n != NULL && *n != NULL) {
        free(*n);
        *n = NULL;
    }
}

Node newNode(int data) {
    Node a = malloc(sizeof(NodeObj));
    a->data = data;
    a->prev = a->next = NULL;
    return (a);
}


List newList(void) {
    List L = malloc(sizeof(ListObj));
    L->front = L->back = L->cursor = NULL;
    L->index = -1;
    L->length = 0;
    return(L);
}


void freeList(List* pL) {
    if(pL != NULL && *pL != NULL) {
        clear(*pL);
        free(*pL);
        *pL = NULL;
    }
}

// Access functions -----------------------------------------------------------

//Returns the length of the list
int length(List L) {
    if(L == NULL) {
        printf("List Error: length() on NULL List reference\n");
        exit(1);
    }
    return(L->length);
}

// returns the index the cursor is on
int index(List L) {
    if(L == NULL) {
        printf("List Error: calling index() on NULL List reference\n");
        exit(1);
    }
    return(L->index);
}
// gets the index of the front of the list
int front(List L) {
    if(L == NULL) {
        printf("List Error: calling front() on NULL List reference\n");
        exit(1);
    } else if(L->length == 0) {
        printf("List Error: calling front() on an empty list\n");
        exit(1);
    }
    if (L->length > 0) {
        return L->front->data;
    }
    return -1;
}

int back(List L) {
    if(L == NULL) {
        printf("List Error: calling front() on NULL List reference\n");
        exit(1);
    }else if(L->length == 0) {
        printf("List Error: calling front() on an empty list\n");
        exit(1);
    }
    if (L->length > 0) {
        return L->back->data;
    }
    return -1;
}

int get(List L) {
    if(L == NULL) {
        printf("List Error: calling getElement() on NULL List reference\n");
        exit(1);
    }else if(L->length == 0) {
        printf("List Error: calling getElement() on empty List\n");
        exit(1);
    }else if(L->index == -1) {
        printf("List Error: calling getElement() on NULL cursor reference\n");
        exit(1);
    }
    if (L->length > 0) {
        return L->cursor->data;
    }
    return -1;
}

int equals(List A, List B) {
    Node firstList = A->front;
    Node secondList = B->front;
    if(firstList == NULL && secondList == NULL) {
        return 1;
    }
    if (A->length != B->length) {
        return 0;
    }
    if (A->back->data != B->back->data) {
        return 0;
    }
    while (firstList->next != NULL) {
        if (firstList->data != secondList->data) {
            return 0;
        }

        firstList = firstList->next;
        secondList = secondList->next;
    }
    return 1;
}

// Manipulation procedures ----------------------------------------------------
void clear(List L) {
    if(L == NULL) {
        return;
    }
    while( L->front != NULL) {
        deleteFront(L);
    }
}

void moveFront(List L) {
    if (L->length > 0) {
        L->cursor = L->front;
        L->index = 0;
    }
}

void moveBack(List L) {
    if (L->length > 0) {
        L->cursor = L->back;
        L->index = L->length - 1;
    }
}

void movePrev(List L) {
    if(L == NULL) {
        printf("List Error: calling movePrev() on NULL List reference\n");
        return;
    }
    if (L->cursor != NULL && L->cursor != L->front) {
        L->cursor = L->cursor->prev;
        --L->index;

    } else if (L->cursor != NULL && L->cursor == L->front) {
        L->cursor = NULL;
        L->index = -1;
    }
}

void moveNext(List L) {
    if(L == NULL) {
        printf("List Error: calling moveNext() on NULL List reference\n");
        return;
    }
    if (L->cursor != NULL && L->cursor != L->back) {
        L->cursor = L->cursor->next;
        ++L->index;
    } else if (L->cursor != NULL && L->cursor == L->back) {
        L->cursor = NULL;
        L->index = -1;
    }
}

void prepend(List L, int data) {
    if(L == NULL) {
        printf("List Error: calling prepend() on NULL List reference\n");
        return;
    }
    Node node = newNode(data);
    if (L->length == 0) {
        L->front = L->back = node;
    } else {
        L->front->prev = node;
        node->next = L->front;
        L->front = node;
    }
    ++L->length;
    ++L->index;
    

}

void append(List L, int data) {
    if(L == NULL) {
        printf("List Error: calling append() on NULL List reference\n");
        return;
    }
    Node node = newNode(data);
    if (L->length == 0) {
        L->front = L->back = node;
    } else {
        L->back->next = node;
        node->prev = L->back;
        L->back = node;
    }
    L->length ++;
}

void insertBefore(List L, int data) {
    if(L->length <= 0) {
        return;
    } else if (index(L) < 0) {
        return;
    }
    Node newNd = newNode(data);
    if (index(L) == 0) {
        prepend(L, data);
    } else {
        (newNd)->prev = L->cursor->prev;
        (newNd)->next = L->cursor;
        L->cursor->prev->next = (newNd);
        L->cursor->prev = (newNd);
        ++L->length;
        ++L->index;
    }

}

void insertAfter(List L, int data) {
    if(L->length <= 0) {
        return;
    } else if (index(L) < 0) {
        return;
    }
    Node newNd = newNode(data);
    if (index(L) == L->length - 1) {
        append(L, data);
    } else {
        (newNd)->data = data;
        (newNd)->next = L->cursor->next;
        (newNd)->prev = L->cursor;
        L->cursor->next =  (newNd);
        ++L->length;
    }
}

void deleteFront(List L) {
    Node N = L->front;
    if(L->length == 1) {
        L->front = L->back = L->cursor = NULL;
        L->index = -1;
        L->length = 0;
    }
    else {
        if(L->cursor == L->front) {
            L->cursor = NULL;
            L->index = -1;
        } else if(L->cursor != NULL) {
            --L->index;
        }
        L->front = L->front->next;
        L->front->prev = NULL;
        --L->length;
    }
    freeNode(&N);
}

void deleteBack(List L) {
    Node N = L->back;
    if(L->length == 1) {
        L->front = L->back = L->cursor = NULL;
        L->index = -1;
        L->length = 0;
    } else {
        if(L->cursor == L->back) {
            L->cursor = NULL;
            L->index = -1;
        }
        
        L->back = L->back->prev;
        if(L->back != NULL) {
            L->back->next= NULL;
        }
        --L->length;
    }
    freeNode(&N);
}

void delete(List L) {
    Node temp = NULL;
    if(L==NULL){
        return;
    } else if(L->length == 0){
        return;
    } else if(L->index < 0) {
        return;
    }
    temp = L->cursor;
    if (L->length > 0 && L->index >= 0) {
        if(L->cursor == L->back) {
            L->back = L->cursor->prev;
            L->back->next = NULL;
        } else if(L->cursor == L->front) {
            L->front = L->cursor->next;
            L->front->prev = NULL;
        } else {
            
            L->cursor->next->prev = L->cursor->prev;
            L->cursor->prev->next = L->cursor->next;
            
            
        }
        L->index = -1;
        L->cursor = NULL;
        --L->length;
    }
    freeNode(&temp);
}

// Other operations -----------------------------------------------------------

void printList(FILE *out, List L) {

    Node temp = L->front;

    if(out == NULL) {
        for (int i = 0; i < L->length; i++) {
            printf(" %d ", temp->data);
            temp = temp->next;
        }
    }
    else {
        for (int i = 0; i < L->length; i++) {
            fprintf(out," %d ", temp->data);
            temp = temp->next;
        }
    }
}

List copyList(List L) {

    List temp = newList();
    if(L->length == 0) {
        return temp;
    }
    Node tempNode = L->front;
    while (tempNode->next != NULL)
    {
        append(temp,tempNode->data);
        tempNode = tempNode->next;
    }

    append(temp,tempNode->data);

    return temp;
}
