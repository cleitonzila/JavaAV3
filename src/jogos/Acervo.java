package jogos;

import erros.CampoVazioException;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Acervo {
    private Map<Integer, Jogo> jogos;

    public Acervo() {
        jogos = new HashMap<>();
    }

    public void adicionarJogo(Jogo jogo) {
        jogos.put(jogo.getId(), jogo);
        salvarJogosNoArquivo("src/dados/jogos.csv");
    }

    public void removerJogo(int id) {
        jogos.remove(id);
    }

    public Map<Integer, Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(Map<Integer, Jogo> jogos) {
        this.jogos = jogos;
    }

    public void adicionarJogoCativo() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Digite o nome do jogo: ");
            String nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'nome' não pode estar vazio.");
            }

            System.out.print("Digite o tempo estimado do jogo (short): ");
            String tempoInput = scanner.nextLine();
            if (tempoInput.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'tempo' não pode estar vazio.");
            }
            short tempo = Short.parseShort(tempoInput);

            System.out.print("Digite o número de jogadores (byte): ");
            String numeroJogadoresInput = scanner.nextLine();
            if (numeroJogadoresInput.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'número de jogadores' não pode estar vazio.");
            }
            byte numeroJogadores = Byte.parseByte(numeroJogadoresInput);

            int novoId = jogos.size() + 1;

            JogoCativo jogoCativo = new JogoCativo(
                    nome,
                    novoId,
                    tempo,
                    numeroJogadores
            );

            jogos.put(novoId, jogoCativo);

            System.out.println("Jogo cativo adicionado com sucesso: " + jogoCativo);
        } catch (CampoVazioException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Certifique-se de inserir valores numéricos corretos.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }


    public void adicionarJogoAlugavel() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Digite o tipo do jogo (byte): ");
            String tipoInput = scanner.nextLine();
            if (tipoInput.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'tipo' não pode estar vazio.");
            }
            byte tipo = Byte.parseByte(tipoInput);

            System.out.print("Digite o nome do jogo: ");
            String nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'nome' não pode estar vazio.");
            }

            System.out.print("Digite o tempo estimado do jogo (short): ");
            String tempoInput = scanner.nextLine();
            if (tempoInput.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'tempo' não pode estar vazio.");
            }
            short tempo = Short.parseShort(tempoInput);

            System.out.print("Digite o número de jogadores (byte): ");
            String numeroJogadoresInput = scanner.nextLine();
            if (numeroJogadoresInput.trim().isEmpty()) {
                throw new CampoVazioException("O campo 'número de jogadores' não pode estar vazio.");
            }
            byte numeroJogadores = Byte.parseByte(numeroJogadoresInput);

            int novoId = jogos.size() + 1;

            JogoAluguel jogoAluguel = new JogoAluguel(
                    nome,
                    novoId,
                    tempo,
                    numeroJogadores,
                    tipo,
                    false
            );

            jogos.put(novoId, jogoAluguel);

            System.out.println("Jogo adicionado com sucesso: " + jogoAluguel);
        } catch (CampoVazioException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Certifique-se de inserir valores numéricos corretos.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }


    public void jogosDisponiveis() {
        for (Jogo jogo : jogos.values()) {
            if (jogo instanceof JogoAluguel && ((JogoAluguel) jogo).isAlugado()) {
                System.out.printf("JOGO: %s ID: %s: DISPONIVEL %n", jogo.getNome(), jogo.getId());
            }
        }
    }

    public JogoAluguel procurarJogoAlugel(int id) {
        if(jogos.get(id) instanceof JogoAluguel) {
            return (JogoAluguel) jogos.get(id);
        }else {
            return null;
        }
    }

    public void carregarJogosDoArquivo(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            // Ignorar cabeçalho
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                boolean alugado = Boolean.parseBoolean(dados[2]);
                short tempo = Short.parseShort(dados[3]);
                byte tipo = Byte.parseByte(dados[4]);
                byte numeroJogadores = Byte.parseByte(dados[5]);

                // Criar instância de jogos.JogoAluguel e adicionar ao mapa
                JogoAluguel jogo = new JogoAluguel(nome, id, tempo, numeroJogadores, tipo, alugado);
                adicionarJogo(jogo);
            }
            System.out.println("Jogos carregados com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
    }

    public void salvarJogosNoArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            // Escrever o cabeçalho
            bw.write("id,nome,alugado,tempo,tipo,numeroJogadores");
            bw.newLine();

            // Escrever cada jogo no arquivo
            for (Jogo jogo : jogos.values()) {
                if (jogo instanceof JogoAluguel) {
                    JogoAluguel jogoAluguel = (JogoAluguel) jogo;
                    String linha = String.format(
                            "%d,%s,%b,%d,%d,%d",
                            jogoAluguel.getId(),
                            jogoAluguel.getNome(),
                            jogoAluguel.isAlugado(),
                            jogoAluguel.getTempo(),
                            jogoAluguel.getTipo(),
                            jogoAluguel.getNumeroJogadores()
                    );
                    bw.write(linha);
                    bw.newLine();
                }
            }

            System.out.println("Jogos salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Acervo{" +
                "jogos=" + jogos +
                '}';
    }
}
