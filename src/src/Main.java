import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            String testText = "src/src/teste.txt";

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

        } catch (IOException e) {
            System.out.println("Erro: Arquivo não encontrado, Certifique-se de que ele se encontra na pasta do arquivo");
        }
    }
}