import java.util.Date;

public class UsuarioPlano extends Usuario implements Planos{
    private byte tipoPlano;
    private Date validadePlano;
    public UsuarioPlano(String nome, String email, String endereco, String telefone, String cpf, int id, byte tipoPlano, Date validadePlano) {
        super(nome, email, endereco, telefone, cpf, id);
        this.tipoPlano = tipoPlano;
        this.validadePlano = validadePlano;
    }

    @Override
    public void pagarPlano() {
        System.out.printf("A valor do seu plano é R$%.2f", tipoPlano*(400)*(1-0.1*tipoPlano));
    }

    public byte getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(byte tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    @Override
    public String toString() {
        return "UsuarioPlano{" +
                tipoPlano +
                ", " +
                validadePlano +
                ", " + super.toString();
    }
}
