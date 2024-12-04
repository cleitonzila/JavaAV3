package produtos;

import erros.CampoVazioException;
import erros.IdInvalidoExcaption;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AdminEstoque {
    private ArrayList<Produto> estoque = new ArrayList<>();

    public ArrayList<Produto> getEstoque() {
        return estoque;
    }

    public void criarProdutoAlimento(int dia, int mes, int ano, String nome, double preco) {
        Date validade = new Date(dia, mes, ano);
        ProdutoAlimento p = new ProdutoAlimento(validade, (short) estoque.size(), nome, preco);
        adicionarProduto(p);
    }

    public void criarProdutoEvento(int dia, int mes, int ano, String nome, double preco, String premio) {
        Date diaDoEvento = new Date(dia, mes, ano);
        ProdutoEvento p = new ProdutoEvento((short) estoque.size(), nome, preco, diaDoEvento, premio);
        adicionarProduto(p);
    }

    public void criarProdutoNaoAlimento(String nome, double preco) {
        ProdutosNaoAlimento p = new ProdutosNaoAlimento((short) estoque.size(), nome, preco);
        adicionarProduto(p);
    }
    public void criarProduto(){
        Scanner input = new Scanner(System.in);
        System.out.println("Quanto o tipo de produto que voce quer criar: [1] Alimento [2] Evento [3] Nao Alimento");
        try{
            int escolha = input.nextInt();
            switch(escolha){
                case 1:
                    System.out.println("Qual o dia da validade?");
                    int diaAlimento = input.nextInt();
                    System.out.println("Qual o mes da validade?");
                    int mesAlimento = input.nextInt();
                    System.out.println("Qual o ano da validade?");
                    int anoAlimento = input.nextInt();

                    System.out.println("Qual o nome do produto?");
                    String nomeAlimento = input.next();

                    System.out.println("Qual o preco do produto?");
                    double precoAlimento = input.nextDouble();

                    if(nomeAlimento.isEmpty()){
                        throw new CampoVazioException("Porfavor prencha todos os campos");
                    }
                    criarProdutoAlimento(diaAlimento, mesAlimento, anoAlimento, nomeAlimento, precoAlimento);
                    break;

                case 2: // produtos.Produto produtos.Evento
                    System.out.println("Qual o dia do evento?");
                    int diaEvento = input.nextInt();
                    System.out.println("Qual o mes do evento?");
                    int mesEvento = input.nextInt();
                    System.out.println("Qual o ano do evento?");
                    int anoEvento = input.nextInt();

                    System.out.println("Qual o nome do evento?");
                    String nomeEvento = input.nextLine();

                    System.out.println("Qual o preco do evento?");
                    double precoEvento = input.nextDouble();


                    System.out.println("Qual o premio do evento?");
                    String premio = input.nextLine();

                    criarProdutoEvento(diaEvento, mesEvento, anoEvento, nomeEvento, precoEvento, premio);
                    break;

                case 3: // produtos.Produto Não Alimento
                    System.out.println("Qual o nome do produto?");
                    String nomeNaoAlimento = input.next();

                    System.out.println("Qual o preco do produto?");
                    double precoNaoAlimento = input.nextDouble();

                    criarProdutoNaoAlimento(nomeNaoAlimento, precoNaoAlimento);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void adicionarProduto(Produto produto) {
        estoque.add(produto);
    }

    public void setEstoque(ArrayList<Produto> estoque) {
        this.estoque = estoque;
    }

    public Produto getProduto(short id) {
        try{
            if (id < estoque.size()) {
                for (Produto produto : estoque) {
                    if (produto.getId() == id) {
                        return produto;
                    }
                }
            }else {
                throw new IdInvalidoExcaption("Id nao encontrado");
            }
        }catch (Exception e){
            System.out.println("Erro ao procurar produto com o id forcenicdo: " + e.getMessage());
        }

        return null;
    }

    public void carregarProdutosDoArquivo(String caminhoArquivo) {
        ArrayList<Produto> produtosCarregados = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // Formato da data no arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("produtos.ProdutoAlimento")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    Date validade = dateFormat.parse(partes[0]);
                    short id = Short.parseShort(partes[1]);
                    String nome = partes[2].replace("'", "");
                    double preco = Double.parseDouble(partes[3]);
                    ProdutoAlimento produtoAlimento = new ProdutoAlimento(validade, id, nome, preco);
                    produtosCarregados.add(produtoAlimento);
                } else if (linha.startsWith("produtos.ProdutoEvento")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    Date diaDoEvento = dateFormat.parse(partes[0]);
                    String premio = partes[1].replace("'", "");
                    short id = Short.parseShort(partes[2]);
                    String nome = partes[3].replace("'", "");
                    double preco = Double.parseDouble(partes[4]);
                    ProdutoEvento produtoEvento = new ProdutoEvento(id, nome, preco, diaDoEvento, premio);
                    produtosCarregados.add(produtoEvento);
                } else if (linha.startsWith("produtos.ProdutosNaoAlimento")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    short id = Short.parseShort(partes[0]);
                    String nome = partes[1].replace("'", "");
                    double preco = Double.parseDouble(partes[2]);

                    ProdutosNaoAlimento produtoNaoAlimento = new ProdutosNaoAlimento(id, nome, preco);
                    produtosCarregados.add(produtoNaoAlimento);
                }
            }

            System.out.println("Produtos carregados com sucesso!");
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar o arquivo de produtos: " + e.getMessage());
        }

        this.estoque = produtosCarregados;
    }

    public void salvarEstoqueNoArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Produto produto : estoque) {
                bw.write(produto.toString());
                bw.newLine();
            }

            System.out.println("Produtos salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de produtos: " + e.getMessage());
        }
    }


}
