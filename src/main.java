import java.util.Date;

public class main {
    public static void main(String[] args) {
        AdminComandas admin = new AdminComandas();
        AdminClientes adminClient = new AdminClientes();
        Date date = new Date();
        admin.carregarComandasDoArquivo("src/comandas.csv");
        adminClient.carregarClientesDoArquivo("src/clientes.csv");
        JogoAluguel jogo = new JogoAluguel("Fear of the unknow", 1, (short) 140, (byte)4, (byte)2, false);
        System.out.println(jogo.toString());
        Usuario u = adminClient.getListaClientes().get(1);
        Aluguel a = new Aluguel(jogo, date, u, 1, false);
        System.out.println(a.toString());
    }
}
