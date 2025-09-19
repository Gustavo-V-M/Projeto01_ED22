import java.nio.charset.Charset;

public class HuffmanTree {
    int[] frequencias = new int[256];
    MinHeap heap = new MinHeap();
    No root = null;

    public HuffmanTree(String text) {
        this.frequencias = calculateFrequencies(unicodeToAscii(text));
        buildTree();
    }

    private void buildTree() {
        for (int i = 0; i < frequencias.length; i++) {
            if (frequencias[i] > 0) {
                heap.insert((char) i, frequencias[i]);
            }
        }

        while (heap.getSize() > 1) {
            No a = heap.removeMin();
            No b = heap.removeMin();

            No root = new No('\0', a.frequencia + b.frequencia);
            root.setEsquerda(a);
            root.setDireita(b);

            heap.insert(root);
        }

        root = heap.removeMin();
    }


    private int[] calculateFrequencies(byte[] text) {
        int[] freq = new int[256];
        for(int i = 0; i < text.length; i++){
            char c = (char) (text[i] & 0xFF);
            if(c=='\n' || c=='\0'){ continue; }
            freq[c]++;
        }
        return freq;
    }

    // Metodo de teste, na implementação final só tirar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(root, "", sb);
        return sb.toString();
    }

    // Metodo de teste, na implementação final só tirar
    private void printTree(No node, String prefix, StringBuilder sb) {
        if (node == null) return;

        sb.append(prefix);
        if (node.isLeaf()) {
            sb.append("'").append(node.caractere).append("'")
                    .append(" (freq=").append(node.frequencia).append(")");
        } else {
            sb.append("Nó (freq=").append(node.frequencia).append(")");
        }
        sb.append("\n");

        printTree(node.esquerda, prefix + "  ", sb);
        printTree(node.direita, prefix + "  ", sb);
    }

    private byte[] unicodeToAscii(String text) {
        Charset ch =  Charset.forName("Windows-1252"); // Windows-1252 é o nome do charset da tabela de ascii extendida (https://www.ascii-code.com/pt)
        return text.getBytes(ch);
    }

}
