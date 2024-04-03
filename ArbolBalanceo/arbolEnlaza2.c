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
int altura(tnodo *aux);
void verifica(tnodo *aux);
tnodo * rotader(tnodo *aux);
tnodo * rotadobleder(tnodo *aux);
tnodo * rotaizq(tnodo *aux);
tnodo * rotadobleizq(tnodo *aux);
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
					{
							printf("\nSi esta y su padre esta en %X",anterior);
							printf("\nLa altura es %i",altura(resultado));
							printf("\nFactor de equilibrio es %i",fe(resultado));
					}
					
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
	verifica(aux);
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

int altura(tnodo *aux)
{
	int ri=0,rd=0,a=0;
	
	if(aux->izq)
		ri = 1 + altura(aux->izq);
	if(aux ->der)
		rd = 1 + altura(aux->der);
	if( ri > rd)
		a = ri;
	else
		a = rd;
	return a;
}
int fe(tnodo *aux)
{
	int ai=0,ad=0;
	if(aux->izq)
		ai = 1 + altura(aux->izq);
	if(aux->der)
		ad = 1 +altura(aux->der);
		
	return ad-ai;
}

void verifica(tnodo *aux)
{
	int fep,feh;
	while(aux->padre!=NULL)
	{
		aux = aux->padre;
		fep= fe(aux);
		if( fep == -2 ) //rotaciones
		{
			feh = fe(aux->izq);
			if(feh<0) //simple derecha
				rotader(aux);
			else
				rotadobleder(aux);
		}
		if( fep == 2 ) //rotaciones
		{
			feh = fe(aux->der);
			if(feh>=0) //simple derecha
				rotaizq(aux);
			else
				rotadobleizq(aux);
		}
			
	}
}

tnodo * rotader(tnodo *aux)
{
	printf("\nRotacion derecha en %i",aux->llave);
}
tnodo * rotadobleder(tnodo *aux)
{
	printf("\nRotacion doble derecha en %i",aux->llave);
}
tnodo * rotaizq(tnodo *aux)
{
	printf("\nRotacion izquierda en %i",aux->llave);
}
tnodo * rotadobleizq(tnodo *aux)
{
	printf("\nRotacion doble izquierda en %i",aux->llave);
}







