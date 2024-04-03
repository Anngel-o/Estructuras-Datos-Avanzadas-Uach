//ARBOLES BINARIOS EN ARREGLOS
//EDA 2023 AGOSTO

#include <stdio.h>
#define MAX 100
#define VACIO 666

int arbol[MAX];

int hizq(int x);
int hder(int x);
void inicia(void);
int buscar(int valor, int x ); //funcion privado
int busca(int valor); //publica
int inserta(int valor);
int eliminar(int valor, int pos);
int maximo(int pos);


int main(int argc, const char * argv[]) {
    int i,opc,r;
    inicia();
    do{
        printf("\n1)Insertar\n2)Eliminar\n3)Buscar\n4)Maximo\n5)Minimo\n6)Salir\nOpcion:");
        scanf("%i",&opc);
        switch(opc)
        {
            case 1: printf("\nValor a insertar:");
                    scanf("%i",&i);
                    r = inserta(i);
                    printf("\nLo puso en la casilla %i",r);
                    break;
            case 2: printf("\nValor a eliminar:");
                    scanf("%i",&i);
                    eliminar(i,0);
                    
                    break;
           
            case 3: printf("\nValor a buscar:");
                    scanf("%i",&i);
                    r = busca(i);
                    if(r<0)
                        printf("\nNo esta");
                    else
                        printf("\nEsta en la casilla %i",r);
                break;
            case 4:	printf("\nEl Maximo es %i",arbol[maximo(0)]);
                        	break;
            
        }
    }while(opc!=6);
    
  
    return 0;
}

int hizq(int x)
{
    return 2*x +1;
}
int hder(int x)
{
    return 2*x+2;
}
void inicia(void)
{
    int i;
    for(i=0;i<MAX;i++)
        arbol[i]=VACIO;
    arbol[0]=8;
    arbol[1]=3;
    arbol[2]=20;
    arbol[4]=5;
    arbol[10]=7;
   
}
int buscar(int valor, int x )
{
    int donde=-2;
    
    if(x<MAX)
    {
        if(arbol[x]!=VACIO)
        {
            if(valor == arbol[x])
               	donde = x;
            else if( valor > arbol[x])
                donde = buscar(valor, hder(x));
            else
                donde = buscar(valor, hizq(x));
        }else
            donde = x;
    }
    return donde;
        
}
int busca(int valor)
{
    int r;
    r = buscar(valor,0);
    if(r>=0 && arbol[r]==VACIO)
        r = -1;
    return r;
}

int inserta(int valor)
{
    int donde;
    donde = buscar(valor,0);
    if(donde>=0 && donde <MAX)
        arbol[donde]=valor;
    
    return donde;
}
int eliminar(int valor, int pos)
{
    int donde;
    donde = buscar(valor,pos);
    if(donde>=0 && donde <MAX)
    {
        if(arbol[donde]==valor)// lo encontre
            if(arbol[hizq(donde)]==VACIO && arbol[hder(donde)]==VACIO)//soy una hoja
                arbol[donde]=VACIO;
            if( arbol[hizq(donde)]==VACIO && arbol[hder(donde)]!=VACIO)//solo tengo al hijo derecho
            {
            	arbol[donde]=arbol[hder(donde)];
            	eliminar(arbol[donde],hder(donde));
            }
    }

    return donde;
}

int maximo(int pos)
{
	int i;
	i = pos;
	if(hder(pos)<MAX && arbol[hder(pos)]!=VACIO  )
		i = maximo(hder(pos)  );
	return i;
}
