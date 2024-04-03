#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct snodo
{
    struct snodo *padre;
    struct snodo *hijo;
    struct snodo *hermanoIzq;
    struct snodo *hermanoDer;
    double llave;
    char palabra[30];
    int orden;
} tnodo;

tnodo *raiz = NULL;
tnodo *maximo;
tnodo *listaOrdenes[5];

//prototipos
tnodo *crearNodo();
void insertar(double llave, char palabra[]);
void eliminar();
int verificar();
tnodo *unir(tnodo *aux);
void actualzarMin();

//funciones
tnodo *crearNodo(){
    tnodo *aux;
    aux = (tnodo *)malloc(sizeof(tnodo));
    aux->padre = NULL;
    aux->hijo = NULL;
    aux->hermanoDer = NULL;
    aux->hermanoIzq = NULL;
    aux->orden = 0;
    return aux;
}

void insertar(double llave, char palabra[])
{
    if (raiz == NULL)
    {
        raiz = crearNodo();
        raiz->llave = llave;
        strcpy(raiz->palabra, palabra);
        maximo = raiz;
        return;
    }
    tnodo *nuevo = crearNodo();
    nuevo->llave = llave;
    strcpy(nuevo->palabra, palabra);
    //si el tnodo a insertar es mayor que el maximo, se actualiza el maximo
    tnodo *aux = maximo;
    if(nuevo->llave > maximo->llave)
        maximo = nuevo;
    //vuelve al nuevo tnodo la raiz
    nuevo->hermanoDer = raiz;
    raiz->hermanoIzq = nuevo;
    raiz = nuevo;
}

void eliminar(){
    //si solo existe la raiz, eliminar raiz
    if(maximo == raiz && !(raiz->hermanoDer) && !(raiz->hijo)){
        maximo = NULL;
        return;
    }
    tnodo *aux;
    // elnlazar los hermanos del mínimo entre ellos
    if(maximo->hermanoIzq)
        maximo->hermanoIzq->hermanoDer = maximo->hermanoDer;
    if(maximo->hermanoDer)
        maximo->hermanoDer->hermanoIzq = maximo->hermanoIzq;
    //si  el nodo a eliminar no tiene hijos entonces solo verifica
    if(!maximo->hijo){
        //nada
    }
    //sino, subir los hijos a la lista de raices
    else{
        aux = maximo->hijo;
        aux->padre = NULL;
        while(aux->hermanoDer){
            aux->padre = NULL;
            aux = aux->hermanoDer;
        }
        aux->padre = NULL;
        if(raiz->hermanoDer){
            if(maximo == raiz){
                raiz = raiz->hermanoDer;
                raiz->hermanoIzq = aux;
                aux->hermanoDer = raiz;
            }
            else{
                raiz->hermanoIzq = aux;
                aux->hermanoDer = raiz;
            }
        }
        raiz = maximo->hijo;
    }
    //desenlazar del maximo a sus hermanos
    
    //maximo->hermanoDer= NULL;
    //maximo->hermanoIzq = NULL;
    //si sencuentra, quitar de la lista de ordenes al maximo para liberar el espacio
    if(listaOrdenes[maximo->orden] == maximo)
        listaOrdenes[maximo->orden] = NULL;
    //actualiza el maximo
    if(maximo == raiz){
        raiz = maximo->hermanoDer;
        maximo = maximo->hermanoDer;
    }
    else{
        maximo = raiz;
        actualzarMin(); 
    }
    verificar();
}

int verificar(){
    tnodo *aux = raiz;
    while(aux){
        //si existe algo en esa posicion de la lista d ordenes llama a unir()
        if(listaOrdenes[aux->orden] && aux != listaOrdenes[aux->orden]){
            aux = unir(aux);
            continue;
        }
        //sino, a esa posicion asignale el tnodo aux
        else if(aux->hermanoDer){
            listaOrdenes[aux->orden] = aux;
        }
        aux = aux->hermanoDer;
    }
}

tnodo *unir(tnodo *aux){
    tnodo *hijo, *padre; 
    //el nodo mayor es el nodo que se hace hijo y el menor el que se queda de padre 
    if(aux->llave > listaOrdenes[aux->orden]->llave){
        padre = aux;
        hijo = listaOrdenes[aux->orden];
    }
    else{
        hijo = aux;
        padre = listaOrdenes[aux->orden];
    }
    if(hijo == raiz){
        raiz = hijo->hermanoDer;
        raiz->hermanoIzq =NULL;
    }
    //sino, enlaza sus hermanos entre ellos
    else{
        if(hijo->hermanoIzq)
            hijo->hermanoIzq->hermanoDer = hijo->hermanoDer; 
        if(hijo->hermanoDer)
            hijo->hermanoDer->hermanoIzq = hijo->hermanoIzq; 
        hijo->hermanoIzq = NULL;
        hijo->hermanoDer = NULL;
    }
    //si el tnodo que se hace hijo es el mínimo, actualiza el mínimo
    if(hijo == maximo)
        actualzarMin();
    //como el tnodo menor se hace hijo, su hermano derecho es el hijo actual del padre
    hijo->hermanoDer = padre->hijo;
    //ahora son hermanos
    if(padre->hijo)
        padre->hijo->hermanoIzq = hijo;
    //y el hijo actual del padre será el menor, desplazando al otro hijo
    padre->hijo = hijo;
    hijo->padre = padre;
    //se incrementa el orden del que se quedo como padre y se actualiza la lista de ordenes
    padre->orden++;
    listaOrdenes[padre->orden-1] = NULL;
    listaOrdenes[padre->orden] = padre;;
    //free(hijo);
    //free(padre);
    return raiz;
}

void actualzarMin(){
    tnodo *aux;
    aux = raiz;
    if(!aux->hermanoDer)
        maximo = aux;
    while(aux->hermanoDer){
        if(aux->hermanoDer->llave > maximo->llave){
            maximo = aux->hermanoDer;
            aux = aux->hermanoDer;
        }
        else
            aux = aux->hermanoDer;
    }
}
