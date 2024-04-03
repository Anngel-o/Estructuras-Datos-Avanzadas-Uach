//Balanceo de Árboles, 353195, José Ángel Ortiz Meraz, 14/09/23
package ArbolBalanceo;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String args[]) {
        // Random numeroAleatorio = new Random();
        Arbol arbol = new Arbol();
        int valor = 0;
        int op = 0;

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("BALANCEO DE ÁRBOLES\n\n1.Insertar \n2.Buscar\n3.Máximo\n4.Mínimo\n5.Altura\n6.Factor de equilibrio\n7.Salir\n\nQué operación deseas realizar: "));

            switch (op) {
                case 1: 
                    valor = Integer.parseInt(JOptionPane.showInputDialog("Introduce un número"));
                    arbol.insertar(valor);
                    System.out.println(valor + " ");
                    break;
                case 2: 
                    valor = Integer.parseInt(JOptionPane.showInputDialog("Introduce un valor a buscar"));
                    Nodo nodo = arbol.raiz;
                    try {
                        JOptionPane.showMessageDialog(null, "El valor es " + arbol.buscar(valor, nodo).datos);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "El valor es " + arbol.buscar(valor, nodo));
                    }
                    break;
                case 3: 
                    valor = arbol.maximo();
                    JOptionPane.showMessageDialog(null, "El valor máximo es " + valor);
                    break;
                case 4: 
                    valor = arbol.minimo();
                    JOptionPane.showMessageDialog(null, "El valor mínimo es " + valor);
                    break;
                case 5: 
                    valor = arbol.altura();
                    JOptionPane.showMessageDialog(null, "La altura del árbol es " + valor);
                    break;
                case 6:
                    Nodo aux;
                    aux = arbol.raiz;
                    valor = arbol.FE(aux);
                    JOptionPane.showMessageDialog(null, "El factor de equilibrio del árbol es " + valor);
                    break;
                case 7: 
                    JOptionPane.showMessageDialog(null, "Hasta pronto!");
                    break;
                default:
                    break;
            }
        } while (op != 7);
    }
}
