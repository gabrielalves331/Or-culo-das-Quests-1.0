package paginas;

import dao.DAOFactory;
import dao.UsuarioDAO;
import modelo.Usuario;

public class CadastroWeb extends BaseWebFrame {

    public CadastroWeb() {
        setTitle("Cadastro de Mestre");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        carregarPagina("cadastro.html");
        setVisible(true);
    }

    public void cadastrar(String nome, String senha) {

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setSenha(senha);

        UsuarioDAO dao = DAOFactory.criarUsuarioDAO();
        dao.cadastrar(u);

        new LoginWeb();
        dispose();
    }
    
     public void voltar() {
        new LoginWeb();
        dispose();
    }
}
