package dao;

import dao.MissaoDAO;
import dao.MissaoDAOJDBC;
import dao.ModeloDAO;
import dao.ModeloDAOJDBC;
import dao.UsuarioDAO;
import dao.UsuarioDAOJDBC;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    public static Connection getConnection() {
    try {
        // Agora ele pega a conexão do SQLite que configuramos no DAOGenerico
        return DAOGenerico.getConexao();
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Erro ao conectar ao SQLite através do Factory", e);
    }
}

    public static MissaoDAO criarMissaoDAO() {
        return new MissaoDAOJDBC(getConnection());
    }

    public static ModeloDAO criarModeloDAO() {
        return new ModeloDAOJDBC(getConnection());
    }

    public static UsuarioDAO criarUsuarioDAO() {
        return new UsuarioDAOJDBC(getConnection());
    }
    
    public static CampanhaDAO criarCampanhaDAO() {
    return new CampanhaDAOJDBC(getConnection());
}

}
