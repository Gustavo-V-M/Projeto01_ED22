import java.io.*;

public class Compressor {

    public static void compress(String inputFile, String outputFile, HuffmanTree arvore, String[] tabela) throws IOException {

        File inFile = new File(inputFile);
        long originalBytes = inFile.length();

        int[] freq = arvore.frequencias;

        long totalBitsEscritos = 0;

        try (FileOutputStream fout = new FileOutputStream(outputFile);
             DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(fout))) {

            // Cabeçalho
            dout.writeBytes("HUF1");
            dout.writeLong(originalBytes);
            for (int i = 0; i < 256; i++) {
                dout.writeInt(freq[i]);
            }
            dout.flush();

            try (BitOutputStream bout = new BitOutputStream(dout)) {
                try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(inFile))) {
                    int b;
                    while ((b = bin.read()) != -1) {
                        String code = tabela[b & 0xFF];
                        if (code == null) continue;
                        for (int k = 0; k < code.length(); k++) {
                            bout.writeBit(code.charAt(k) == '1' ? 1 : 0);
                            totalBitsEscritos++;
                        }
                    }
                }
            }
        }

        // Resumo (ETAPA 5)
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
