/*
* Robert Loquinario
* rloquina
* pa4
* Graph.c
*/

#include <stdlib.h>
#include "Graph.h"
#include "List.h"

typedef struct GraphObj {
    List* adj;
    char* color;
    int* parent;
    int* distance;
    int order, size, source;
} GraphObj;


/**Constructors-Destructors  **/

Graph newGraph(int n) {
    if( n < 1 ) {
        printf("Graph Error: Calling newGraph on n < 1");
        exit(1);
    }
    Graph G = malloc(sizeof(GraphObj));
    G->adj = malloc((n+1)*sizeof(List));
    G->color = malloc((n+1)*sizeof(int));
    G->parent = malloc((n+1)*sizeof(int));
    G->distance = malloc((n+1)*sizeof(int));
    G->order = n;
    G->size = 0;
    G->adj[0] = NULL;
    G->source = NIL;

    for (int i = 1; i <= n; i++) {
        G->adj[i] = newList();
        G->parent[i] = NIL;
        G->distance[i] = INF;
        G->color[i] = 'w';
    }
    return G;
}

void freeGraph(Graph* pG) {
    for (int i = 1; i <=getOrder(*pG); i++)
        freeList(&(*pG)->adj[i]);
    free((*pG)->adj);
    free((*pG)->color);
    free((*pG)->distance);
    free((*pG)->parent);
    (*pG)->adj = NULL;
    (*pG)->color = NULL;
    (*pG)->parent = NULL;
    (*pG)->distance = NULL;
    free(*pG);
    *pG = NULL;
}  

/*** Access functions ***/ 

int getOrder(Graph G) {
    if(G == NULL) {
        printf("Graph Error: calling getOrder on NULL Graph reference");
        exit(1);
    }
    return G->order;
} 
int getSize(Graph G) {
    if(G == NULL) {
        printf("Graph Error: calling getSize on NULL Graph reference");
        exit(1);
    }
    return G->size;
}
int getSource(Graph G) {
    if(G == NULL) {
        printf("Graph Error: calling getSource on NULL Graph reference");
        exit(1);
    }
    return G->source;
}
int getParent(Graph G, int u) {
    if(G == NULL) {
        printf("Graph Error: calling getParent on NULL Graph reference");
        exit(1);
    }
    if( u < 1 || u > G->order){
        printf("Graph ERROR: trying to get parent with invalid parameters!\n");
        exit(1);
    }
    return G->parent[u];
} 
int getDist(Graph G, int u) {
    if(G == NULL) {
        printf("Graph Error: calling getDist on NULL Graph reference");
        exit(1);
    }
    if( G->source == 0){
        return INF;
    }
    return G->distance[u];
}
void getPath(List L, Graph G, int u) {
    if(getSource(G) == NIL) {
        printf("Graph Error: calling getPath before calling BFS");
        exit(1);
    }
    if(getSource(G) == u) {
        append(L, u);
    } else if(G->parent[u] == NIL) {
        append(L, NIL);
    } else {
        getPath(L, G, G->parent[u]);
        append(L, u);
    }
}

/*** Manipulation procedures ***/ 

//deletes G
//restores it to original state
void makeNull(Graph G) {
    if(G == NULL) {
        printf("Graph Error: calling makeNull on NULL Graph reference");
        exit(1);
    } else {
        for(int i = 1; i < getOrder(G); i++) {
            clear(G->adj[i]);
        }
        G->size = 0;
    }
}

//addEdge() adds new edges from u -> v and v-> u
void addEdge(Graph G, int u, int v) {
    if(G == NULL) {
        printf("Graph Error: calling addEdge on NULL Graph reference");
        exit(1);
    }
    if(u < 1 || v < 1 || u > getOrder(G) || v > getOrder(G)){
        printf("Graph Error: addEdge called on bad vertices %d , %d", u, v);
        exit(1);
    }
    addArc(G, u, v);
    addArc(G, v, u);
    G->size--; // -- because addArc is called twice
}

// addArc() adds an arc
// @param: Graph and two vert
// @
void addArc(Graph G, int u, int v) {
    if(G == NULL) {
        printf("Graph Error: calling addArc on NULL Graph reference");
        exit(1);
    }
    if(u < 1 || v < 1 || u > getOrder(G) || v > getOrder(G)){
        printf("Graph Error: addArc called on bad vertices %d , %d", u, v);
        exit(1);
    }
    List L = G->adj[u];
    for(moveFront(L); index(L) != -1; moveNext(L)) {
        if(v < get(L)) {
            insertBefore(L, v);
            break;
        }
    }
    if(index(L) == -1) append(L, v);
    G->size++;
} 

// performs BFS
void BFS(Graph G, int s) {
    if(G == NULL) {
        printf("Graph Error: calling BFS on NULL Graph reference");
        exit(1);
    }
    for( int i = 0; i < G->order + 1; i++ ) {
        G->parent[i] = NIL;
        G->distance[i] = INF;
        G->color[i] = 'w';
    }
    G->source = s;
    G->distance[s] = 0;
    G->color[s] = 'g';
    List q = newList();
    prepend(q, s);
    while(length(q) > 0) {
        int temp = back(q);
        deleteBack(q);
        List adjacent = G->adj[temp];
        for(moveFront(adjacent); index(adjacent) != -1; moveNext(adjacent)) {
            int v = get(adjacent);
            if(G->color[v] == 'w') {
                G->color[v] = 'g';
                G->parent[v] = temp;
                G->distance[v] = G->distance[temp] + 1;
                prepend(q, v);
            }
        }
        G->color[temp] = 'b';
    }
    freeList(&q);
}

/*** Other operations ***/

void printGraph(FILE* out, Graph G) {
    
    if(out == NULL || G == NULL) {
        printf("Graph Error: calling printGraph() ");
        exit(1);
    }

    for(int i = 1; i <= getOrder(G); i++ ) {
        fprintf( out,"%d:", i );
        printList(out, G->adj[i]);
        fprintf(out, "\n");
    }
}