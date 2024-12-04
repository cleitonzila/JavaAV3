package produtos;

public abstract class Produto {
    private short id;
    private String nome;
    private double preco;

    public Produto(short id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return  id + ", "+
                '\'' + nome + '\'' + ", "+
                preco +
                '}';
    }
}
