public class ProdutosNaoAlimento extends Produto {

    public ProdutosNaoAlimento(short id, String nome, double preco) {
        super(id, nome, preco);
    }

    @Override
    public String toString() {
        return "ProdutosNaoAlimento{" + super.toString();
    }
}
