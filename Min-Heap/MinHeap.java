public class MinHeap {
    private int[] heap;
    private int size; //cantidad de elementos 

    public MinHeap(int maxSize) {
        this.size = 0;
        this.heap = new int[maxSize];
    }

    public MinHeap(int[] array) {
        this.size = array.length;
        this.heap = array;
        buildHeap(); //se construye el heap
    }

    public int size() {
        return size;
    }

    public void insert(int date) {
        if (size == heap.length) {
            throw new IllegalStateException("Min-Heap Lleno");
        } else {
            //determinar la ubicación a insertar
            int posCurrent = size;
            //se inserta el elemento en el array
            heap[posCurrent] = date;
            size++;

            //nvelación del heap
            while(heap[posCurrent] < heap[parent(posCurrent)]) { //si el nodo insertado es menor que su padre debemos intercambiar las posiciones
                //cambiar la posición del hjo por la del padre y viceversa
                change(posCurrent, parent(posCurrent));
                //actualizar la posición actual después de hacer el intercambio
                posCurrent = parent(posCurrent);
            }
        }
    }

    public void buildHeap() {
        for (int i = size/2; i >= 0; i--) {
            //recorre desde la mitad del arreglo hasta llegar a la posición 0 del arreglo
            heapify(i, size -1);
            //(posición a empezar el heap, donde termina)
        }
    }

    public void heapify(int i, int j) {
        //Comprueba que el hijo izquierdo de la posicion i sea un valor menor o igual a j
        if ((leftChild(i)) <= j) {
            //k almacena la posicion del valor que tiene que subir
            int k;
            //Si el hijo derecho de i es menor que j significa que tiene que elegir el mayor de los dos hijos
            if ((rightChild(i)) <= j) {
                //Se elige la posición que tiene el valor mayor
                if(heap[rightChild(i)] <= heap[leftChild(i)]) {
                    k = rightChild(i);
                }else {
                    k = leftChild(i);
                }
            } else {//Significa que solo tiene hijo izquierdo
                k = leftChild(i);
            }
            //Revisa si es mayor, se hace el intercambio para armar nuevamente el min-heap
            if (heap[i] > heap[k]) {
                change(i, k);
                heapify(k, j);
            }
        }
    }

    private void change(int i, int j) {
        int aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }
    
    private int parent(int pos) {
        //posición del padre de un nodo
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        //posición del hijo izquierdo de un nodo
        return 2 * pos + 1;
    }

    private int rightChild(int pos) {
        //posición del hijo derecho de un nodo
        return 2 * pos + 2;
    }

    public int getMin() {
        return heap[0];
    }

    public void showArray() {
        System.out.println("--Inicio--");
        for (int i = 0; i < heap.length; i++) {
            System.out.print(heap[i] + "|");
        }
        System.out.println();
        System.out.println("--Fin--");
    }

    public void printTree() {
        for (int i = 0; i < Math.ceil(size/2); i++) {
            try{
                System.out.print(" PADRE : " + heap[i]);
            }catch(Exception e) {
                System.out.println("");
            }
            
            try{
                System.out.print(" HIJO IZQ : " + heap[leftChild(i)]);
            }catch(Exception e) {
                System.out.println("");
            }
            
            try{
                System.out.print(" HIJO DER :" + heap[rightChild(i)]);
            }catch(Exception e) {
                System.out.println("");
            }
            System.out.println();    
        }
    }

    public static void main(String[] args) {
        //Cuando el Heap se encuentra vacio y se van agregando los elementos uno a uno.
        //El Heap se va nivelando inmediatamente a medida que se insertan.
        MinHeap minHeap = new MinHeap(7);
        
        minHeap.insert(30);
        minHeap.insert(48);
        minHeap.insert(15);
        minHeap.insert(67);
        minHeap.insert(24);
        minHeap.insert(17);
        minHeap.insert(5);
        
        minHeap.printTree();
        minHeap.showArray();
    }
}
