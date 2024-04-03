//Membresía Hash, 353195, José Ángel Ortiz Meraz, 14/11/23
import javax.swing.*;
import java.io.*;

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

    public static boolean membresia_diccionario(String word, int[] hashTable) {
        int hashValue = hash(word, hashTable.length);
        return hashTable[hashValue] == 1;
    }

    public static boolean membresia_email(String word) {
        return word.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public static void main(String[] args) {
        int tableSize = 81974;
        int[] hashTable = new int[tableSize];
        String[] dictionary;

        try {
            file = new File("diccionario.txt");
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line;
            int dictionarySize = 0;
            while ((line = buffer.readLine()) != null) {
                dictionarySize++;
            }
            // Inicializar el diccionario
            dictionary = new String[dictionarySize];
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            int i = 0;
            while ((line = buffer.readLine()) != null) {
                dictionary[i] = line;
                i++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Inicializar la tabla hash en ceros
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = 0;
        }

        // Realizar el hash de cada palabra y marcar la celda correspondiente en la tabla hash
        for (String word : dictionary) {
            int hashValue = hash(word, tableSize);
            hashTable[hashValue] = 1;
        }

        String[] options = {"Probar membresía en el diccionario", "Probar membresía como dirección de correo electrónico", "Salir"};

        int choice;
        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción:",
                    "Menú",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0:
                    String wordDiccionario = JOptionPane.showInputDialog("Ingrese la palabra a verificar en el diccionario:");
                    if (membresia_diccionario(wordDiccionario, hashTable)) {
                        JOptionPane.showMessageDialog(null, "La palabra pertenece al diccionario.");
                    } else {
                        JOptionPane.showMessageDialog(null, "La palabra no pertenece al diccionario.");
                    }
                    break;
                case 1:
                    String wordEmail = JOptionPane.showInputDialog("Ingrese la cadena a verificar como dirección de correo electrónico:");
                    if (membresia_email(wordEmail)) {
                        JOptionPane.showMessageDialog(null, "La cadena es una dirección de correo electrónico válida.");
                    } else {
                        JOptionPane.showMessageDialog(null, "La cadena no es una dirección de correo electrónico válida.");
                    }
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Inténtelo de nuevo.");
            }
        } while (choice != 2);
    }
}
