public abstract class Usuario {
    protected String nome;
    protected String email;
    protected String endereco;
    protected String telefone;
    protected String cpf;
    protected int id;
    public Usuario(String nome, String email, String endereco, String telefone, String cpf, int id) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cpf = cpf;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return  nome + ", " +
                email + ", " +
                endereco + ", " +
                telefone + ", " +
                cpf + ", " +
                id +
                '}';
    }
}
