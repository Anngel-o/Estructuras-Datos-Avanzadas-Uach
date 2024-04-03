#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <ctype.h>

typedef struct snode{
    char key[40];
    char value[100];
    struct snode *next;
} tnode;

FILE *fptr;
#define MAX 8969
short int flagArray[MAX];
tnode *ptrArray[MAX];
char test[6][25];
long int randNumberArray[40] = {174,31485,8093,16066,9011,13682,28784,6388,18147,27348,
                                18741,5411,2229,8156,3591,8404,10224,3523,32194,67,26620,
                                6916,22193,2316,22218,16468,18391,28568,23919,17610,
                                7044,21152,5726,32543,7006,11209,20735,3116,4009,1495};


//prototypes
void printList(long int index);
void maxColissions();
void updateColissionList();
void initArray();
void insertWord(char word[], char clue[]);
void InsertFromFile(char fname[]);
int validateFileExtension(char fname[]);
void InsertFromKeyboad(char word[], char clue[]);
tnode *search(long int index);
tnode *wordExists(char word[], long int index);
void delete(char word[]);
long int hashFunc(char word[]);

int initHash(){
    initArray();
    InsertFromFile("DiccJuego2023.csv");
    char word[40];
}

//functions

void printList(long int index){
    tnode *aux = ptrArray[index];
    printf("\nIndex / elements");
    printf("\n[%ld] = ", index);
    while(aux){
        printf("%s -> ", aux->key);
        aux = aux->next;
    }
    printf("NULL\n");
}

void maxColissions(){
    int max = 0;
    int index;
    fptr = fopen("colisions.txt", "r");
    for(long int i = 0; i < MAX; ++i){
        if(flagArray[i] > max){
          max = flagArray[i];
        } 
    }
    printf("\nMax of collisions ocurred: %i\n\n", max);
    fclose(fptr);
}

void updateColissionList(){
    fptr = fopen("colisions.txt", "w");
    char w[20];
    for(long int i = 0; i < MAX; ++i){
        sprintf(w, "%d", (flagArray[i]));
        fprintf(fptr,"[%i] - %s\n", i, w);
        //fprintf(fptr, "%s\n",w);
    }
    fclose(fptr);
}

void initArray(){
    long int i = 0;
    for (i = 0; i < MAX; ++i){
        flagArray[i] = -1;
    }
}

void insertWord(char word[], char clue[]){ 
    long int index;
    tnode *new = (tnode*)malloc(sizeof(tnode));
    new->next = NULL;
    strcpy(new->key, word);
    strcpy(new->value, clue);
    index = hashFunc(new->key);
    tnode *aux;
    if(flagArray[index] == -1){
        ptrArray[index] = new;
        flagArray[index] = 0; 
    }
    else{
        ++flagArray[index];
        aux = ptrArray[index];   
        while(aux->next){
            aux = aux->next;    
        }
        aux->next = new;
    } 
}

void InsertFromFile(char fname[]){
    char *word, *clue;
    char fileWord[1000];
    unsigned long int index;
    fptr = fopen(fname,"r");
    if(fptr == NULL){
        printf("Not able to open the file.");
        return;
    }
    while (!feof(fptr)){
        fgets(fileWord, 1000, fptr);
        word = strtok(fileWord, ",");
        clue = strtok(NULL, ",");
        clue[strlen(clue)-1] = '\0';
        insertWord(word, clue);
    }
    fclose(fptr);
}


tnode *search(long int index){
    return ptrArray[index];
}

tnode *wordExists(char word[], long int index){
    tnode *aux = ptrArray[index];
    while(aux){
        if(strcmp(aux->key, word) == 0){
            return aux;
        }
        else
            aux = aux->next;
    }
}

void delete(char word[]){
    unsigned long int index = hashFunc(word);
    if(flagArray[index] == -1 || index > MAX){
        printf("\n<<<< Word not on the dicctionary >>>>\n");
        return;
    }
    tnode *aux = ptrArray[index];
    tnode *last;
    if(aux->next == NULL){
        flagArray[index] = -1;
        ptrArray[index] = NULL;
    }
    else while(aux){
        if(strcmp(ptrArray[index]->key, word) == 0){
            ptrArray[index] = ptrArray[index]->next;
            aux = NULL;
            break;
        }
        last = aux;
        aux = aux->next;
        if(strcmp(aux->key, word) == 0){
            last->next = aux->next;
            aux = NULL;
            break;
        }
    }
}

long int hashFunc(char word[]){
    //#### Author: Gerardo Esteban Jurado Carrera #####
    //####      Advances Data Structures          #####
    //####    Atonomous University of Chihuahua   #####
    unsigned long int index = 0;
    for(int i = 0, j = strlen(word); i < strlen(word); ++i, --j){
        index += word[i]*(pow(10,j));
        index *= j;
    }
    index -= randNumberArray[strlen(word)-1]; 
    index = index % MAX;
    return index;
}