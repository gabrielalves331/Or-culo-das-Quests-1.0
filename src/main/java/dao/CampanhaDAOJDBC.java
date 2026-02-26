package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Campanha;

public class CampanhaDAOJDBC implements CampanhaDAO {

    private Connection conn;

    public CampanhaDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Campanha c) {
        String sql = """
            INSERT INTO campanhas (nome,usuario_id)
            VALUES (?, ?)
        """;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNome());
            st.setInt(2, c.getUsuarioId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Campanha> listarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM campanhas WHERE usuario_id = ?";
        List<Campanha> lista = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, usuarioId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Campanha c = new Campanha(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("usuario_id")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Campanha buscarPorId(int id) {
        String sql = "SELECT * FROM campanhas WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Campanha(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("usuario_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    
    @Override
public void deletar(int id) {

    String sql = "DELETE FROM campanhas WHERE id = ?";

    try (PreparedStatement st = conn.prepareStatement(sql)) {

        st.setInt(1, id);
        st.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
