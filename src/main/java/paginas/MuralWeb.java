package paginas;

import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MissaoDAO;
import java.util.List;
import javax.swing.SwingUtilities;
import modelo.Missao;
import util.Sessao;

public class MuralWeb extends BaseWebFrame {

    public MuralWeb() {
    setTitle("Mural de Missões");
    setExtendedState(MAXIMIZED_BOTH);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    carregarPagina("mural.html");

    setVisible(true);

    }

    public void voltarHub() {
        SwingUtilities.invokeLater(() -> {
            new HubWeb();
            dispose();
        });
    }

   public String buscarMissoes() {
    try {

        System.out.println("Campanha atual: " + Sessao.campanhaAtual);
        System.out.println("ID: " + Sessao.campanhaAtual.getId());

        MissaoDAO dao = DAOFactory.criarMissaoDAO();
        List<Missao> lista = dao.listarPorCampanha(
            Sessao.campanhaAtual.getId()
        );

        Gson gson = new Gson();
        return gson.toJson(lista);

    } catch (Exception e) {
        e.printStackTrace();
        return "[]";
    }
}


}
