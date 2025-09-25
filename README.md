# Projeto01_ED22

Participantes:
- Gustavo Vilela Mitraud - 10400866
- Leoanrdo Custódio Magalhães - 10395913
- Murilo Ramos do Nascimento - 10403318

# Resumo
Este projeto tem como objetivo implementar, em Java, um sistema de compressão e descompressão de arquivos utilizando o algoritmo de Huffman. A proposta envolve a construção de uma fila de prioridades (Min-Heap), a modelagem e o percurso de uma árvore binária de Huffman e a geração de códigos de tamanho variável para os caracteres de um arquivo, de acordo com suas frequências. O sistema deve ser capaz de ler um arquivo de texto, gerar um novo arquivo comprimido (.huff) e, a partir dele, restaurar o original de forma idêntica. Além disso, o trabalho exige a análise de desempenho do algoritmo (tempo de execução em diferentes tamanhos de arquivo) e a avaliação das taxas de compressão em distintos tipos de dados, como textos comuns, códigos-fonte, arquivos repetitivos e arquivos aleatórios.

# Classes Java
- Main.java
  A classe Main é o ponto de entrada do programa, responsável por interpretar os comandos da linha de execução e chamar os módulos de compressão ou descompressão de arquivos com    Huffman, exibindo também as etapas e o tempo de processamento.

- HuffmanTree.java
  Constrói a árvore de Huffman a partir das frequências dos caracteres e gera a base para os códigos usados na compressão e descompressão.

 - Tabela.java
   Responsável por criar e imprimir a tabela de códigos de Huffman, ligando cada caractere ao seu código binário.

- Compressor.java
  Implementa o processo de compressão: aplica os códigos de Huffman sobre o arquivo original e gera o arquivo comprimido.

- Decompressor.java
  Realiza a descompressão do arquivo, reconstruindo o conteúdo original ao percorrer a árvore de Huffman.

- MinHeap.java
  Estrutura auxiliar que organiza os nós de acordo com suas frequências, permitindo a construção eficiente da árvore de Huffman.

- No.java
  Representa os nós da árvore de Huffman, armazenando caractere, frequência e referências para os filhos.

- BitInputStream.java
  Fornece leitura de bits individuais a partir de um fluxo de entrada, essencial para interpretar os dados comprimidos.

- BitOutputStream.java
  Permite escrever bits de forma compacta em um fluxo de saída, garantindo que os dados sejam gravados no formato comprimido.
