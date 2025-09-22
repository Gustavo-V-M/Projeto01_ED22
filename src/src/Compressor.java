import java.io.*;

public class Compressor {

    public static void compress(String inputFile, String outputFile, HuffmanTree arvore, String[] tabela) throws IOException {

        // Tamanho original
        File inFile = new File(inputFile);
        long originalBytes = inFile.length();

        // Frequências já calculadas na árvore (foi a base para a construção) :contentReference[oaicite:4]{index=4}
        int[] freq = arvore.frequencias;

        long totalBitsEscritos = 0;

        try (DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
             BufferedInputStream bin = new BufferedInputStream(new FileInputStream(inFile));
             BitOutputStream bout = new BitOutputStream(dout)) {

            // Cabeçalho
            dout.writeBytes("HUF1");
            dout.writeLong(originalBytes);

            // Frequências (256 ints) — para reconstruir a árvore na descompressão, conforme PDF :contentReference[oaicite:5]{index=5}
            for (int i = 0; i < 256; i++) {
                dout.writeInt(freq[i]);
            }

            // Escreve os bits conforme a tabela de códigos (gerada a partir da raiz da árvore) :contentReference[oaicite:6]{index=6}
            int b;
            while ((b = bin.read()) != -1) {
                String code = tabela[b & 0xFF];
                if (code == null) continue; // ignorar símbolos não presentes (ex.: \n foi filtrado na freq)
                for (int k = 0; k < code.length(); k++) {
                    bout.writeBit(code.charAt(k) == '1' ? 1 : 0);
                    totalBitsEscritos++;
                }
            }
        }

        // Resumo (ETAPA 5), como pede o PDF :contentReference[oaicite:7]{index=7}
        long compressedBytes = new File(outputFile).length();
        long originalBits = originalBytes * 8;

        System.out.printf("Tamanho original....: %d bits (%d bytes)%n", originalBits, originalBytes);
        System.out.printf("Tamanho comprimido..: %d bytes (aprox. %d bits uteis)%n", compressedBytes, totalBitsEscritos);
        if (originalBytes > 0) {
            double taxa = (1.0 - (compressedBytes / (double) originalBytes)) * 100.0;
            System.out.printf("Taxa de compressao..: %.2f%%%n", taxa);
        } else {
            System.out.println("Taxa de compressao..: N/A (arquivo vazio)");
        }
    }
}
