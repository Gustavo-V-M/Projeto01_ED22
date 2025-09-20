import java.nio.charset.Charset;

public class HuffmanTree {
    int[] frequencias = new int[256];
    MinHeap heap = new MinHeap();
    No root = null;
    private int contadorNos = 1;

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

            No interno = new No('\0', a.frequencia + b.frequencia);
            interno.setEsquerda(a);
            interno.setDireita(b);

            heap.insert(interno);
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

    static void imprimirFrequencias(String text, int[] freq){
        java.util.Set<Integer> ordem = new java.util.LinkedHashSet<>();
        byte[] ascii = text.getBytes(Charset.forName("Windows-1252")); // <- aqui

        for (int i = 0; i < ascii.length; i++) {
            int b = ascii[i] & 0xFF;
            if (freq[b] > 0 && b != '\n' && b != 0) ordem.add(b);
        }
        for (int b : ordem) {
            char c = (char) b;
            System.out.println("Caractere '" + c + "' (ASCII: " + b + "): " + freq[b]);
        }
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
        } else if (node == root) {
            sb.append("(RAIZ, ").append(node.frequencia).append(")");
        } else {
            sb.append("(N").append(contadorNos++).append(", ").append(node.frequencia).append(")");
        }
        sb.append("\n");

        printTree(node.esquerda, prefix + "  ", sb);
        printTree(node.direita,  prefix + "  ", sb);
    }


    private byte[] unicodeToAscii(String text) {
        Charset ch =  Charset.forName("Windows-1252"); // Windows-1252 é o nome do charset da tabela de ascii extendida (https://www.ascii-code.com/pt)
        return text.getBytes(ch);
    }

}
