//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String testText = "BANANA";
        HuffmanTree arvore = new HuffmanTree(testText);

        System.out.println("--------------------------------------------------");
        System.out.println("ETAPA 1: Tabela de Frequencia de Caracteres");
        System.out.println("--------------------------------------------------");
        HuffmanTree.imprimirFrequencias(testText, arvore.frequencias);

        System.out.println("\n--------------------------------------------------");
        System.out.println("ETAPA 2: Min-Heap Inicial (Vetor)");
        System.out.println("--------------------------------------------------");
        //MinHeap.imprimirHeapInicial(arvore.frequencias);

        System.out.println("--------------------------------------------------");
        System.out.println("ETAPA 3: Arvore de Huffman");
        System.out.println("--------------------------------------------------");
        System.out.println(arvore.toString());

        String[] tabela = Tabela.gerarTabela(arvore.root);
        System.out.println("--------------------------------------------------");
        System.out.println("ETAPA 4: Tabela de Codigos de Huffman");
        System.out.println("--------------------------------------------------");
        Tabela.imprimirTabela(tabela);
    }
}
