//Función Hash, 353195, José Ángel Ortiz Meraz, 02/10/23
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class HashFunction {
    static File file = null;
    static FileReader reader = null;
    static BufferedReader buffer = null;

    public static int hash(String word, int tableSize) {
        int hash = 0;
        for (char c : word.toCharArray()) {
            hash += c;
        }
        return Math.abs(hash) % tableSize;
    }

    public static void main(String[] args) {
        int tableSize = 81974; 
        ArrayList<LinkedList<String>> hashTable = new ArrayList<>(tableSize);

        for (int i = 0; i < tableSize; i++) {
            hashTable.add(new LinkedList<>());
        }

        ArrayList<String> dictionary = new ArrayList<>();

        try {
            file = new File("Funcion-Hash/diccionario.txt");
            reader = new FileReader(file);
            //FileReader permite leer caracteres
            buffer = new BufferedReader(reader);
            //BufferReader contiene metodos para leer lineas completas
            String line;
            while ((line = buffer.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Realizar el hash de cada palabra y manejar colisiones con listas vinculadas
        for (String word : dictionary) {
            int hashValue = hash(word, tableSize);
            hashTable.get(hashValue).add(word);
        }

        // Crear y escribir las estadísticas de colisiones en un archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Funcion-Hash/colisiones.txt"))) {
            int totalCollisions = 0;
            int maxCollisions = 0;

            for (LinkedList<String> list : hashTable) {
                if (list.size() > 1) {
                    //hay al menos una colisión en esa posición de la tabla hash.
                    totalCollisions += list.size() - 1;
                    maxCollisions = Math.max(maxCollisions, list.size() - 1);
                    //se actualiza con el máximo valor entre su valor actual y el número de colisiones en la celda actual.
                    writer.write("Colisiones en celda: " + (list.size() - 1) + "\n");
                    writer.write("Palabras colisionantes: " + list.toString() + "\n\n");
                }
            }

            writer.write("Total de colisiones: " + totalCollisions + "\n");
            writer.write("Máximo de colisiones en una celda: " + maxCollisions + "\n");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de colisiones: " + e.getMessage());
        }
    }
}