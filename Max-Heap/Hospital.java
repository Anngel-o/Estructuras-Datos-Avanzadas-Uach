//MAX-Heap-Hospital, 353195, José Ángel Ortiz Meraz, 26/09/23
import javax.swing.JOptionPane;

class Patient {
    private String name;
    private int priority;
    
    public Patient(String name,int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }
}

public class Hospital {
    Patient[] heap;
    int size;
    int capacity;

    public Hospital(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Patient[capacity + 1];
    }

    public void insertPatient() {
        if (size >= capacity) {
            JOptionPane.showMessageDialog(null, "La sala de espera del Hospital se encuentra llena");
            return;
        }

        String name;
        int priority;
        name = JOptionPane.showInputDialog("¿Cuál es el nombre del paciente?");
        priority = Integer.parseInt(JOptionPane.showInputDialog("¿Cuál es la prioridad de " + name + "?"));

        Patient patient = new Patient(name, priority);
        size++;
        heap[size] = patient; //Se agrega el paciente en el siguiente lugar disponible del array

        int indexCurent = size;

        //mientras el indiceActal sea mayor a 1, es decir, la raíz && comparar la prioridad del índiceActual con la de su padre
        while (indexCurent > 1 && heap[indexCurent].getPriority() > heap[indexCurent / 2].getPriority()) {
            //Intercambiar el paciente con su padre si la prioridad del paciente es mayor
            swap(indexCurent, indexCurent / 2);
            indexCurent /= 2;
            //mover índiceActual al ídice de su padre
        }
        JOptionPane.showMessageDialog(null, name + " agregado a la sala de espera!");
    }

    public void attendPatient() {
        if (size == 0) {
            JOptionPane.showMessageDialog(null, "La sala de espera del Hospital está vacía");
            return;
        }

        //Paciente con mayor prioridad, la raíz del array heap
        Patient patientAttend = heap[1];
        JOptionPane.showMessageDialog(null, "Atendiendo a " + patientAttend.getName() + ", con prioridad: " + patientAttend.getPriority() + ".");

        //Mover el último paciente al primer lugar y amontonar
        heap[1] = heap[size];
        size--;
        heapify(1);
    }

    public void nextPatient() {
        if (size == 0) {
            JOptionPane.showMessageDialog(null, "La sala de espera del Hospital está vacía");
            return;
        }

        Patient nextPatient = heap[1];
        JOptionPane.showMessageDialog(null, "El siguiente paciente a atender es " + nextPatient.getName() + ", con prioridad " + nextPatient.getPriority() + ".");
    }

    public void printPatients() {
        if (size == 0) {
            JOptionPane.showMessageDialog(null, "La sala de espera del Hospital está vacía");
            return;
        } 

        JOptionPane.showMessageDialog(null, "Lista de Pacientes impresa en la consola");
        for (int i = 1; i < heap.length; i++) {
            Patient patient = heap[i];
            System.out.println("Casilla " + i + ": " + patient.getName() + " - Prioridad: " + patient.getPriority() + ".");
        }
    }

    private void heapify(int index) {
        int max = index;
        int left = 2 * index; //2*index+1 para left y 2*index+2 si se trabaja con el array empezando con índice 0 
        int right = 2 * index + 1;

        //si el hijo izquierdo y derecho existen y su prioridad es mayor a la del paciente actualmente con mayor prioridad
        if (left <= size && heap[left].getPriority() > heap[max].getPriority()) {
            max = left;
        }
        if (right <= size && heap[right].getPriority() > heap[max].getPriority()) {
            max = right;
        }

        //si se encuentra un hijo con mayor prioridad
        if (max != index) {
            swap(index, max);
            heapify(max);
        }
    }

    private void swap(int i, int j) {
        Patient temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        int op;
        int capacity;

        capacity = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad del Hospital:"));

        Hospital hospital = new Hospital(capacity);

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("BIENVENIDO A HOSPITAL DEL MONTÓN \n\n1.Registrar Paciente\n2.Mostrar siguiente paciente a atender\n3.Atender paciente\n4.Mostrar lista de pacientes a atender\n5.Salir del programa\n\nSeleccione una opción:"));

            switch (op) {
                case 1:
                    hospital.insertPatient();
                    break;
                case 2:
                    hospital.nextPatient();
                    break;
                case 3:
                    hospital.attendPatient();
                    break;
                case 4:
                    try {
                        hospital.printPatients();
                        System.out.println();
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("");
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