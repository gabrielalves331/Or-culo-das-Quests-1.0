package paginas;

import dao.CampanhaDAO;
import dao.DAOFactory;
import javafx.application.Platform;
import modelo.Campanha;
import util.Sessao;

public class CampanhasWeb extends BaseWebFrame {
    
    public CampanhasWeb() {
        setTitle("Nenhuma Campanha");
        setExtendedState(MAXIMIZED_BOTH);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
        carregarPagina("campanha_escolha.html");
        setVisible(true);      
    }
    
    public String buscarCampanhas() {

    CampanhaDAO dao = DAOFactory.criarCampanhaDAO();

    var lista = dao.listarPorUsuario(
        Sessao.usuarioLogado.getId()
    );

    String json = new com.google.gson.Gson().toJson(lista);

    return json;
}


    public void selecionarCampanha(String id) {

    CampanhaDAO dao = DAOFactory.criarCampanhaDAO();
    Campanha c = dao.buscarPorId(Integer.parseInt(id));

    Sessao.campanhaAtual = c;

    new HubWeb();
    dispose();
}
    
    public void criarNova() {
        new CriarCampanhaWeb();
        dispose();
        
    }
    
    private void atualizarLista() {
    String json = buscarCampanhas();
    engine.executeScript("carregarCampanhas(" + json + ");");
}


    public void excluirCampanha(String id) {
    CampanhaDAO dao = DAOFactory.criarCampanhaDAO();
    dao.deletar(Integer.parseInt(id));

    Platform.runLater(() -> atualizarLista());
}

    
     public void voltarlogin() {
        new LoginWeb();
        dispose();
    }
    
}
