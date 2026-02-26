package dao;

import java.util.List;
import modelo.Missao;

public interface MissaoDAO {
    void inserir(Missao m);
    void atualizar(Missao m);
    void deletar(int id);
    Missao buscarPorId(int id);

    List<Missao> listarPorCampanha(int campanhaId);

    void aceitarMissao(int id);
    void concluirMissao(int id);
}
