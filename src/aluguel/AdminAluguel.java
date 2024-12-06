package aluguel;

import cliente.AdminClientes;
import cliente.Usuario;
import cliente.UsuarioNormal;
import cliente.UsuarioPlano;
import jogos.Acervo;
import jogos.Jogo;
import jogos.JogoAluguel;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AdminAluguel {
    private ArrayList<Aluguel> alugueis = new ArrayList<>();
    private AdminClientes clientes = new AdminClientes();
    private Acervo acervo = new Acervo();

    public AdminAluguel() {
        this.acervo.carregarJogosDoArquivo("src/dados/jogos.csv");
        this.clientes.carregarClientesDoArquivo("src/dados/clientes.csv");
    }

    public void alugarJogoById(int jogoId, int clienteId){
        Usuario usuario = null;
        Jogo jogoAluguel = null;

        for(Usuario cliente: clientes.getListaClientes()){
            if(cliente.getId() == clienteId){
                usuario = cliente;
                break;
            }
        }

        for(Map.Entry<Integer, Jogo> entry : acervo.getJogos().entrySet()){
            if(entry.getKey() == jogoId){
                jogoAluguel = entry.getValue();
                break;
            }
        }

        if(usuario != null && jogoAluguel != null){
            alugarJogo((JogoAluguel) jogoAluguel, usuario);
        } else {
            if(usuario == null) {
                System.out.println("Usuário não encontrado.");
            }
            if(jogoAluguel == null) {
                System.out.println("Jogo não encontrado.");
            }
        }
    }

    public void alugarJogo(JogoAluguel jogo, Usuario usuario) {
        Date hoje = new Date();
        Aluguel aluguel = new Aluguel(jogo, hoje, usuario, alugueis.size()+1, false);
        if(usuario instanceof UsuarioNormal){
            if(((UsuarioNormal) usuario).getDivida()==0){
                System.out.printf("O valor do seu aluguel é de R$%.2f\n", 10.0);
                alugueis.add(aluguel);

            }else{
                System.out.println("Pague sua divida antes de alugar um jogo caloteiro\n");
            }
        }
        if(usuario instanceof UsuarioPlano){
            alugueis.add(aluguel);
        }
    }

    public void devolverById(int aluguelId) {
        Aluguel aluguelDevolver = null;
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getId() == aluguelId) {
                aluguelDevolver = aluguel;
                break;
            }
        }

        if (aluguelDevolver != null) {
            devolver(aluguelDevolver);
        } else {
            System.out.println("Aluguel com ID " + aluguelId + " não encontrado.\n");
        }
    }
    public void devolver(Aluguel aluguelDoCliente) {
        Date hoje = new Date();
        for(Aluguel aluguel : alugueis){
            if(aluguel.equals(aluguelDoCliente) && !aluguel.isConcluido() && aluguel.getUsuarioAluguel() instanceof UsuarioNormal){
                long diffInMillies = Math.abs(hoje.getTime() - aluguel.getDataAluguel().getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diffInDays >= 7) {
                    System.out.printf("Pague a multa de R$%.2f, por causa de um atraso na entrega de %d dias",(double)(diffInDays - 7)*10, (diffInDays-7));
                }
                aluguel.setConcluido(true);
                System.out.printf("aluguel.Aluguel concluido, muito obrigado %s\n", aluguel.getUsuarioAluguel().getNome());
            } else if (aluguel.getUsuarioAluguel() instanceof UsuarioPlano && !aluguel.isConcluido()){
                aluguel.setConcluido(true);
                System.out.printf("aluguel.Aluguel concluido, muito obrigado %s\n", aluguel.getUsuarioAluguel().getNome());
            } else if (aluguel.isConcluido()) {
                System.out.printf("A devolução do jogo %s ja foi concluido\n", aluguel.getUsuarioAluguel().getNome());
            }
        }
    }

    public void atualizarDivida(Usuario usuario) {
        Date hoje = new Date();
        long tmp = 0;
        for(Aluguel aluguel : alugueis){
            if(aluguel.getUsuarioAluguel().equals(usuario) && !aluguel.isConcluido()){
                long diffInMillies = Math.abs(hoje.getTime() - aluguel.getDataAluguel().getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diffInDays >= 7) {
                    tmp += (diffInDays - 7)*10;
                }
            }
        }
        ((UsuarioNormal)usuario).setDivida(tmp);
    }

    public ArrayList<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(ArrayList<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Aluguel aluguel : alugueis){
            s.append("JOGO: ").append(aluguel.getJogoAluguel().getNome()).append(" USUARIO: ").append(aluguel.getUsuarioAluguel()).append(" DATA: ").append(aluguel.getDataAluguel()).append("\n");
        }
        return s.toString();
    }

    public void pagarDivida(Usuario usuario) {
        ((UsuarioNormal)usuario).setDivida(0);
    }

    public void adicionarAluguel(Aluguel aluguel) {
        alugueis.add(aluguel);
    }

    public void carregarAlugueisDoArquivo(String caminhoArquivo) {
        ArrayList<Aluguel> alugueisCarregados = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // Formato da data no arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                String[] partes = conteudo.split(", ");
                short idJogo = Short.parseShort(partes[0]);
                Date validade = dateFormat.parse(partes[1]);
                short idUsuario = Short.parseShort(partes[2]);
                short id = Short.parseShort(partes[3]);
                boolean status = Boolean.parseBoolean(partes[4]);
                Aluguel aluguel = new Aluguel(acervo.procurarJogoAlugel(idJogo), validade, clientes.pegarUsuarioPorId(idUsuario), id, status);
                alugueisCarregados.add(aluguel);

            }

            System.out.println("Alugeis carregados com sucesso!");
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o arquivo de produtos: " + e.getMessage());
        }
        setAlugueis(alugueisCarregados);
    }


    public void salvarAlugueisNoArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Aluguel alugel : alugueis) {
                bw.write(alugel.toString());
                bw.newLine();
            }
            System.out.println("Alugueis salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de alugueis: " + e.getMessage());
        }
    }
}
