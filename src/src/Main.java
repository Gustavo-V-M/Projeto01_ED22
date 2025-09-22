import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            String path = "src/src/";
            String testText = path + "teste.txt";
            String outputText = path + "output.huff";
            Scanner input = new Scanner(System.in);


            try (BufferedReader br = new BufferedReader
                    (new InputStreamReader(new FileInputStream(testText), Charset.forName("UTF-8")))) {
            StringBuilder sb = new StringBuilder();

            String line;

            while((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            HuffmanTree arvore = new HuffmanTree(sb.toString());

            System.out.println("--------------------------------------------------");
            System.out.println("ETAPA 1: Tabela de Frequencia de Caracteres");
            System.out.println("--------------------------------------------------");
            HuffmanTree.imprimirFrequencias(sb.toString(), arvore.frequencias);

            System.out.println("\n--------------------------------------------------");
            System.out.println("ETAPA 2: Min-Heap Inicial (Vetor)");
            System.out.println("--------------------------------------------------");
            //MinHeap.imprimirHeapInicial(arvore.frequencias);

            System.out.println("--------------------------------------------------");
            System.out.println("ETAPA 3: Arvore de Huffman");
            System.out.println("--------------------------------------------------");
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

            String answer;
            System.out.println("Tem arquivo para descomprimir? (Y/N)");
            answer = input.nextLine();
            String descompressText = path + "restaurado.txt";
            if (answer.equalsIgnoreCase("y")) {
                    Descompressor.decompress(outputText, descompressText);
            } else {
                return;
            }

            } catch (IOException e) {
            System.out.println("Erro: Arquivo não encontrado, Certifique-se de que ele se encontra na pasta do arquivo");
        }
    }
}