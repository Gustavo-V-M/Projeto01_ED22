public class Tabela {

    public static String[] gerarTabela(No raiz) {
        String[] tabela = new String[256];
        buildCodes(raiz, "", tabela);
        return tabela;
    }

    private static void buildCodes(No node, String codigo, String[] tabela) {
        if (node == null) return;

        if (node.isLeaf()) {
            int idx = ((int) node.caractere) & 0xFF;
            tabela[idx] = codigo.length() == 0 ? "0" : codigo; // tratar caso de único caractere
            return;
        }

        buildCodes(node.esquerda, codigo + "0", tabela);
        buildCodes(node.direita,  codigo + "1", tabela);
    }

    public static void imprimirTabela(String[] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) {
                char c = (char) i;
                String nome;
                if (c == '\n') nome = "\\n";
                else if (c == '\r') nome = "\\r";
                else if (c == '\t') nome = "\\t";
                else if (i == 32) nome = "SPACE";
                else nome = Character.toString(c);

                System.out.println("Caractere '" + nome + "' (ASCII: " + i + "): " + tabela[i]);
            }
        }
    }
}
