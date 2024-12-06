package cliente;

import erros.CampoVazioException;
import erros.NumeroNegativoException;
import erros.IdInvalidoExcaption;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminClientes {
    private ArrayList<Usuario> listaClientes = new ArrayList<>();

    public void addCliente(Usuario cliente) {
        listaClientes.add(cliente);
    }

    public void criarClienteNormal(String nome, String email, String endereco, String telefone, String cpf) {
        int id = listaClientes.size();
        UsuarioNormal u = new UsuarioNormal(nome, email, endereco, telefone, cpf, id, 0);
        listaClientes.add(u);
    }

    public void criarClientePlano(String nome, String email, String endereco, String telefone, String cpf, byte tipoPlano, Date validade) {
        int id = listaClientes.size();
        UsuarioPlano u = new UsuarioPlano(nome, email, endereco, telefone, cpf, id, tipoPlano, validade);
        listaClientes.add(u);
    }

    public void criarCliente() {
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("Qual o nome do cliente: ");
            String nome = input.nextLine();

            System.out.println("Qual o email do cliente: ");
            String email = input.nextLine();

            System.out.println("Qual o endereco do cliente: ");
            String endereco = input.nextLine();

            System.out.println("Qual o telefone do cliente: ");
            String telefone = input.nextLine();

            System.out.println("Qual o cpf do cliente: ");
            String cpf = input.nextLine();

            System.out.println("O cliente é normal[1] ou plano [2]: ");
            byte escolha = Byte.parseByte(input.nextLine());

            if (escolha < 0) {
                throw new NumeroNegativoException("Opção de cliente não pode ser negativa.");
            }

            boolean vazio = nome.isEmpty() || email.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || cpf.isEmpty();
            if (escolha == 1) {
                if (vazio) {
                    throw new CampoVazioException("Todos os campos devem ser prenchidos");
                }
                criarClienteNormal(nome, email, endereco, telefone, cpf);
                System.out.println("Cliente criado com sucesso!");
            } else if (escolha == 2) {
                System.out.println("Qual o tipo de plano: ");
                byte tipoPlano = Byte.parseByte(input.nextLine());

                if (tipoPlano < 0) {
                    throw new NumeroNegativoException("Tipo de plano não pode ser negativo.");
                }

                Date hoje = new Date();
                Date validade = new Date(hoje.getTime());
                validade.setYear(validade.getYear() + 1);
                if (vazio) {
                    throw new CampoVazioException("Todos os campos devem ser prenchidos");
                }
                criarClientePlano(nome, email, endereco, telefone, cpf, tipoPlano, validade);
                System.out.println("Cliente criado com sucesso!");
            } else {
                throw new IllegalArgumentException("Opção inválida! Escolha 1 para cliente normal ou 2 para cliente com plano.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Erro: Entrada inválida. Certifique-se de inserir números para opções e tipo de plano.");
        } catch (NumeroNegativoException | IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    public void carregarClientesDoArquivo(String caminhoArquivo) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy"); // Formato da data no arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("cliente.UsuarioPlano")) {
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
                } else if (linha.startsWith("cliente.UsuarioNormal")) {
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
        try{
            if (id < 0 || id > listaClientes.size()) {
                throw new IdInvalidoExcaption("O id fornecido nao existe na lista de clientes");
            }
            for (Usuario cliente : listaClientes) {
                if (cliente.getId() == id) {
                    return cliente;
                }
            }
        }catch(Exception e){
            System.out.println("Erro ao procurar por cliente" + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "AdminClientes{" +
                "listaClientes=" + listaClientes +
                '}';
    }
}
