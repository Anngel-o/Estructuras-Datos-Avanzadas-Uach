//Standard Trie, 353195, José Ángel Ortiz Meraz, 11/010/23
package Trie;

import javax.swing.JOptionPane;

class Node {
    Node[] children;
    boolean isEnd;

    public Node() {
        children = new Node[26]; // Arreglo de nodos para las letras.
        isEnd = false;
    }
}

public class Trie {
    private Node root;

    public Trie() {
        root = new Node();
    }

    // Verificar si un nodo es vacío (no tiene hijos)
    private boolean isEmptyNode(Node node) {
        for (Node child : node.children) {
            if (child != null) {
                return false; // El nodo no está vacío
            }
        }
        return true; // El nodo está vacío
    }

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            // calcular el índice en el arreglo children para buscar o crear el nodo hijo
            // correspondiente al carácter ch
            // Restando 'a' a la letra ch, se realiza una conversión de caracteres a números
            // enteros. Si ch es 'a' --> ch - 'a' = 97 - 97 = 0.
            char ch = word.charAt(i);
            if (node.children[ch - 'a'] == null) {
                node.children[ch - 'a'] = new Node();
            }
            node = node.children[ch - 'a'];
        }
        node.isEnd = true; // Marcar el último nodo como fin de palabra
    }

    public boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.children[ch - 'a'] == null) {
                return false; // La letra no existe en el Trie
            }
            node = node.children[ch - 'a'];
        }
        return node.isEnd; // Verificar si es una palabra completa
    }

    public boolean startsWith(String prefix) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (node.children[ch - 'a'] == null) {
                return false; // El prefijo no coincide con ninguna palabra en el Trie
            }
            node = node.children[ch - 'a'];
        }
        return true; // El prefijo coincide con al menos una palabra en el Trie
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(Node node, String word, int index) {
        if (index == word.length()) {
            if (!node.isEnd) {
                return false; // La palabra no está presente
            }
            node.isEnd = false; // Desmarcar el nodo como fin de palabra
            return isEmptyNode(node); // Verificar si el nodo debe ser eliminado
        }
        char ch = word.charAt(index);
        if (node.children[ch - 'a'] == null) {
            return false; // La letra no existe
        }
        boolean shouldDeleteCurrentNode = delete(node.children[ch - 'a'], word, index + 1);
        if (shouldDeleteCurrentNode) {
            node.children[ch - 'a'] = null; // Eliminar el nodo actual
            return isEmptyNode(node);
        }
        return false;
    }

    public void autocomplete(String prefix) {
        Node node = root;
        StringBuilder currentPrefix = new StringBuilder();
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (node.children[ch - 'a'] == null) {
                return; // No hay palabras que coincidan con el prefijo
            }
            currentPrefix.append(ch);
            node = node.children[ch - 'a'];
        }
        autocompleteAuxiliary(node, currentPrefix);
    }

    private void autocompleteAuxiliary(Node node, StringBuilder currentPrefix) {
        if (node.isEnd) {
            // Imprimir la palabra completa
            JOptionPane.showMessageDialog(null, currentPrefix.toString());
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            Node child = node.children[ch - 'a'];
            if (child != null) {
                currentPrefix.append(ch);
                autocompleteAuxiliary(child, currentPrefix); // Recursivamente autocompletar palabras
                currentPrefix.deleteCharAt(currentPrefix.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        int op = 1;
        String word;

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    "ÁRBOL BINARIO DE BÚSQUEDA\n\n1.Insertar\n2.Eliminar\n3.Buscar\n4.Autocompletar\n5.Verificar prefijo\n6.Salir\n\nQué operación deseas realizar: "));

            switch (op) {
                case 1:
                    word = JOptionPane.showInputDialog("Qué palabra deseas insertar?");
                    trie.insert(word);
                    break;
                case 2:
                    word = JOptionPane.showInputDialog("Qué palabra deseas eliminar?");
                    trie.delete(word);
                    break;
                case 3:
                    word = JOptionPane.showInputDialog("Qué palabra deseas bucar?");
                    if (trie.search(word)) {
                        JOptionPane.showMessageDialog(null, "'" + word + "' existe en el Trie.");
                    } else {
                        JOptionPane.showMessageDialog(null, "'" + word + "' no existe en el Trie.");
                    }
                    break;
                case 4:
                    word = JOptionPane.showInputDialog("Qué prefijo deseas autocompletar?");
                    trie.autocomplete(word);
                    break;
                case 5:
                    word = JOptionPane.showInputDialog("Qué prefijo deseas saber si existe?");
                    if (trie.startsWith(word)) {
                        JOptionPane.showMessageDialog(null, "Al menos una palabra comienza con '" + word + "'.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay palabras que comiencen con '" + word + "'.");
                    }
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Hasta pronto!");
                    break;
                default:
                    break;
            }
        } while (op != 6);
    }
}