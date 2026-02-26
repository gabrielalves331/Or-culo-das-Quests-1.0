package dao;

import java.sql.*;
import modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDAO {

    private Connection conn;

    public UsuarioDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void cadastrar(Usuario u) {

        String sql = """
            INSERT INTO usuarios (nome, senha)
            VALUES (?, ?)
        """;

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, u.getNome());
            st.setString(2, u.getSenha());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

@Override    
public Usuario login(String nome, String senha) {
    // CORRIGIDO: de 'usuario' para 'usuarios'
    String sql = """
        SELECT * FROM usuarios
        WHERE nome = ? AND senha = ?
    """;

    try (PreparedStatement st = conn.prepareStatement(sql)) {

        st.setString(1, nome);
        st.setString(2, senha);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("senha")
            );
        }

        return null;

    } catch (SQLException e) {
        throw new RuntimeException("Erro no login: " + e.getMessage());
    }
}


    @Override
    public boolean existeUsuario() {

        String sql = "SELECT COUNT(*) FROM usuarios";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar usuários");
        }
    }
}