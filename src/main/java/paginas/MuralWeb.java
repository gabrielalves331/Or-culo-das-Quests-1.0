package paginas;

import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MissaoDAO;
import java.util.List;
import java.util.stream.Collectors;
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

        List<Missao> todas = dao.listarPorCampanha(Sessao.campanhaAtual.getId());

        List<Missao> pendentes = todas.stream()
                .filter(m -> !"CONCLUIDA".equalsIgnoreCase(m.getStatus()))
                .collect(Collectors.toList());

        Gson gson = new Gson();
        return gson.toJson(pendentes);

    } catch (Exception e) {
        e.printStackTrace();
        return "[]";
    }
}


}
