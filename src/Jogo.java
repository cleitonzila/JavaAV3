public abstract class Jogo {
    protected String nome;
    protected int id;
    protected short tempo;
    protected byte numeroJogadores;

    public Jogo(String nome, int id, short tempo, byte numeroJogadores) {
        this.nome = nome;
        this.id = id;
        this.tempo = tempo;
        this.numeroJogadores = numeroJogadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getTempo() {
        return tempo;
    }

    public void setTempo(short tempo) {
        this.tempo = tempo;
    }

    public byte getNumeroJogadores() {
        return numeroJogadores;
    }

    public void setNumeroJogadores(byte numeroJogadores) {
        this.numeroJogadores = numeroJogadores;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                ", tempo=" + tempo +
                ", numeroJogadores=" + numeroJogadores +
                '}';
    }
}
