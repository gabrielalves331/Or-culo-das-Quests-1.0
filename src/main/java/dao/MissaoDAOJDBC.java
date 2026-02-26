package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Missao;

public class MissaoDAOJDBC implements MissaoDAO {

    private Connection conn;

    public MissaoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
public void inserir(Missao m) {
    String sql = """
        INSERT INTO missoes
        (titulo, descricao, dificuldade, recompensa, origem, status, campanha_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, m.getTitulo());
        st.setString(2, m.getDescricao());
        st.setString(3, m.getDificuldade());
        st.setString(4, m.getRecompensa());
        st.setString(5, m.getOrigem());
        st.setString(6, m.getStatus());
        st.setInt(7, m.getCampanhaId()); 

        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao inserir missão: " + e.getMessage());
    }
}


    @Override
    public void atualizar(Missao m) {
    String sql = """
        UPDATE missoes
        SET titulo = ?,
            descricao = ?,
            dificuldade = ?,
            recompensa = ?,
            origem = ?,
            status = ?
        WHERE id = ?
    """;

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, m.getTitulo());
        st.setString(2, m.getDescricao());
        st.setString(3, m.getDificuldade());
        st.setString(4, m.getRecompensa());
        st.setString(5, m.getOrigem());
        st.setString(6, m.getStatus());
        st.setInt(7, m.getId());

        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao atualizar missão: " + e.getMessage());
    }
}

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM missoes WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar missão: " + e.getMessage());
        }
    }

    @Override
   public Missao buscarPorId(int id) {
    String sql = "SELECT * FROM missoes WHERE id=?";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            Missao m = new Missao();
            m.setId(rs.getInt("id"));
            m.setTitulo(rs.getString("titulo"));
            m.setDescricao(rs.getString("descricao"));
            m.setDificuldade(rs.getString("dificuldade"));
            m.setRecompensa(rs.getString("recompensa"));
            m.setOrigem(rs.getString("origem"));
            m.setStatus(rs.getString("status"));

            return m;
        }
        return null;
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar missão: " + e.getMessage());
    }
}

@Override
public List<Missao> listarPorCampanha(int campanhaId) {

    String sql = "SELECT * FROM missoes WHERE campanha_id = ?";
    List<Missao> lista = new ArrayList<>();

    try (PreparedStatement st = conn.prepareStatement(sql)) {

        st.setInt(1, campanhaId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Missao m = new Missao();
            m.setId(rs.getInt("id"));
            m.setTitulo(rs.getString("titulo"));
            m.setDescricao(rs.getString("descricao"));
            m.setDificuldade(rs.getString("dificuldade"));
            m.setRecompensa(rs.getString("recompensa"));
            m.setOrigem(rs.getString("origem"));
            m.setStatus(rs.getString("status"));
            m.setCampanhaId(rs.getInt("campanha_id"));

            lista.add(m);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar missões: " + e.getMessage());
    }

    return lista;
}


@Override
public void aceitarMissao(int id) {
    String sql = "UPDATE missoes SET status = 'EM_ANDAMENTO' WHERE id = ?";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, id);
        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao aceitar missão: " + e.getMessage());
 }
}
@Override
public void concluirMissao(int id) {
    String sql = "UPDATE missoes SET status = 'CONCLUIDA' WHERE id = ?";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, id);
        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao concluir missão: " + e.getMessage());
  }
 }
}
