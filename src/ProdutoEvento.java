import cliente.Usuario;

import java.util.Date;

public class ProdutoEvento extends Produto implements Evento {
    private Date diaDoEvento;
    private String premio;
    public ProdutoEvento(short id, String nome, double preco, Date diaDoEvento, String premio) {
        super(id, nome, preco);
        this.diaDoEvento = diaDoEvento;
        this.premio = premio;
    }

    @Override
    public void premiar(Usuario usuario) {
        System.out.printf("Parabens %s, você ganhou %s", usuario.getNome(), premio);
    }

    @Override
    public String toString() {
        return "ProdutoEvento{" +
                diaDoEvento + ", " +
                '\'' + premio + '\'' +
                ", " + super.toString();
    }
}
