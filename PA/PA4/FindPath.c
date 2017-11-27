/* 
*  Robert Loquinario
*  rloquina
*  cmps101 pa4
*/

#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"
#include "List.h"

int main (int argc, char* argv[]) {
	//Check if correct args
	if(argc != 3){
		printf("Please include two arguments <in> <out>");
		exit(1);
	}
	// setting vars to read and write
	FILE *in, *out;
	in = fopen(argv[1], "r");
	out = fopen(argv[2], "w");
	if(in == NULL) {
		printf("Can't open %s for reading", argv[1]);
		exit(1);
	}
	if(out == NULL) {
		printf("Can't open %s for writing", argv[2]);
		exit(1);
	}

	// find size of graph
	int vertice;
	fscanf(in, "%d", &vertice);
	Graph G = newGraph(vertice);

	// read edges
	int edge1, edge2;
	while(fscanf(in, "%d %d", &edge1, &edge2) == 2){
		if(edge1 == 0 && edge2 == 0) break;
		addEdge(G, edge1, edge2);
	}

	// print Graph
	printGraph(out, G);

	// vars for source and destination
	List L = newList();
	int source, destination;
	// now to read input
	while(fscanf(in, "%d %d", &source, &destination)) {
		if(source == 0 || destination== 0) break;
		BFS(G,source);
		getPath(L, G, destination);
		if(source == destination){
			fprintf(out, "\nThe distance from %d to %d is 0\n", source, destination);
			fprintf(out, "A shortest %d-%d path is:", source, destination);
			printList(out, L);
			fprintf(out, "\n");
		} else if(length(L) -1 == 0) {
			fprintf(out, "\nThe distance from %d to %d is infinity\n", source, destination);
			fprintf(out, "No %d-%d path exists\n", source, destination);
		} else {
			fprintf(out, "\nThe distance from %d to %d is %d\n", source, destination, length(L) - 1);
			fprintf(out, "A shortest %d-%d path is:", source, destination);
			printList(out, L);
			fprintf(out, "\n");
		}
		clear(L);
	}



	freeList(&L);
	freeGraph(&G);
    fclose(in);
    fclose(out);

    return 0;
}
