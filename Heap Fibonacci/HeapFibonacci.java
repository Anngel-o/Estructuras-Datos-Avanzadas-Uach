//Heap Fibonacci, 353195, José Ángel Ortiz Meraz, 04/10/23
import javax.swing.JOptionPane;

class Node {
    Node next;
    Node prev;
    Node child;
    Node parent;
    int key;
    
    public Node(int key) {
        this.key = key;
        this.prev = this.next = this.child = this.parent = null;
    }
}

public class HeapFibonacci {
    Node minNode;
    int size;
    
    public HeapFibonacci() {
        this.minNode = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int key) {
        Node newNode = new Node(key);
        if (minNode == null) {
            minNode = newNode;
        } else {
            newNode.next = minNode;
            newNode.prev = minNode.prev;
            minNode.prev = newNode;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            }
            if (key < minNode.key) {
                minNode = newNode;
            }
        }
        JOptionPane.showMessageDialog(null, key + " agregado");
        size++;
    }

    public int findMin() {
        if (minNode == null) {
            throw new IllegalStateException("El heap de Fibonacci está vacío");
        }
        return minNode.key;
    }

    public void deleteMin() {
        if (minNode == null) {
            throw new IllegalStateException("El heap de Fibonacci está vacío");
        }
    
        Node min = minNode;
    
        // Eliminar el nodo mínimo de la lista de raíces
        if (min == min.next) { // Si solo hay un nodo en el heap
            minNode = null;
        } else {
            Node nextMin = min.next;
            min.prev.next = nextMin;
            nextMin.prev = min.prev;
            minNode = nextMin;
        }
    
        // Establecer el nuevo mínimo
        if (min.child != null) {
            Node child = min.child;
            Node temp = child;
            do {
                child.parent = null;
                if (child == child.next) {
                    temp = null;
                } else {
                    temp = child.next;
                    child.next = minNode;
                    child.prev = minNode.prev;
                    minNode.prev.next = child;
                    minNode.prev = child;
                }
                child = temp;
            } while (child != null);
        }
    
        JOptionPane.showMessageDialog(null, min.key + " eliminado");
        size--;
    }

    public void displayRoots() {
        if (minNode == null) {
            System.out.println("El heap de Fibonacci está vacío.");
        } else {
            System.out.println("Raíces del heap de Fibonacci:");
            Node current = minNode;
            do {
                System.out.println(current.key);
                current = current.next;
            } while (current != minNode);
        }
    }

    public static void main(String[] args) {
        int op;
        int key;
        int min;
        HeapFibonacci heap = new HeapFibonacci();
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("BIENVENIDO A HEAP FIBONACCI \n\n1.Insertar\n2.Eliminar mínimo\n3.Consultar mínimo\n4.Mostrar raíces\n5.Salir\n\nSeleccione una opción:"));

            switch (op) {
                case 1:
                    key = Integer.parseInt(JOptionPane.showInputDialog("¿Qué valor desea insertar?"));
                    heap.insert(key);
                    break;
                case 2:
                    try {
                        if (!heap.isEmpty()) {
                        heap.deleteMin();
                        } else {
                            JOptionPane.showMessageDialog(null, "El heap de Fibonacci está vacío.");
                        }
                    } catch (Exception e) {
                        System.out.print("");
                    }
                    break;
                case 3:
                    if (!heap.isEmpty()) {
                        min = heap.findMin();
                        JOptionPane.showMessageDialog(null, min + " es el valor mínimo");
                        } else {
                            JOptionPane.showMessageDialog(null, "El heap de Fibonacci está vacío.");
                        }
                    break;
                case 4:
                    try {
                        heap.displayRoots();
                    } catch (Exception e) {
                        System.out.print("");
                    }
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Hasta pronto!");
                    break;            
                default:
                    JOptionPane.showMessageDialog(null, "Opción no valida!");
                    break;
            }
        } while (op != 5);
    }
}
