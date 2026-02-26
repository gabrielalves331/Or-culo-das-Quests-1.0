package paginas;

import dao.DAOFactory;
import dao.MissaoDAO;
import java.util.List;
import modelo.Missao;
import util.Sessao;

public class EdicaoescolhaWeb extends BaseWebFrame {

    public EdicaoescolhaWeb() {
        setTitle("Editar Missão");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarPagina("edicaoescolha.html");

        setVisible(true);
    }
    
    public void editarMissao(String id) {
    new EdicaoWeb(Integer.parseInt(id));
    dispose();
}

  public String buscarMissoes() {
    try {
        MissaoDAO dao = DAOFactory.criarMissaoDAO();

        List<Missao> lista = dao.listarPorCampanha(
            Sessao.campanhaAtual.getId()
        );

        return new com.google.gson.Gson().toJson(lista);

    } catch (Exception e) {
        e.printStackTrace();
        return "[]";
    }
}



    public void voltarHub() {
        new HubWeb();
        dispose();
    }
}
