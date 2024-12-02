import cliente.AdminClientes;
import erros.OpcaoInvalidaException;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        AdminComandas adminComandas = new AdminComandas();
        AdminClientes adminCliente = new AdminClientes();
        Acervo acervo = new Acervo();
        AdminEstoque adminEstoque = new AdminEstoque();
        adminComandas.carregarComandasDoArquivo("src/dados/comandas.csv");
        adminCliente.carregarClientesDoArquivo("src/dados/clientes.csv");
        acervo.carregarJogosDoArquivo("src/dados/jogos.csv");
        adminEstoque.carregarProdutosDoArquivo("src/dados/estoque.csv");

        Scanner input = new Scanner(System.in);
        boolean stop = false;
        while (!stop) {
            System.out.println("O que voce dejesa fazer \n[1] Clientes \n[2] Estoque \n[3] Jogos \n[4] Alugueis \n[5] Sair");
            int opcao = input.nextInt();
            try{
                switch (opcao) {
                    case 1:
                        System.out.println("[1] Ver lista de clientes \n[2] Adicionar Cliente \n[3] Procuarar Cliente \n[4] Sair");
                        int proximaOpcao = input.nextInt();
                        switch (proximaOpcao) {
                            case 1:
                                System.out.println(adminCliente.getListaClientes());
                                break;
                            case 2:
                                adminCliente.criarCliente();
                                adminCliente.salvarClientesNoArquivo("src/dados/clientes.csv");
                                break;
                            case 3:
                                System.out.println("Qual o id do cliente?\n");
                                System.out.println(adminCliente.pegarUsuarioPorId(input.nextInt()));
                                break;
                            case 4:
                                break;
                            default:
                                throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3 ou 4.");
                        }
                        break;
                    case 2:
                        System.out.println("[1] Ver estoqe \n[2] Adicionar produto \n[3] Procuarar produto \n[4] Sair");
                        int opcao2 = input.nextInt();
                        switch (opcao2){
                            case 1:
                                System.out.println(adminEstoque.getEstoque());
                                break;
                            case 2:
                                adminEstoque.criarProduto();
                                break;
                            case 3:
                                System.out.println("Qual o id do estoque?\n");
                                adminEstoque.getProduto((short) input.nextInt());
                                break;
                            case 4:
                                break;
                            default:
                                throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3 ou 4.");
                        }
                        break;
                    case 3:
                        System.out.println(acervo.getJogos());
                        break;
                    case 4:
                        break;
                    case 5:
                        stop = true;
                        break;
                    default:
                        throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3, 4 ou 5.");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
