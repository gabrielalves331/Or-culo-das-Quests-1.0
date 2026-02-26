package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAOGenerico {
    
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        // Nome do arquivo do banco que ficará na pasta do seu jogo
        String URL_BANCO = "jdbc:sqlite:oraculo_das_quests.db";
        
        // Carrega o Driver do SQLite
        Class.forName("org.sqlite.JDBC");

        return DriverManager.getConnection(URL_BANCO);
    }
    
    public static void inicializarBanco() {
    try (Connection conn = getConexao(); Statement stmt = conn.createStatement()) {
        
        // 1. Tabela de usuários (com S no final!)
        stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "nome TEXT, senha TEXT);");
        
        // 2. Tabela de campanhas (com S e usuario_id)
        stmt.execute("CREATE TABLE IF NOT EXISTS campanhas (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "nome TEXT, usuario_id INTEGER, " +
                     "FOREIGN KEY(usuario_id) REFERENCES usuarios(id));");
                     
        // 3. Tabela de missoes (com S e todos os campos do seu DAO)
        stmt.execute("CREATE TABLE IF NOT EXISTS missoes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "titulo TEXT, descricao TEXT, dificuldade TEXT, " +
                     "recompensa TEXT, origem TEXT, status TEXT, " +
                     "campanha_id INTEGER, " +
                     "FOREIGN KEY(campanha_id) REFERENCES campanhas(id));");
        
        stmt.execute("""
CREATE TABLE IF NOT EXISTS modelo (
    codigo INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT
);
""");


        System.out.println("SQLite: Banco resetado e sincronizado!");
    } catch (Exception e) {
        System.err.println("Erro ao inicializar SQLite: " + e.getMessage());
    }
}
    public static int executarComando(String query, Object... params) throws SQLException, ClassNotFoundException {
        try (PreparedStatement sql = getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                sql.setObject(i + 1, params[i]);
            }
            
            int result = sql.executeUpdate();
            try (ResultSet rs = sql.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return result;
        }
    }
    
    public static ResultSet executarConsulta(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i + 1, params[i]);
        }
        return sql.executeQuery();
    }
}