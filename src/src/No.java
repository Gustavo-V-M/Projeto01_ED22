public class No implements Comparable<No> {
    char caractere;
    int frequencia;
    No esquerda, direita, parent = null;

    public No(char caractere, int frequencia) {
        this.caractere = caractere;
        this.frequencia = frequencia;
        esquerda = null;
        direita = null;
        
    }

    public No setEsquerda(No esquerda) {
        this.esquerda = esquerda;
        if (esquerda != null) esquerda.parent = this;
        return this;
    }

    public No setDireita(No direita) {
        this.direita = direita;
        if (direita != null) direita.parent = this;
        return this;
    }


    public boolean isLeaf() {
        return (esquerda == null && direita == null);
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    @Override
    public int compareTo(No o) {
        return this.frequencia - o.frequencia;
    }

    // Metodo de teste, na implementação final só tirar
    @Override
    public String toString() {
        return "No: Caractere: " + caractere + ", Frequencia: " + frequencia;
    }
}
