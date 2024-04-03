#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h> 


char palabras[10][15] = {"mermelada",
                         "limosina", 
                         "aqueducto", 
                         "monasterio",
                         "polisonte",
                         "artillerria",
                         "cangrejo",
                         "Hospital",
                         "pimienta",
                         "noruega" 
                         };
char ahorcado[65][20] = {
"     ________     \n",
"     |      |     \n", 
"     |            \n",
"     |            \n",
"     |            \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |            \n",
"     |            \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |      |     \n",
"     |            \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |     /|     \n",
"     |            \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |     /|\\   \n",
"     |            \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |     /|\\   \n",
"     |     /      \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
"     ________     \n",
"     |      |     \n", 
"     |      O     \n",
"     |     /|\\   \n",
"     |     / \\   \n",
"     |            \n",
"    / \\          \n",
" ================ \n",
};

/*char tablero[3][31] = {
    "[Q][W][E][R][T][Y][U][I][O][P]",
    "[A][S][D][F][G][H][J][K][L][Ñ]",
    "     [Z][X][C][V][B][N][M]    ",
};*/

void generarPalabraAzar(char palabra[]){
    srand (time(NULL));
    int r = rand()%11;
    strcpy(palabra, palabras[r]);
}

void imprimirTablero(char tablero[]){
    printf("\n");
    for(int i = 0; i < 27; ++i){
        printf("[%c]", tablero[i]);
        if(i == 9)
            printf("\n");
        if(i == 19)
            printf("\n    ");
    }
    printf("\n\n");
}

void actualizarTablero(char letra, char tablero[]){
    for(int i = 0; i < strlen(tablero); ++i){
        if(tablero[i] == toupper(letra))
            tablero[i] = ' ';
    }
}

void imprimirPantalla(int numPantalla){
     for(int i = numPantalla*8, j = i + 8; i < j; ++i){
        printf("%s", ahorcado[i]);
    }
}

int verificarLetra(char palabra[], char letra, char letrasIntentadas[]){
    for(int i  = 0; i < strlen(letrasIntentadas); ++i){
        if(letra == letrasIntentadas[letra - 97]){
            printf("\n<<<< Ya no se puede usar esa letra >>>>\n");
            getchar();
            return -2; // la letra ya se uso
        }
    }
    for(int i = 0; i < strlen(palabra); ++i){
        if(letra == palabra[i])
            return i; // la palabra si tiene esa letra
    }
    return -1; // la palabra no tiene esa letra
}

void imprimirCasillas(char palabra[], char letra, char palabraGuardada[]){
    for(int i = 0; i < strlen(palabra); ++i){
        if(palabra[i] == letra)
            palabraGuardada[i] = palabra[i];
        printf("[ %c ]", palabraGuardada[i]);
    }     
}

void iniPalGuard(char palabraGuardada[], char palabra[]){
    for(int i = 0; i < strlen(palabra); ++i){
        palabraGuardada[i] = '-';
    }
}

int juegoTerminado(int numPantalla, char palabra[], char palabraGuardada[]){
    if(numPantalla == 6){
        printf("\n\n============================\n");
        printf("<<<<  SE HA AHORCADO :(  >>>\n");
        printf("============================\n");
        getchar();
        getchar();
        system("cls");
        return 1;
    }
    int x = 0; 
    for(int i = 0; !x && i < strlen(palabra); ++i){
        x = palabra[i] - palabraGuardada[i];
    }
    if(x == 0){
        printf("\n\n============================\n");
        printf("<<<<      HA GANADO     >>>>\n");
        printf("============================\n");
        getchar();
        getchar();
        system("cls");
        return 1;
    }
    return 0;

}

int main(){
    int numPantalla = 0;
    int existe = 1;
    char palabra[15];
    char palabraGuardada[15];
    char letrasIntentadas[26];
    char tablero[28] = {"QWERTYUIOPASDFGHJKLZXCVBNM"};
    char letra;
    printf("=========================================\n");
    printf("    Bienvenido al Juego del Condenado    \n");     
    printf("=========================================\n");
    generarPalabraAzar(palabra);
    iniPalGuard(palabraGuardada, palabra);
    imprimirPantalla(numPantalla);
    imprimirTablero(tablero);
    imprimirCasillas(palabra, letra, palabraGuardada);
    do{
        printf(":: ");
        letra = getchar();
        letra = tolower(letra);
        actualizarTablero(letra, tablero);
        existe = verificarLetra(palabra, letra, letrasIntentadas);
        letrasIntentadas[letra - 97] = letra;
        if(existe == -1)
            numPantalla++;
        if(existe == -2)
            continue;
        system("cls");
        imprimirPantalla(numPantalla);
        imprimirTablero(tablero);
        imprimirCasillas(palabra, letra, palabraGuardada);
        if(juegoTerminado(numPantalla, palabra, palabraGuardada)){
            getchar();
            numPantalla = 0;
            strcpy(tablero, "QWERTYUIOPASDFGHJKLZXCVBNM");
            generarPalabraAzar(palabra);
            iniPalGuard(palabraGuardada, palabra);
            imprimirPantalla(numPantalla);
            imprimirTablero(tablero);
            imprimirCasillas(palabra, letra, palabraGuardada);
        }
        getchar();
        
    } while(1);
    scanf("%c");
    scanf("%c");
}