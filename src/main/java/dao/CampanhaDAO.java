package dao;

import java.util.List;
import modelo.Campanha;

public interface CampanhaDAO {

    void inserir(Campanha c);

    List<Campanha> listarPorUsuario(int usuarioId);

    Campanha buscarPorId(int id);
    
    void deletar(int id);

}
