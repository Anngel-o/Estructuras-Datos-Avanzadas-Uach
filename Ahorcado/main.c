//||                Profesor Titular: MENDEZ MARISCAL NORMA LETICIA             ||
//||                   Tarea: Juego Ahorcado (Lenguaje C)                       ||
//||                 Alumnos: Gerardo Esteban Jurado Carrera                    ||
//||                          José Ángel Ortiz Meráz                            ||
//||                                  11/21/2023                                ||
/*################################################################################*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h> 
#include "Heap.c"
#include "TablaHash.c"

tnode *palabra;

double puntaje = 0.0;


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

// funciones

char pistaPedida(tnode *palabra, char letra){
    do{
        if(letra == '1'){
            printf("\n>>>> %s <<<<\n", palabra->value);
            getchar();
        }
        printf(":: ");
        letra = getchar();
    } while(letra == '1');
    return letra;
}

tnode *generarPalabraAzar(){
    tnode *palabra;
    srand (time(NULL));
    int index = rand()% 8969;
    palabra = search(index);
    while(palabra == NULL){
        palabra = search(++index);
    }
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

void imprimirPuntuacion(){
    printf("\n   ||| PUNTUACION: %.1f |||\n\n", puntaje);
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
    printf("\n<<<< Ingrese \"1\" para obtener la pista.>>>>\n");
}

int verificarLetra(char palabra[], char letra, char letrasIntentadas[]){
    for(int i  = 0; i < strlen(letrasIntentadas); ++i){
        if(letra == letrasIntentadas[letra - 97]){
            printf("\n<<<< Ya no se puede usar esa letra >>>>\n");
            return -2; // la letra ya se uso
        }
    }
    for(int i = 0; i < strlen(palabra); ++i){
        if(letra == palabra[i]){
            return i; // la palabra si tiene esa letra
        }    
    }
    return -1; // la palabra no tiene esa letra
}

char validarInput(char letra, tnode *palabra){
    //si letra esta fuera de estos rangos y es diferente de 1 (opcion para pista)
    while((letra > 'Z' || letra < 'A') && (letra > 'z' || letra < 'a')){
        if(letra == '1'){
            letra = pistaPedida(palabra, letra);
            continue;
        }
        printf("\n>>> No valido. Porfavor asegurese de ingresar una letra <<<\n");
        printf(":: ");
        if(letra <= 57 && letra >= 48)
            letra = getchar();
        letra = getchar();
    }
    return letra;
}

void imprimirCasillas(char palabra[], char letra, char palabraGuardada[]){
    for(int i = 0; i < strlen(palabra); ++i){
        if(palabra[i] == letra){
            palabraGuardada[i] = palabra[i];
        }
        printf("[ %c ]", palabraGuardada[i]);
    }     
}

void iniPalGuard(char palabraGuardada[], char palabra[]){
    for(int i = 0; i < strlen(palabra); ++i){
        palabraGuardada[i] = '-';
    }
}

void actualizarPuntaje(char palabra[], char letra, int existe){
    if(existe >= 0){
        for(int i = 0; i < strlen(palabra); ++i){
            if(letra == palabra[i]){
                puntaje += 100.0/strlen(palabra);
            }
        }   
    }
    else if(existe < 0)
        puntaje -= 100.0/strlen(palabra);
}

int juegoTerminado(int numPantalla, char palabra[], char palabraGuardada[]){
    if(numPantalla == 6){
        printf("\n\n==================================\n");
        printf("<<<<  SE HA AHORCADO JOVEN :(  >>>\n");
        printf("==================================\n");
        puntaje = 0.0;
        printf("\n|||||||||||||||||||||||||||\n");
        printf("|| PUNTUACION FINAL: %.1f ||\n", puntaje);
        printf("|||||||||||||||||||||||||||\n");
        delete(palabra);
        getchar();
        system("cls");
        return 1;
    }
    int x = 0; 
    for(int i = 0; !x && i < strlen(palabra); ++i){
        x = palabra[i] - palabraGuardada[i];
    }
    if(x == 0){
        printf("\n\n===============================\n");
        printf("<<<<      HA ADIVINADO     >>>>\n");
        printf("===============================\n");
        printf("\n|||||||||||||||||||||||||||\n");
        printf("|| PUNTUACIO FINAL: %.1f ||\n", puntaje);
        printf("|||||||||||||||||||||||||||\n");
        insertar(puntaje, palabra);
        delete(palabra);
        getchar();
        system("cls");
        return 1;
    }
    return 0;

}

void motorJuego(){
    puntaje = 0.0;
    int numPantalla = 0;
    int existe = 1;
    tnode *palabra;
    char palabraGuardada[30] = {' '};
    char letrasIntentadas[26] = {' '};
    char tablero[28] = {"QWERTYUIOPASDFGHJKLZXCVBNM"};
    char letra = '\0';
    palabra = generarPalabraAzar();
    printf("\n%s\n", palabra->key);
    iniPalGuard(palabraGuardada, palabra->key);
    imprimirPantalla(numPantalla);
    imprimirTablero(tablero);
    imprimirPuntuacion();
    imprimirCasillas(palabra->key, letra, palabraGuardada);
    do{
        printf(":: ");
        letra = getchar();
        letra = validarInput(letra, palabra);
        getchar();
        letra = tolower(letra);
        actualizarTablero(letra, tablero);
        existe = verificarLetra(palabra->key, letra, letrasIntentadas);
        letrasIntentadas[letra - 97] = letra;
        actualizarPuntaje(palabra->key, letra, existe);
        if(existe == -1){
            numPantalla++;
        }
        if(existe == -2){
            imprimirPuntuacion();
            continue;
        }
        system("cls");
        imprimirPantalla(numPantalla);
        imprimirTablero(tablero);
        imprimirPuntuacion();
        imprimirCasillas(palabra->key, letra, palabraGuardada);
    } while(!juegoTerminado(numPantalla, palabra->key, palabraGuardada));
}

int main(){
    initHash();
    char jugar = '\n';
    printf("=========================================\n");
    printf("    Bienvenido al Juego del Ahorcado    \n");     
    printf("=========================================\n");
    printf("\n>>>> Presione Enter para comenzar <<<<\n");
    getchar();
    while(jugar =='\n'){
        system("cls");
        motorJuego();
        printf("\n>>> Desea continuar jugando? [Enter] <<<<\n");
        jugar = getchar();
    }
    printf("\n>>> Lista de palabras adivinadas <<<\n");
    while(maximo){
        printf("\nPalabra: %s. Puntaje: %.1F\n", maximo->palabra, maximo->llave);
        eliminar();
    }

    system("pause");
}