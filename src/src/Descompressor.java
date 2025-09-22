import java.io.*;

public class Descompressor {

    public static void decompress(String inputHuff, String outputTxt) throws IOException {
        try (DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream(inputHuff)));
             FileOutputStream fout = new FileOutputStream(outputTxt)) {

            // 1) Magic "HUF1"
            byte[] magic = new byte[4];
            din.readFully(magic);
            String magicStr = new String(magic, "US-ASCII");
            if (!"HUF1".equals(magicStr)) {
                throw new IOException("Formato invalido: magic 'HUF1' nao encontrado");
            }

            // 2) Tamanho original (bytes)
            long originalBytes = din.readLong();

            // 3) Tabela de frequencias (256 ints)
            int[] freq = new int[256];
            int distintos = 0;
            for (int i = 0; i < 256; i++) {
                freq[i] = din.readInt();
                if (freq[i] > 0) distintos++;
            }

            // Arquivo vazio: nada a fazer
            if (originalBytes == 0) return;

            // Caso 1 símbolo: escreve direto sem ler bits
            if (distintos == 1) {
                int simbolo = -1;
                for (int i = 0; i < 256; i++) {
                    if (freq[i] > 0) { simbolo = i; break; }
                }
                if (simbolo == -1) throw new IOException("Frequencias inconsistentes.");
                for (long w = 0; w < originalBytes; w++) {
                    fout.write(simbolo);
                }
                return;
            }

            // 4) Reconstruir árvore de Huffman
            No raiz = reconstruirArvore(freq);
            if (raiz == null) throw new IOException("Nao foi possivel reconstruir a arvore.");

            // 5) Ler bits e decodificar até escrever 'originalBytes'
            long escritos = 0;
            try (BitInputStream bin = new BitInputStream(din)) {
                while (escritos < originalBytes) {
                    No p = raiz;
                    while (!p.isLeaf()) {
                        int bit = bin.readBit();
                        if (bit == -1) {
                            throw new EOFException("Fim do arquivo antes de completar a descompressao");
                        }
                        p = (bit == 0) ? p.esquerda : p.direita;
                        if (p == null) throw new IOException("Caminho de bits invalido (arquivo corrompido).");
                    }
                    fout.write((int) p.caractere);
                    escritos++;
                }
            }
        }
    }

    /** Constrói a árvore com base nas frequências, usando teu MinHeap (insert/removeMin/getSize). */
    private static No reconstruirArvore(int[] freq) {
        MinHeap heap = new MinHeap();
        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                heap.insert((char) i, freq[i]);
            }
        }
        if (heap.getSize() == 0) return null;

        while (heap.getSize() > 1) {
            No a = heap.removeMin();
            No b = heap.removeMin();
            No pai = new No('\0', a.frequencia + b.frequencia); // nó interno (caractere dummy)
            pai.setEsquerda(a);
            pai.setDireita(b);
            heap.insert(pai); // usa a sobrecarga insert(No)
        }
        return heap.removeMin();
    }
}
