import java.io.*;

public class Decompressor {

    public static void decompress(String inputHuff, String outputTxt) throws IOException {
        File inFile = new File(inputHuff);
        if (!inFile.exists()) {
            throw new FileNotFoundException("Arquivo de entrada nao encontrado: " + inFile.getAbsolutePath());
        }

        try (FileInputStream fis = new FileInputStream(inFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream din = new DataInputStream(bis)) {

            // 1) Magic "HUF1"
            byte[] magic = new byte[4];
            try {
                din.readFully(magic);
            } catch (EOFException e) {
                throw new IOException("Arquivo corrompido ou incompleto: nao foi possivel ler magic.", e);
            }
            String magicStr = new String(magic, "US-ASCII");
            if (!"HUF1".equals(magicStr)) {
                throw new IOException("Formato invalido: magic 'HUF1' nao encontrado. Encontrado='" + magicStr + "'");
            }
            System.out.println("Magic OK: " + magicStr);

            // 2) Tamanho original (bytes)
            long originalBytes;
            try {
                originalBytes = din.readLong();
            } catch (EOFException e) {
                throw new IOException("Arquivo corrompido: nao foi possivel ler originalBytes (long).", e);
            }
            System.out.println("Tamanho original (bytes): " + originalBytes);

            // 3) Tabela de frequencias (256 ints)
            int[] freq = new int[256];
            int distintos = 0;
            for (int i = 0; i < 256; i++) {
                try {
                    freq[i] = din.readInt();
                } catch (EOFException e) {
                    throw new IOException("Arquivo corrompido: tabela de frequencias incompleta (nao houve 256 ints).", e);
                }
                if (freq[i] > 0) distintos++;
            }
            System.out.println("Simbolos distintos na tabela: " + distintos);

            // preparar arquivo de saída (cria diretório se preciso)
            File outFile = new File(outputTxt);
            File parent = outFile.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    System.out.println("Aviso: nao foi possivel criar diretorio pai: " + parent.getAbsolutePath());
                }
            }

            if (originalBytes == 0) {
                // cria arquivo vazio e sai
                try (FileOutputStream fout = new FileOutputStream(outFile)) {
                    // arquivo criado vazio
                }
                System.out.println("Arquivo original vazio. Arquivo de saída criado vazio: " + outFile.getAbsolutePath());
                return;
            }

            // Caso 1 símbolo: escreve diretamente sem ler bits
            if (distintos == 1) {
                int simbolo = -1;
                for (int i = 0; i < 256; i++) {
                    if (freq[i] > 0) { simbolo = i; break; }
                }
                if (simbolo == -1) throw new IOException("Frequencias inconsistentes (distintos==1 mas simbolo nao encontrado).");

                try (FileOutputStream fout = new FileOutputStream(outFile)) {
                    for (long w = 0; w < originalBytes; w++) {
                        fout.write(simbolo);
                    }
                }
                System.out.println("Descompressao concluida (arquivo com 1 simbolo). Saida: " + outFile.getAbsolutePath());
                return;
            }

            // 4) Reconstruir árvore de Huffman
            No raiz = reconstruirArvore(freq);
            if (raiz == null) throw new IOException("Nao foi possivel reconstruir a arvore.");

            // 5) Ler bits e decodificar até escrever 'originalBytes'
            long escritos = 0;
            try (BitInputStream bitIn = new BitInputStream(din);
                 FileOutputStream fout = new FileOutputStream(outFile)) {

                while (escritos < originalBytes) {
                    No p = raiz;
                    while (!p.isLeaf()) {
                        int bit = bitIn.readBit();
                        if (bit == -1) {
                            throw new EOFException("Fim do arquivo antes de completar a descompressao (bits).");
                        }
                        p = (bit == 0) ? p.esquerda : p.direita;
                        if (p == null) throw new IOException("Caminho de bits invalido (arquivo corrompido).");
                    }
                    fout.write((int) p.caractere);
                    escritos++;
                }

                fout.flush();
            }

            System.out.println("Descompressao concluida com sucesso. Escreveu bytes: " + escritos + " -> " + outFile.getAbsolutePath());
        }
    }

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
            No pai = new No('\0', a.frequencia + b.frequencia);
            pai.setEsquerda(a);
            pai.setDireita(b);
            heap.insert(pai);
        }
        return heap.removeMin();
    }
}
