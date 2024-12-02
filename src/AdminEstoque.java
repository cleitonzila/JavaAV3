import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminEstoque {
    private ArrayList<Produto> estoque = new ArrayList<>();

    public ArrayList<Produto> getEstoque() {
        return estoque;
    }

    public void adicionarProduto(Produto produto) {
        estoque.add(produto);
    }

    public void setEstoque(ArrayList<Produto> estoque) {
        this.estoque = estoque;
    }

    public Produto getProduto(short id) {
        for (Produto produto : estoque) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public void carregarProdutosDoArquivo(String caminhoArquivo) {
        ArrayList<Produto> produtosCarregados = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // Formato da data no arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("ProdutoAlimento")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    Date validade = dateFormat.parse(partes[0]);
                    short id = Short.parseShort(partes[1]);
                    String nome = partes[2].replace("'", "");
                    double preco = Double.parseDouble(partes[3]);
                    ProdutoAlimento produtoAlimento = new ProdutoAlimento(validade, id, nome, preco);
                    produtosCarregados.add(produtoAlimento);
                } else if (linha.startsWith("ProdutoEvento")) {
                    String conteudo = linha.substring(linha.indexOf("{") + 1, linha.indexOf("}"));
                    String[] partes = conteudo.split(", ");

                    Date diaDoEvento = dateFormat.parse(partes[0]);
                    String premio = partes[1].replace("'", "");
                    short id = Short.parseShort(partes[2]);
                    String nome = partes[3].replace("'", "");
                    double preco = Double.parseDouble(partes[4]);
                    ProdutoEvento produtoEvento = new ProdutoEvento(id, nome, preco, diaDoEvento, premio);
                    produtosCarregados.add(produtoEvento);
                } else if (linha.startsWith("ProdutosNaoAlimento")) {
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
