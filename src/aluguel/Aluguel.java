package aluguel;

import cliente.Usuario;
import jogos.JogoAluguel;

import java.util.Date;

public class Aluguel {
    private JogoAluguel jogoAluguel;
    private Date dataAluguel;
    private Usuario usuarioAluguel;
    private int id;
    private boolean concluido;

    public Aluguel(JogoAluguel jogoAluguel, Date date, Usuario usuarioAluguel, int id, boolean concluido) {
        this.jogoAluguel = jogoAluguel;
        this.usuarioAluguel = usuarioAluguel;
        this.dataAluguel = date;
        this.concluido = concluido;
        this.id = id;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuarioAluguel() {
        return usuarioAluguel;
    }

    public void setUsuarioAluguel(Usuario usuarioAluguel) {
        this.usuarioAluguel = usuarioAluguel;
    }

    public JogoAluguel getJogoAluguel() {
        return jogoAluguel;
    }

    public void setJogoAluguel(JogoAluguel jogoAluguel) {
        this.jogoAluguel = jogoAluguel;
    }

    public Date getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    @Override
    public String toString() {
        return "aluguel.Aluguel{" +
                jogoAluguel.getId() +
                ", " + dataAluguel +
                ", " + usuarioAluguel.getId() +
                ", " + id +
                ", " + concluido +
                '}';
    }
}
