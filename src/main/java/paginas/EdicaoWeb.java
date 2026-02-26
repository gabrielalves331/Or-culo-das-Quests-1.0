package paginas;

import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MissaoDAO;
import modelo.Missao;

public class EdicaoWeb extends BaseWebFrame {
    
    public EdicaoWeb(int id) {
    this.idMissao = id;

    setTitle("Editar Missão");
    setExtendedState(MAXIMIZED_BOTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    carregarPagina("edicao.html");

    setVisible(true);
    
    System.out.println("ID recebido: " + idMissao);
}

    private int idMissao;

  public String buscarMissao() {
    try {
        MissaoDAO dao = DAOFactory.criarMissaoDAO();
        Missao m = dao.buscarPorId(idMissao);   

        Gson gson = new Gson();
        return gson.toJson(m);

    } catch (Exception e) {
        e.printStackTrace();
        return "{}";
    }
}
  
  public void salvarMissao(String titulo, String descricao,
                         String dificuldade, String recompensa,
                         String origem) {
    try {
        MissaoDAO dao = DAOFactory.criarMissaoDAO();

        Missao m = dao.buscarPorId(idMissao);

        m.setTitulo(titulo);
        m.setDescricao(descricao);
        m.setDificuldade(dificuldade);
        m.setRecompensa(recompensa);
        m.setOrigem(origem);   // ← FALTAVA ISSO

        dao.atualizar(m);

        new EdicaoescolhaWeb();
        dispose();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


  
    public void voltarEdicaoescolhaWeb() {
        new EdicaoescolhaWeb();
        dispose();
    }
}
