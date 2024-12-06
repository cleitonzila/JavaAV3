package comandas;

import produtos.AdminEstoque;
import produtos.Produto;

import java.io.*;
import java.util.ArrayList;

public class AdminComandas {
    private ArrayList<Comanda> comandas;
    AdminEstoque adminEstoque = new AdminEstoque();

    public AdminComandas() {
        adminEstoque.carregarProdutosDoArquivo("src/dados/estoque.csv");
        comandas = new ArrayList<>();
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;

    }
    public void ativarComanda(byte id) {
        for (Comanda comanda : comandas) {
            if (comanda.getId() == id) {
                comanda.setAtiva(true);
            }
        }
    }

    public void desativarComanda(byte id) {
        for (Comanda comanda : comandas) {
            if (comanda.getId() == id) {
                comanda.setAtiva(false);
            }
        }
    }

    public void adicionarProduto(byte id, short produtoId) {
        for (Comanda comanda : comandas) {
            if (comanda.getId() == id) {
                comanda.adicionarProduto(adminEstoque.getProduto(produtoId));
            }
        }

    }
    public ArrayList<Produto> getProdutos(byte id) {
        ArrayList<Produto> tmp = new ArrayList<>();
        for (Comanda comanda : comandas) {
            if (comanda.getId() == id) {
                tmp.addAll(comanda.getProdutos());
            }

        }
        return tmp;
    }

    public void setComandas(ArrayList<Comanda> comandas) {
        this.comandas = comandas;
    }

    public void addComanda(Comanda comanda) {
        comandas.add(comanda);
    }

    public void removeComanda(Comanda comanda) {
        comandas.remove(comanda);
    }

    public void carregarComandasDoArquivo(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                byte id = Byte.parseByte(dados[0]);
                boolean ativa = Boolean.parseBoolean(dados[1]);
                Comanda comanda = new Comanda(id);
                comanda.setAtiva(ativa);

                if (!dados[2].isEmpty()) {
                    String produtos = dados[2].replace("(", "").replace(")", "");
                    if(!produtos.isEmpty()){
                        String[] produtosId = produtos.split(",");
                        for (String produtoId : produtosId) {
                            comanda.adicionarProduto(adminEstoque.getProduto(Short.parseShort(produtoId)));
                        }
                    }
                }
                comandas.add(comanda);

            }
            System.out.println("Comandas carregadas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de comandas: " + e.getMessage());
        }
    }

    public void salvarComandasNoArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("id;ativa;produtos");
            bw.newLine();

            for (Comanda comanda : comandas) {
                StringBuilder linha = new StringBuilder();
                linha.append(comanda.getId()).append(";");
                linha.append(comanda.isAtiva()).append(";");
                linha.append("(");
                ArrayList<Produto> produtos = comanda.getProdutos();
                if (!produtos.isEmpty()) {
                    for (Produto produto : produtos) {
                        linha.append(produto.getId()).append(";");
                    }
                }
                linha.append(")");

                bw.write(linha.toString());
                bw.newLine();
            }

            System.out.println("Comandas salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de comandas: " + e.getMessage());
        }
    }

    public ArrayList<Comanda> comandasAtivas() {
        ArrayList<Comanda> tmp = new ArrayList<>();
        for (Comanda comanda : comandas) {
            if (comanda.isAtiva()) {
                tmp.add(comanda);
            }
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "AdminComandas{" +
                "comandas=" + comandas +
                ", adminEstoque=" + adminEstoque +
                '}';
    }
}
