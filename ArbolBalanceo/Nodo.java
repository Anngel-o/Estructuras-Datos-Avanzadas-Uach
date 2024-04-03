//Balanceo de Árboles, 353195, José Ángel Ortiz Meraz, 14/09/23
package ArbolBalanceo;
public class Nodo {
    Nodo nodoIzq;
    Nodo nodoDer;
    int datos;
    int fe;

    public Nodo(int datosNodo) {
        this.datos = datosNodo;
        this.nodoIzq = this.nodoDer = null;
        this.fe = 0;
    }
}
