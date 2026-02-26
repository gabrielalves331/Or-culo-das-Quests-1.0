package paginas;

import dao.DAOFactory;
import dao.MissaoDAO;
import modelo.Missao;
import util.Sessao;

public class InsercaoWeb extends BaseWebFrame {

    public InsercaoWeb() {
        setTitle("Inserir Missão");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        carregarPagina("insercao.html");

        setVisible(true);
    }
    
    public void inserirMissao(String titulo, String descricao,
                          String dificuldade, String recompensa,
                          String origem) {

    try {
        Missao m = new Missao();
        m.setTitulo(titulo);
        m.setDescricao(descricao);
        m.setDificuldade(dificuldade);
        m.setRecompensa(recompensa);
         m.setOrigem(origem);
        m.setStatus("DISPONIVEL");
        m.setCampanhaId(Sessao.campanhaAtual.getId());


        MissaoDAO dao = DAOFactory.criarMissaoDAO();
        System.out.println("SALVANDO MISSÃO PRA CAMPANHA: " + Sessao.campanhaAtual.getId());

        dao.inserir(m);

        System.out.println("Missão inserida com sucesso!");

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("INSERINDO...");

    }
}


    public void voltarHub() {
    new HubWeb();
    dispose();
}

}
