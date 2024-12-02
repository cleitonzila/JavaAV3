import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Acervo {
    private Map<Integer, Jogo> jogos;

    public Acervo() {
        jogos = new HashMap<>();
    }

    public void adicionarJogo(Jogo jogo) {
        jogos.put(jogo.getId(), jogo);
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

    public void jogosAlugaveis() {
        for (Jogo jogo : jogos.values()) {
            if (jogo instanceof JogoAluguel) {
                if (((JogoAluguel) jogo).isAlugado()) {
                    System.out.printf("JOGO: %s ID: %s: DISPONIVEL %n", jogo.getNome(), jogo.getId());
                } else {
                    System.out.printf("JOGO: %s ID: %s: INDISPONIVEL %n", jogo.getNome(), jogo.getId());
                }
            }
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

                // Criar instância de JogoAluguel e adicionar ao mapa
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

}
