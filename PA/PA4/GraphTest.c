/*
* Robert Loquinario
* rloquina
* GraphTest.c
* cmps101 pa4
*/

#include<stdio.h>
#include"Graph.h"

//test the graph
int main(int argc, char* argv[]) {
    int n = 15;
    Graph A = newGraph(n);

    printf("Order: %d\n", getOrder(A));
    printf("Size: %d\n", getSize(A));
    printf("Source: %d\n", getSource(A));
    printf("Parent of 1: %d\n", getParent(A, 1));
    printf("Distance to source: %d\n", getDist(A, 1));
    printf("\n");

    addEdge(A,1,2);
    addEdge(A,2,3);
    addEdge(A,3,5);
    addEdge(A,1,6);
    addEdge(A,2,5);
    addEdge(A,4,3);
    addEdge(A,10,2);
    addEdge(A,7,3);
    addEdge(A,11,5);
    addEdge(A,14,1);
    addEdge(A,15,2);
    addEdge(A,9,7);
    addEdge(A,13,1);

    printGraph(stdout, A);

    printf("Size: %d\n", getSize(A));
    printf("Source: %d\n", getSource(A));
    printf("Parent of 1: %d\n", getParent(A, 1));
    printf("Distance to source: %d\n", getDist(A, 1));
    printf("\n");

    addEdge(A,1,2);
    printGraph(stdout, A);
    printf("Size: %d\n", getSize(A));
    addArc(A,5,4);
    printGraph(stdout, A);
    printf("Size: %d\n", getSize(A));
    addArc(A,4,5);
    printGraph(stdout, A);
    printf("Size: %d\n", getSize(A));

    printf("\n");

    BFS(A,1);
    printf("Source: %d\n", getSource(A));
    for(int i =  1; i <=5 ;i++){
        printf("Parent of %d: %d\n", i, getParent(A, i));
        printf("Distance (1,%d): %d\n", i, getDist(A, i));
    }

    printf("\n");

    printf("Parent of 2: %d\n", getParent(A, 2));
    printf("Distance (2,15): %d\n", getDist(A, 15));

    printf("\n");

    BFS(A,3);
    printf("Source: %d\n", getSource(A));
    for(int i = 1; i <= 5; i++){
        printf("Parent of %d: %d\n", i, getParent(A, i));
        printf("Distance (1,%d): %d\n", i, getDist(A, i));
    }

    printf("\n");

    makeNull(A);
    printGraph(stdout, A);
    printf("Order: %d\n", getOrder(A));
    printf("Size: %d\n", getSize(A));
    printf("Source: %d\n", getSource(A));

    freeGraph(&A);

    return(0);
}