//Árbol Binario de Búsqueda con arreglos, 353195, José Ángel Ortiz Meraz, 28/08/23
import java.io.*;
import javax.swing.JOptionPane;

public class ABB {
    static final int MAX = 101;
    static final int VACIO = 666;
    static int[] arbol = new int[MAX];

    static File file = null;
    static FileReader reader = null;
    static BufferedReader buffer = null;

    static int nodoIzquierdo(int x) {
        return x*2 +1;
    }

    static int nodoDerecho(int x) {
        return x*2 +2;
    }

    static int busqueda(int valor, int casillaInicialABuscar) {
        int donde = -2;
        
        if (casillaInicialABuscar < MAX) {
            if (arbol[casillaInicialABuscar] != VACIO) {
                if(valor == arbol[casillaInicialABuscar]) {
                    donde = casillaInicialABuscar;
                } else if (valor > arbol[casillaInicialABuscar]) {
                    donde = busqueda(valor, nodoDerecho(casillaInicialABuscar));
                } else {
                    donde = busqueda(valor, nodoIzquierdo(casillaInicialABuscar));
                } 
            } else {
                donde = casillaInicialABuscar;
            }  
        } 

        return donde;
    }

    static int buscar(int valor) {
        int r;
        r = busqueda(valor, 0);
        if (r >= 0 && arbol[r] == VACIO) {
            r = -1;
        }
        return r;
    }

    static int busquedaSecuencial(int valor) {
        boolean flag = false;
        int i = 0;
        while (i < arbol.length && !flag) {
            if (arbol[i] == valor) {
                flag = true;
            }
            i++;
        }

        if (flag == true) {
            JOptionPane.showMessageDialog(null, "El valor se encuentra en el índice " + (i-1) + ", el valor es " + arbol[i -1]);
            return i;
        }
        else {
            JOptionPane.showMessageDialog(null, "El valor no se encuentra en el arreglo");
        }

        return arbol[i -1];
    }

    static int insertar(int valor) {
        int donde;
        donde = busqueda(valor, 0);
        if (donde >= 0 && donde < MAX) {
            arbol[donde] = valor;
        }
        return donde;
    }

    static int eliminar(int valor, int pos) {
        int donde;
        donde = busqueda(valor, 0);
        if (donde >= 0 && donde < MAX) {
            if (arbol[donde] == valor) {
                if (arbol[nodoIzquierdo(donde)] == VACIO && arbol[nodoDerecho(donde)] == VACIO) {
                    arbol[donde] = VACIO;
                }
                if (arbol[nodoIzquierdo(donde)] == VACIO && arbol[nodoDerecho(donde)] != VACIO) {
                    arbol[donde] = arbol[nodoDerecho(donde)];
                    eliminar(arbol[donde], nodoDerecho(donde));
                }
            }
        }
        return donde;
    }

    static void iniciar() {
        // for (int i = 0; i < MAX; i++) {
        //     arbol[i] = VACIO;
        // }

        try {
            file = new File("datos.txt");
            reader = new FileReader(file);
            //FileReader permite leer caracteres
            buffer = new BufferedReader(reader);
            //BufferReader contiene metodos para leer lineas completas
            String line;
            int i = 0;
            while ((line = buffer.readLine()) != null) {
                arbol[i] = Integer.parseInt(line);
                i++;
            }
        } catch (Exception e) {
            e.getMessage();
        } 

        // arbol[0] = 8;
        // arbol[1] = 3;
        // arbol[2] = 20;
        // arbol[4] = 5;
        // arbol[10] = 7;
    }

    static void imprimir() {
        for (int i = 0; i < MAX; i++) {
            // JOptionPane.showMessageDialog(null, arbol[i]);
            System.out.println(arbol[i]);
        }
    }

    static int max() {
        int max = 0;
        for (int i = 0; i < arbol.length; i++) {
            if (arbol[i] > max) {
                max = arbol[i];
            }
        }
        return max;
    }
    static int min() {
        int min = 999;
        for (int i = 0; i < arbol.length; i++) {
            if (arbol[i] < min) {
                min = arbol[i];
            }
        }
        return min;
    }

    public static void main(String[] args) throws Exception{
        int r = 0, i = 0,op = 1;

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("ÁRBOL BINARIO DE BÚSQUEDA\n\n1.Rellenar arreglo \n2.Imprimir árbol\n3.Buscar nodo\n4.Eliminar nodo\n5.Valor mínimo\n6.Valor máximo\n7.Salir\n\nQué operación deseas realizar: "));

            switch (op) {

                case 1: 
                    // i = Integer.parseInt(JOptionPane.showInputDialog("Insertar Nodo\n\nValor a insertar: "));
                    // r = insertar(i);
                    // JOptionPane.showMessageDialog(null, "Valor acomodado en la casilla " + r);
                    iniciar();
                    JOptionPane.showMessageDialog(null, "Arreglo lleno con valores de datos.txt");
                    imprimir();
                    break;
                case 2: 
                    JOptionPane.showMessageDialog(null, "Árbol impreso en la consola");
                    imprimir();
                    break;
                case 3: 
                    i = Integer.parseInt(JOptionPane.showInputDialog("Buscar Nodo\n\nValor a buscar: "));
                    busquedaSecuencial(i);
                    // r = buscar(i);
                    // if(r < 0) {
                    //     JOptionPane.showMessageDialog(null, "Valor no encontrado");
                    // } else {
                    //     JOptionPane.showMessageDialog(null, "El valor se encuentra en la casilla " + r + ", el valor es: " + arbol[r]);
                    // }
                    break;
                case 4: 
                    i = Integer.parseInt(JOptionPane.showInputDialog("Valor a eliminar: "));
                    JOptionPane.showMessageDialog(null, i + " eliminado");
                    eliminar(i, 0);
                    break;
                case 5: 
                    r = min();
                    JOptionPane.showMessageDialog(null, "Consultar valor mínimo\n\nEl valor mínimo es " + r);
                    break;
                case 6: 
                    r = max();
                    JOptionPane.showMessageDialog(null, "Consultar valor mínimo\n\nEl valor máximo es " + r);
                    break;
                case 7: 
                    System.out.println("Hasta pronto!");
                    break;
                default:
                    break;
            }
        } while (op !=7);
    }
}
