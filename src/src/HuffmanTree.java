import java.nio.charset.Charset;

public class HuffmanTree {
    int[] frequencias = new int[256];
    MinHeap heap = new MinHeap();
    No root = null;
    private int contadorNos = 1;

    public HuffmanTree(byte[] data) {
        this.frequencias = calculateFrequencies(data);
    }

    public HuffmanTree(String text) {
        this.frequencias = calculateFrequencies(text.getBytes(Charset.forName("UTF-8")));
    }

    public void buildInitialHeap() {
        for (int i = 0; i < frequencias.length; i++) {
            if (frequencias[i] > 0) {
                heap.insert((char) i, frequencias[i]);
            }
        }
    }

    public void buildTree() {
        if (heap.getSize() == 0) {
            buildInitialHeap();
        }
        while (heap.getSize() > 1) {
            No a = heap.removeMin();
            No b = heap.removeMin();

            No interno = new No('\0', a.frequencia + b.frequencia);
            interno.setEsquerda(a);
            interno.setDireita(b);

            heap.insert(interno);
        }

        root = heap.removeMin();
    }

    private int[] calculateFrequencies(byte[] data) {
        int[] freq = new int[256];
        for (int i = 0; i < data.length; i++) {
            int b = data[i] & 0xFF;
            freq[b]++;
        }
        return freq;
    }

    static void imprimirFrequencias(byte[] data, int[] freq) {
        java.util.Set<Integer> ordem = new java.util.LinkedHashSet<>();
        for (int i = 0; i < data.length; i++) {
            int b = data[i] & 0xFF;
            if (freq[b] > 0) ordem.add(b);
        }
        for (int b : ordem) {
            char c = (char) b;
            System.out.println("Caractere '" + c + "' (ASCII: " + b + "): " + freq[b]);
        }
    }

    public MinHeap getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(root, "", sb);
        return sb.toString();
    }

    private void printTree(No node, String prefix, StringBuilder sb) {
        if (node == null) return;

        sb.append(prefix);

        if (node.isLeaf()) {
            sb.append("'").append(node.caractere).append("'")
                    .append(" (freq=").append(node.frequencia).append(")");
        } else if (node == root) {
            sb.append("(RAIZ, ").append(node.frequencia).append(")");
        } else {
            sb.append("(N").append(contadorNos++).append(", ").append(node.frequencia).append(")");
        }
        sb.append("\n");

        printTree(node.esquerda, prefix + "  ", sb);
        printTree(node.direita,  prefix + "  ", sb);
    }
}
