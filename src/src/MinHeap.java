import java.util.ArrayList;

public class MinHeap {
    private ArrayList<No> heap;


    public MinHeap() {
        heap = new ArrayList<>();
    }

    public void insert(char caracter, int frequencia) {
        No n = new No(caracter, frequencia);
        heap.add(n);
        int index = heap.size()-1;
        heapifyUp(index);
    }

    public void insert(No n) {
        heap.add(n);
        int index = heap.size()-1;
        heapifyUp(index);
    }

    public int getSize() {
        return heap.size();
    }

    public No removeMin() {
        if (heap.isEmpty()) return null;
        No min = heap.getFirst();
        No last = heap.removeLast();
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    private void heapifyUp(int i) {
        while (i > 0 && isLessThenParent(i)) {
            swap(i, (i-1)/2);
            i = (i-1)/2;
        }
    }

    private void heapifyDown(int i) {
        int n = heap.size();
        while (true) {
            int left = 2*i + 1, right = 2*i + 2, smallest = i;
            if (left < n && heap.get(left).compareTo(heap.get(smallest)) < 0) smallest = left;
            if (right < n && heap.get(right).compareTo(heap.get(smallest)) < 0) smallest = right;
            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else break;
        }
    }

    private boolean isLessThenParent(int index) {
        return heap.get(index).compareTo(heap.get((index-1)/2)) < 0;
    }

    private void swap(int i, int j) {
        No swap = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, swap);
    }

    // Metodo de teste, na implementação final só tirar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = heap.size();
        for (int i = 0; i < n; i++) {
            No node = heap.get(i);
            sb.append(i)
                    .append(": (")
                    .append(node.isLeaf() ? "'" + (char)node.caractere + "'" : "Nó")
                    .append(", freq=")
                    .append(node.frequencia)
                    .append(")\n");
        }
        return sb.toString();
    }
}