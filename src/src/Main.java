import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {
            String testText = "src/src/teste.txt";
            BufferedReader br = new BufferedReader(new FileReader(testText));
            StringBuilder sb = new StringBuilder();

            String line;

            while((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            HuffmanTree arvore = new HuffmanTree(sb.toString());
            System.out.println(arvore);

        } catch (IOException e) {
            System.out.println("Erro: Arquivo não encontrado, Certifique-se de que ele se encontra na pasta do arquivo");
        }
    }
}