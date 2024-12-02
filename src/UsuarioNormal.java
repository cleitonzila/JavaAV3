import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UsuarioNormal extends Usuario{
    private double divida;

    public UsuarioNormal(String nome, String email, String endereco, String telefone, String cpf, int id, double divida) {
        super(nome, email, endereco, telefone, cpf, id);
        this.divida = divida;
    }

    public double getDivida() {
        return divida;
    }

    public void setDivida(double divida) {
        this.divida = divida;
    }

    @Override
    public String toString() {
        return "UsuarioNormal{" +
                divida +
                ", " + super.toString();
    }
}
