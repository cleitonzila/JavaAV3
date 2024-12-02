import java.util.Date;

public class ProdutoAlimento extends Produto implements ProdutoPerecivel{
    private Date validade;

    public ProdutoAlimento(Date validade, short id, String nome, double preco) {
        super(id, nome, preco);
        this.validade = validade;
    }
    @Override
    public boolean jogarFora() {
        Date hoje = new Date();
        return validade.before(hoje);
    }

    @Override
    public String toString() {
        return "ProdutoAlimento{" +
                validade +
                ", " + super.toString();
    }
}
