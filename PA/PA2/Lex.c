/*
* Robert Loquinario
* rloquina
* pa2
* Lex.c
*
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "List.h"

#define MAX_NUM 255
int main (int argc, char *argv[]) {
	if(argc != 3) {
		printf("Lex needs two arguments");
		return 1;
	}


	// char *input = malloc(strlen(argv[1] + 5));
	// char *output = malloc(strlen(argv[2] + 5));
	// sprintf(input, "%s", argv[1]);
	// sprintf(output, "%s", argv[2]);

	FILE *file;

	char buffer[MAX_NUM];

	file = fopen(argv[1], "r");

	if(file == NULL){
		perror("There is no input file");
	}
	int lines = 0;

	while(fgets(buffer, MAX_NUM, (FILE*) file) != NULL) {
		++lines;
	}

	char **fileArray = (char **)malloc(lines *sizeof(char *));
	for(int i = 0; i < lines; i++)
		fileArray[i] = (char *)malloc(MAX_NUM * sizeof(char));
	
	//start from the top
	rewind(file);

	int count = 0;
	
	while(fgets(buffer, MAX_NUM, file) != NULL) {
		strcpy(fileArray[count++], buffer);
	}
	fclose(file);

	List L = newList();
	append(L,0);

	for (int i = 1; i < lines; i++) {
        moveFront(L);

        while (index(L) >= 0) {
            if(strcmp(fileArray[i],fileArray[get(L)]) < 0) {
                if(index(L) == 0) {
                    prepend(L,i);
                    break;
                }
                insertBefore(L,i);
                break;
            }
            moveNext(L);
        }

        if(index(L) < 0) {
            append(L,i);
        }

    }

	moveFront(L);

	file = fopen(argv[2], "w");

	if(file == NULL) {
		perror("There is no output file");
		exit(1);
	}
	for (int i = 0; i < lines; ++i) {
		fprintf(file, "%s", fileArray[get(L)]);
		moveNext(L);
	}

	for(int i = 0; i < lines; i++) {
		free(fileArray[i]);
	}
	free(fileArray);

	fclose(file);

	freeList(&L);

}