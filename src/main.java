import aluguel.AdminAluguel;
import cliente.AdminClientes;
import cliente.Usuario;
import comandas.AdminComandas;
import erros.OpcaoInvalidaException;
import jogos.Acervo;
import jogos.Jogo;
import jogos.JogoAluguel;
import produtos.AdminEstoque;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        AdminComandas adminComandas = new AdminComandas();
        AdminClientes adminCliente = new AdminClientes();
        Acervo acervo = new Acervo();
        AdminEstoque adminEstoque = new AdminEstoque();
        AdminAluguel adminAluguel = new AdminAluguel();
        adminComandas.carregarComandasDoArquivo("src/dados/comandas.csv");
        adminCliente.carregarClientesDoArquivo("src/dados/clientes.csv");
        acervo.carregarJogosDoArquivo("src/dados/jogos.csv");
        adminEstoque.carregarProdutosDoArquivo("src/dados/estoque.csv");
        adminAluguel.carregarAlugueisDoArquivo("src/dados/alugueis.csv");



        Scanner input = new Scanner(System.in);
        boolean stop = false;
        while (!stop) {
            System.out.println("O que voce dejesa fazer \n[1] Clientes \n[2] Estoque \n[3] Jogos \n[4] Alugueis \n[5] Comandas \n[6] Sair");
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
                        System.out.println("[1] Ver acervo \n[2] Adicionar Jogo cativo \n[3] Adicionar Jogo Aluguel \n[4] Sair");
                        int opcao3 = input.nextInt();
                        switch (opcao3){
                            case 1:
                                System.out.println(acervo.getJogos());
                                break;
                            case 2:
                                acervo.adicionarJogoCativo();
                                break;
                            case 3:
                                acervo.adicionarJogoAlugavel();
                                break;
                            case 4:
                                break;
                            default:
                                throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3 ou 4.");
                        }
                        break;
                    case 4:
                        System.out.println("[1] Listar alugueis \n[2] Alugar \n[3] Devolver \n[4] Sair");
                        int opcao4 = input.nextInt();
                        switch (opcao4){
                            case 1:
                                System.out.println(adminAluguel.getAlugueis());
                                break;
                            case 2:
                                System.out.println("Qual o id do cliente?\n");
                                int clienteId = input.nextInt();
                                System.out.println("Qual o id do jogo?\n");
                                short jogoId = input.nextShort();
                                adminAluguel.alugarJogoById(jogoId, clienteId);
                                break;
                            case 3:
                                System.out.println("Qual o id do aluguel?\n");
                                adminAluguel.devolverById(input.nextInt());
                                break;
                            case 4:
                                break;
                            default:
                                throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3 ou 4.");
                        }

                        break;
                    case 5:
                        System.out.println("[1] Ativar comanda \n[2] Desativar comanda \n[3] Adicionar produto a comanda \n[4] Listar comandas ativas \n[5] Listar produtos na comanda \n[6] Sair");
                        int opcao5 = input.nextInt();
                        switch (opcao5){
                            case 1:
                                System.out.println("Qual o id da comanda?\n");
                                adminComandas.ativarComanda((byte) input.nextInt());
                                break;
                            case 2:
                                System.out.println("Qual o id da comanda?\n");
                                adminComandas.desativarComanda((byte) input.nextInt());
                                break;
                            case 3:
                                System.out.println("Qual o id da comanda?\n");
                                byte comandaId = input.nextByte();
                                System.out.println("Qual o id do produto?\n");
                                short produtoId = input.nextShort();
                                adminComandas.adicionarProduto(comandaId, produtoId);
                                break;
                            case 4:
                                System.out.println(adminComandas.comandasAtivas());
                                break;
                            case 5:
                                System.out.println("Qual o id da comanda?\n");
                                System.out.println(adminComandas.getProdutos((byte) input.nextInt()));
                                break;
                            case 6:
                                break;
                            default:
                                throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3, 4, 5 ou 6.");
                        }
                        break;
                    case 6:
                        stop = true;
                        adminComandas.salvarComandasNoArquivo("src/dados/comandas.csv");
                        adminCliente.salvarClientesNoArquivo("src/dados/clientes.csv");
                        acervo.salvarJogosNoArquivo("src/dados/jogos.csv");
                        adminEstoque.salvarEstoqueNoArquivo("src/dados/estoque.csv");
                        adminAluguel.salvarAlugueisNoArquivo("src/dados/alugueis.csv");
                        break;
                    default:
                        throw new OpcaoInvalidaException("Opção inválida! Escolha entre 1, 2, 3, 4, 5 ou 6.");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
