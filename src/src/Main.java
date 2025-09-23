import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String path = "src/src/";
        String testText = path + "teste.txt";
        String outputText = path + "output.huff";
        Scanner input = new Scanner(System.in);

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(testText));

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

            Compressor.compress(testText, outputText, arvore, tabela);

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        String answer;
        System.out.println("Tem arquivo para descomprimir? (Y/N)");
        answer = input.nextLine();
        String descompressText = path + "restaurado.txt";
        try {
            if (answer.equalsIgnoreCase("y")) {
                Descompressor.decompress(outputText, descompressText);
            } else {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
