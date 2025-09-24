import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso:");
            System.out.println("  java -jar huffman.jar -c <arquivo_original> <arquivo_comprimido>");
            System.out.println("  java -jar huffman.jar -d <arquivo_comprimido> <arquivo_restaurado>");
            return;
        }

        String option = args[0];
        String inputFile = args[1];
        String outputFile = args[2];

        try {
            long startTime, endTime;
            long elapsed;

            switch (option) {
                case "-c": {
                    byte[] fileBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(inputFile));

                    HuffmanTree arvore = new HuffmanTree(fileBytes);
                    arvore.buildInitialHeap();

                    System.out.println("--------------------------------------------------");
                    System.out.println("ETAPA 1: Tabela de Frequencia de Caracteres");
                    System.out.println("--------------------------------------------------");
                    HuffmanTree.imprimirFrequencias(fileBytes, arvore.frequencias);

                    System.out.println("\n--------------------------------------------------");
                    System.out.println("ETAPA 2: Min-Heap Inicial (Vetor)");
                    System.out.println("--------------------------------------------------");
                    System.out.println(arvore.getHeap());

                    System.out.println("--------------------------------------------------");
                    System.out.println("ETAPA 3: Arvore de Huffman");
                    System.out.println("--------------------------------------------------");
                    arvore.buildTree();
                    System.out.println(arvore);

                    String[] tabela = Tabela.gerarTabela(arvore.root);

                    System.out.println("--------------------------------------------------");
                    System.out.println("ETAPA 4: Tabela de Codigos de Huffman");
                    System.out.println("--------------------------------------------------");
                    Tabela.imprimirTabela(tabela);

                    System.out.println("--------------------------------------------------");
                    System.out.println("ETAPA 5: Resumo da Compressao");
                    System.out.println("--------------------------------------------------");

                    startTime = System.nanoTime();
                    Compressor.compress(inputFile, outputFile, arvore, tabela);
                    endTime = System.nanoTime();
                    elapsed = (endTime - startTime) / 1_000_000; // em ms

                    System.out.println("Tempo de compressão: " + elapsed + " ms");
                    System.out.println("Arquivo comprimido com sucesso!");
                    break;
                }

                case "-d": {
                    startTime = System.nanoTime();
                    Decompressor.decompress(inputFile, outputFile);
                    endTime = System.nanoTime();
                    elapsed = (endTime - startTime) / 1_000_000; // em ms

                    System.out.println("--------------------------------------------------");
                    System.out.println("ETAPA 6: Resumo da Descompressao");
                    System.out.println("--------------------------------------------------");
                    System.out.println("Arquivo descomprimido com sucesso!");
                    System.out.println("Tempo de descompressão: " + elapsed + " ms");
                    break;
                }

                default:
                    System.out.println("Opção inválida: " + option);
                    System.out.println("Use -c para compressão ou -d para descompressão.");
            }

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}