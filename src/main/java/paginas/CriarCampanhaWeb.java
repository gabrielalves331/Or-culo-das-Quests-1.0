package paginas;

import dao.CampanhaDAO;
import dao.DAOFactory;
import modelo.Campanha;
import util.Sessao;

public class CriarCampanhaWeb extends BaseWebFrame {

    public CriarCampanhaWeb() {
        setTitle("Criar Campanha");
        setExtendedState(MAXIMIZED_BOTH);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
        carregarPagina("criar_campanha.html");
        setVisible(true);
    }

    public void criar(String nome) {

        Campanha c = new Campanha();
        c.setNome(nome);
        c.setUsuarioId(Sessao.usuarioLogado.getId());

        CampanhaDAO dao = DAOFactory.criarCampanhaDAO();
        dao.inserir(c);

        new CampanhasWeb();
        dispose();
    }

    public void voltar() {
        new CampanhasWeb();
        dispose();
    }
}

