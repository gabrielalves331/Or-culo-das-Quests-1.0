package paginas;

import dao.DAOFactory;
import dao.MissaoDAO;
import java.util.List;
import javafx.application.Platform;
import modelo.Missao;
import util.Sessao;

public class RemocaoWeb extends BaseWebFrame {

    public RemocaoWeb() {
        setTitle("Remover Missão");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        carregarPagina("remocao.html");

        setVisible(true);
    }
    
    public void removerMissao(String id) {

    try {
        int idMissao = Integer.parseInt(id);

        MissaoDAO dao = DAOFactory.criarMissaoDAO();
        dao.deletar(idMissao);

        Platform.runLater(() -> atualizarLista());

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void removerMissoes(String ids) {

    try {
        MissaoDAO dao = DAOFactory.criarMissaoDAO();

        for (String id : ids.split(",")) {
            dao.deletar(Integer.parseInt(id));
        }

        Platform.runLater(() -> atualizarLista());

    } catch (Exception e) {
        e.printStackTrace();
    }
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


private void atualizarLista() {
    String json = buscarMissoes();
    engine.executeScript("carregarMissoes(" + json + ");");
    
    System.out.println("Misssões Caregadas ID: ");
}

    public void voltarHub() {
        new HubWeb();
        dispose();
    }
}
