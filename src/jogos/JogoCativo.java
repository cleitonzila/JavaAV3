package jogos;

public class JogoCativo extends Jogo {
    private boolean jogando;

    public JogoCativo(String nome, int id, short tempo, byte numeroJogadores) {
        super(nome, id, tempo, numeroJogadores);
        jogando = false;
    }

    public boolean isJogando() {
        return jogando;
    }

    public void setJogando(boolean jogando) {
        this.jogando = jogando;
    }

    public void jogar(){
        jogando = true;
    }

    public void retornar(){
        jogando = false;
    }
}
