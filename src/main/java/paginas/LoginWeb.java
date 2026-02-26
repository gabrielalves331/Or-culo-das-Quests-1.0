package paginas;


import dao.CampanhaDAO;
import dao.UsuarioDAO;
import modelo.Usuario;
import util.Sessao;
import dao.DAOFactory;

public class LoginWeb extends BaseWebFrame {

    public LoginWeb() {
        setTitle("Login");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        carregarPagina("login.html");
        setVisible(true);
System.out.println("Abrindo próxima tela");

    }

    public void logar(String nome, String senha) {

    try {
        UsuarioDAO dao = DAOFactory.criarUsuarioDAO();

        Usuario usuario = dao.login(nome, senha);

        if (usuario != null) {

    Sessao.usuarioLogado = usuario;

    CampanhaDAO campanhaDAO = DAOFactory.criarCampanhaDAO();

    if (campanhaDAO.listarPorUsuario(usuario.getId()).isEmpty()) {
        new CampanhasVaziaWeb();
    } else {
        new CampanhasWeb();
    }

    dispose(); // UMA vez, aqui
    
    System.out.println("LOGIN OK, INDO PRA CAMPANHAS");

}

 else {
            System.out.println("Login inválido");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void irParaCadastro() {
        new CadastroWeb();
        dispose();
    }
    
     public void voltar() {
        new PaginaInicialWeb();
        dispose();
    }
     
     
}
