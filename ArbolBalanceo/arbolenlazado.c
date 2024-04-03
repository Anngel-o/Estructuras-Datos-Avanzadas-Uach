#include <stdio.h>
#include <stdlib.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
typedef struct snodo
{
	int llave;
	struct snodo *izq;
	struct snodo *der;
	struct snodo *padre;
}tnodo;
tnodo *raiz=NULL, *anterior=NULL;
tnodo *insertar(int valor);
tnodo *buscar(int valor);

int main(int argc, char *argv[]) {
	int opc, valor;
	tnodo *resultado;
	raiz=NULL;
	do{
		printf("\n1)Insertar\n2)Buscar\n6)Salir");
		printf("\nOpcion:"); scanf("%i",&opc);
		switch(opc)
		{
			case 1: printf("\nValor a insertar"); scanf("%i",&valor);
					insertar(valor);
					break;
			case 2: printf("\nValor a buscar"); scanf("%i",&valor);
					resultado=buscar(valor);
					if(resultado==NULL)
						printf("\nNo existe");
					else
						printf("\nSi esta y su padre esta en %X",anterior);
					break;
					
		}
	}while(opc!=6);
	return 0;
}


tnodo *insertar(int valor)
{
	tnodo *aux;
	
	aux = buscar(valor);
	if(aux==NULL)
	{
		
		aux = (tnodo *)malloc(sizeof(tnodo));
		aux->llave = valor;
		aux->izq= NULL;
		aux->der=NULL;
		aux->padre=anterior;
		if(anterior!=NULL)
			if(valor < anterior->llave)
				anterior->izq = aux;
			else
				anterior->der = aux;
		else
			raiz = aux;
	}
	return aux;
}

tnodo *buscar(int valor)
{
	tnodo *aux;
	aux = raiz;

	while(aux!=NULL && aux->llave !=valor )
	{
		anterior = aux;
		if(valor < aux->llave)
			aux = aux->izq;
		else
			aux = aux->der;
	}
	return aux;
}














