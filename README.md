# Projeto01_ED22

Participantes:
- Gustavo Vilela Mitraud - 10400866
- Leoanrdo Custódio Magalhães - 10395913
- Murilo Ramos do Nascimento - 10403318

# Resumo
Este projeto tem como objetivo implementar, em Java, um sistema de compressão e descompressão de arquivos utilizando o algoritmo de Huffman. A proposta envolve a construção de uma fila de prioridades (Min-Heap), a modelagem e o percurso de uma árvore binária de Huffman e a geração de códigos de tamanho variável para os caracteres de um arquivo, de acordo com suas frequências. O sistema deve ser capaz de ler um arquivo de texto, gerar um novo arquivo comprimido (.huff) e, a partir dele, restaurar o original de forma idêntica. Além disso, o trabalho exige a análise de desempenho do algoritmo (tempo de execução em diferentes tamanhos de arquivo) e a avaliação das taxas de compressão em distintos tipos de dados, como textos comuns, códigos-fonte, arquivos repetitivos e arquivos aleatórios.

# Classes Java
O projeto foi estruturado em diversas classes Java, cada uma com uma responsabilidade bem definida, de forma a manter o código organizado, modular e de fácil manutenção. Essa separação permite que cada parte do processo — desde a construção da árvore de Huffman até a leitura e escrita de bits — seja implementada e testada de maneira independente. Além disso, a divisão em classes facilita a compreensão do fluxo do algoritmo, tornando claro o papel de cada componente na compressão e descompressão de arquivos.

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

# Validações
Para garantir a corretude e a eficiência do sistema desenvolvido, foram realizados testes práticos de compressão e descompressão em diferentes cenários. O objetivo desta etapa é verificar se os arquivos comprimidos podem ser restaurados fielmente ao original, além de avaliar o desempenho do algoritmo em termos de tempo de execução e taxa de compressão. Dessa forma, é possível validar não apenas a implementação das estruturas de dados utilizadas (Min-Heap e Árvore de Huffman), mas também comprovar a aplicabilidade do algoritmo em arquivos de naturezas distintas, como textos comuns, códigos-fonte, dados altamente repetitivos e dados aleatórios.

*Primeira Validação:* 2 Capítulos de _Dom Casmurro_
  - Após compilar os arquivos java e gerar o executável _huffman.jar_, fazemos a seguinte linha de comando.
    <img width="586" height="367" alt="Captura de Tela 2025-09-25 às 11 54 15" src="https://github.com/user-attachments/assets/fc491206-9698-4cba-8eeb-0ca005283742" />
  
  - Resultado da execução
    <img width="1710" height="1112" alt="Captura de Tela 2025-09-25 às 11 58 55" src="https://github.com/user-attachments/assets/a6ad6903-33d4-4d1f-be63-e5be72ca316a" />
    <img width="1710" height="1112" alt="Captura de Tela 2025-09-25 às 12 01 30" src="https://github.com/user-attachments/assets/b01e2b22-4a0a-4718-bfa4-d2b094e8ba41" />
    <img width="1710" height="1112" alt="Captura de Tela 2025-09-25 às 12 01 37" src="https://github.com/user-attachments/assets/576bf01a-c15b-461f-b6a1-0641a90c5ae2" />
    <img width="1710" height="1112" alt="Captura de Tela 2025-09-25 às 12 01 43" src="https://github.com/user-attachments/assets/ee25abf4-639b-45ea-af03-0f3c74b3d19b" />
    <img width="441" height="140" alt="Captura de Tela 2025-09-25 às 12 01 45" src="https://github.com/user-attachments/assets/c059d754-21b1-4dc5-b1fe-8fa18b35ec90" />





