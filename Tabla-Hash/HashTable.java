//Tabla Hash, 353195, José Ángel Ortiz Meraz, 09/11/23
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

// las colisiones se manejan agrupando elementos con el mismo valor de hash en la misma posición de la tabla hash utilizando listas enlazadas.

public class HashTable {
    private static final int TABLE_SIZE = 100;
    private LinkedList<Pair>[] table;

    public HashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private class Pair {
        String word;
        String hint;

        public Pair(String word, String hint) {
            this.word = word;
            this.hint = hint;
        }
    }

    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash + key.charAt(i)) % TABLE_SIZE;
        }
        return hash;
    }

    public void insert(String word, String hint) {
        int index = hash(word);
        table[index].add(new Pair(word, hint));
    }

    public String search(String word) {
        int index = hash(word);
        LinkedList<Pair> bucket = table[index];
        for (Pair pair : bucket) {
            if (pair.word.equals(word)) {
                return pair.hint;
            }
        }
        return "Palabra no encontrada";
    }

    public void delete(String word) {
        int index = hash(word);
        LinkedList<Pair> bucket = table[index];
        for (Pair pair : bucket) {
            if (pair.word.equals(word)) {
                bucket.remove(pair);
                return;
            }
        }
    }

    public void insertFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Separar la línea en partes 
                String[] parts = line.split(",");
                
                // Hay exactamente dos partes (palabra y pista)
                if (parts.length == 2) {
                    // Obtener la palabra y la pista
                    String word = parts[0].trim();
                    String hint = parts[1].trim();
                    
                    insert(word, hint);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de línea incorrecto: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        int op = 1;
        String word;
        String hint;

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    "ÁRBOL BINARIO DE BÚSQUEDA\n\n1.Insertar palabra -teclado\n2.Insertar palabra -archivo\n3.Buscar palabra\n4.Eliminar palabra\n5.Salir \n\nQué operación deseas realizar: "));

            switch (op) {
                case 1:
                    // inserción desde teclado
                    word = JOptionPane.showInputDialog("Qué palabra deseas insertar?");
                    hint = JOptionPane.showInputDialog("Cuál es la pista de la palabra?");

                    hashTable.insert(word, hint);
                    break;
                case 2:
                    // inserción desde archivo CSV
                    hashTable.insertFromFile("diccionario.csv");
                    break;
                case 3:
                    // búsqueda
                    word = JOptionPane.showInputDialog("Qué palabra deseas bucar?");
                    JOptionPane.showMessageDialog(null, "Pista para '" + word + "': " + hashTable.search(word));
                    break;
                case 4:
                    // eliminación
                    word = JOptionPane.showInputDialog("Qué palabra deseas eliminar?");
                    hashTable.delete(word);
                    JOptionPane.showMessageDialog(null, "Palabra '" + word + "' eliminada.");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Hasta pronto!");
                    break;
            }
        } while (op != 5);
    }
}
