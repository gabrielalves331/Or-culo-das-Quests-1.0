package paginas;

import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MissaoDAO;
import modelo.Missao;

import java.util.List;
import java.util.stream.Collectors;

public class RegistroWeb extends BaseWebFrame {

    private int campanhaId;

    public RegistroWeb(int campanhaId) {

        this.campanhaId = campanhaId;

        setTitle("Livro de Registros");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarPagina("registro.html");
        

        setVisible(true);
    }

    public String buscarConcluidas() {
        try {
            MissaoDAO dao = DAOFactory.criarMissaoDAO();

            List<Missao> todas = dao.listarPorCampanha(campanhaId);

            List<Missao> concluidas = todas.stream()
                    .filter(m -> "CONCLUIDA".equalsIgnoreCase(m.getStatus()))
                    .collect(Collectors.toList());

            Gson gson = new Gson();
            return gson.toJson(concluidas);

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