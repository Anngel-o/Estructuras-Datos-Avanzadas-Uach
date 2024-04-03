//Balanceo de Árboles, 353195, José Ángel Ortiz Meraz, 14/09/23
package ArbolBalanceo;
import javax.swing.JOptionPane;
public class Arbol {
    public Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    public Nodo insertarNodo(Nodo nuevo, Nodo sub) {
        Nodo nuevoPadre = sub;
        if (nuevo.datos < sub.datos) {
            if (sub.nodoIzq == null) {
                sub.nodoIzq = nuevo;
            } else {
                sub.nodoIzq = insertarNodo(nuevo, sub.nodoIzq);
                if (FE(sub.nodoIzq) - FE(sub.nodoDer) == 2) {
                    //Desbalanceo
                    if (nuevo.datos < sub.nodoIzq.datos) {
                        nuevoPadre = rotacionSimpleIzquierda(sub);
                    } else {
                        nuevoPadre = rotacionDobleDerecha(sub);
                    }
                }
            }
        } else if (nuevo.datos > sub.datos){
            if (sub.nodoDer == null) {
                sub.nodoDer = nuevo;
            } else {
                sub.nodoDer = insertarNodo(nuevo, sub.nodoDer);
                if (FE(sub.nodoDer) - FE(sub.nodoIzq) == 2) {
                    if (nuevo.datos > sub.nodoDer.datos) {
                        nuevoPadre = rotacionSimpleDerecha(sub);
                    } else {
                        nuevoPadre = rotacionDobleDerecha(sub);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nodo duplicado");
        }

        //Actualizar altura
        if ((sub.nodoIzq == null) && (sub.nodoDer != null)) {
            sub.fe = sub.nodoDer.fe + 1;
            //balanceo
        } else if((sub.nodoDer == null) && (sub.nodoIzq != null)) {
            sub.fe = sub.nodoIzq.fe + 1;
        } else {
            sub.fe = Math.max(FE(sub.nodoIzq), FE(sub.nodoDer) + 1);
        }
        return nuevoPadre;
    }

    public void insertar(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            raiz = insertarNodo(nuevo, raiz);
        }
    }

    public Nodo buscar(int valor, Nodo raiz) {
        if (raiz == null) {
            return null;
        } else if(raiz.datos == valor) {
            return raiz;
        } else if(raiz.datos < valor) {
            return buscar(valor, raiz.nodoDer);
        } else {
            return buscar(valor, raiz.nodoIzq);
        }
    }
    
    public int minimo() {
        int minimo = raiz.datos;
        Nodo nodo = raiz;
        while (nodo.nodoIzq != null) {
            nodo = nodo.nodoIzq;
            minimo = nodo.datos;
        }
        return minimo;
    }

    public int maximo() {
        int mayor = raiz.datos;
        Nodo nodo = raiz;
        while (nodo.nodoDer != null) {
            nodo = nodo.nodoDer;
            mayor = nodo.datos;
        }
        return mayor;
    }

    public int maximoNivel() {
        if (raiz != null) {
            return maximoNivel(this.raiz);
        }
        // si el árbol está vacío su nivel es -1
        return -1;
    }
    public int maximoNivel(Nodo raiz){
        if(raiz != null){
            if((raiz.nodoIzq != null) || (raiz.nodoDer != null)) {
                int nivelIzquierdo = maximoNivel(raiz.nodoIzq);
                int nivelDerecho = maximoNivel(raiz.nodoDer);
                return 1 + Math.max(nivelIzquierdo, nivelDerecho);
            }
        }
        return 0;
    }

    public int altura() {
        //La altura de un árbol comienza desde el índice 0 o 1 dependiendo del autor
        //en este caso lo tomaremos desde el índice 0
        return maximoNivel();
    }

    public int FE(Nodo nodo) {
        if (nodo == null) {
            return -1;
        } else {
            return nodo.fe;
        }
    }

    public Nodo rotacionSimpleIzquierda(Nodo nodo) {
        Nodo aux = nodo.nodoIzq;
        nodo.nodoIzq = aux.nodoDer;
        aux.nodoDer = nodo;
        nodo.fe = Math.max(FE(nodo.nodoIzq), FE(nodo.nodoDer)+ 1);
        aux.fe = Math.max(FE(aux.nodoIzq), FE(aux.nodoDer)+ 1);
        return aux;
    }

    public Nodo rotacionSimpleDerecha(Nodo nodo) {
        Nodo aux = nodo.nodoDer;
        nodo.nodoDer = aux.nodoIzq;
        aux.nodoIzq = nodo;
        nodo.fe = Math.max(FE(nodo.nodoIzq), FE(nodo.nodoDer)+ 1);
        aux.fe = Math.max(FE(aux.nodoIzq), FE(aux.nodoDer)+ 1);
        return aux;
    }

    public Nodo rotacionDobleIzquierda(Nodo nodo) {
        Nodo aux;
        nodo.nodoIzq = rotacionSimpleDerecha(nodo.nodoIzq);
        aux = rotacionSimpleIzquierda(nodo);
        return aux;
    }

    public Nodo rotacionDobleDerecha(Nodo nodo) {
        Nodo aux;
        nodo.nodoDer = rotacionSimpleIzquierda(nodo.nodoDer);
        aux = rotacionSimpleDerecha(nodo);
        return aux;
    }
}
