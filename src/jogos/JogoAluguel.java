package jogos;

public class JogoAluguel extends Jogo {
    private byte tipo;
    private boolean alugado;
    public JogoAluguel(String nome, int id, short tempo, byte numeroJogadores, byte tipo, boolean alugado) {
        super(nome, id, tempo, numeroJogadores);
        this.tipo = tipo;
        this.alugado = alugado;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public void alugar(){
        alugado = true;
    }

    public void devolver(){
        alugado = false;
    }

    @Override
    public String toString() {
        return "JogoAluguel{" +
                "tipo=" + tipo +
                ", alugado=" + alugado +
                "} " + super.toString();
    }
}
