package comandas;

import produtos.Produto;

import java.util.ArrayList;

public class Comanda {
    private ArrayList<Produto> produtos;
    private byte id;
    private boolean ativa;

    public Comanda(byte id) {
        this.id = id;
        this.produtos = new ArrayList<Produto>();
        this.ativa = false;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public double getValorTotal() {
        double total = 0;
        for (Produto produto : this.produtos){
            total += produto.getPreco();
        }
        return total;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }


    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void pagarComanda(){
        this.produtos.clear();
        this.ativa = false;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "produtos=" + produtos +
                ", id=" + id +
                ", ativa=" + ativa +
                '}';
    }
}
