import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class AdminClientes {
    private ArrayList<Usuario> listaClientes = new ArrayList<>();

    public void addCliente(Usuario cliente) {
        listaClientes.add(cliente);
    }

    public void carregarClientesDoArquivo(String caminhoArquivo) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // Formato da data no arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("UsuarioPlano")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    Date validade = dateFormat.parse(partes[1]);
                    byte tipoDePlano = Byte.parseByte(partes[0]);
                    String nome = partes[2].replace("'", "");
                    String email = partes[3];
                    String endereco = partes[4];
                    String telefone = partes[5];
                    String cpf = partes[6];
                    int id = Integer.parseInt(partes[7]);
                    UsuarioPlano usuarioPlano = new UsuarioPlano(nome,email,endereco, telefone, cpf, id, tipoDePlano, validade);
                    usuarios.add(usuarioPlano);
                } else if (linha.startsWith("UsuarioNormal")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    double divida = Double.parseDouble(partes[0]);
                    String nome = partes[1].replace("'", "");
                    String email = partes[2];
                    String endereco = partes[3];
                    String telefone = partes[4];
                    String cpf = partes[5];
                    int id = Integer.parseInt(partes[6]);
                    UsuarioNormal usuarioNormal = new UsuarioNormal(nome, email, endereco, telefone, cpf, id, divida);
                    usuarios.add(usuarioNormal);
                }
            }
            setListaClientes(usuarios);
            System.out.println("Clientes carregados com sucesso!");
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o arquivo de clientes: " + e.getMessage());
        }

    }

    public void salvarClientesNoArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario cliente : listaClientes) {
                bw.write(cliente.toString());
                bw.newLine();
            }
            System.out.println("Clientes salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de clientes: " + e.getMessage());
        }
    }

    public ArrayList<Usuario> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Usuario> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Usuario pegarUsuarioPorId(int id) {
        for (Usuario cliente : listaClientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }
}
