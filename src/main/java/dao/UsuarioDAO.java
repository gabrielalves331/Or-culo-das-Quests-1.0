package dao;

import modelo.Usuario;

public interface UsuarioDAO {

    void cadastrar(Usuario u);

    Usuario login(String nome, String senha);

    boolean existeUsuario();  
}
