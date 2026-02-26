/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import modelo.Modelo;

/**
 *
 * @author gabri
 */
public class ModeloDAOJDBC implements ModeloDAO{

    public ModeloDAOJDBC(Connection connection) {
    }

     Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
   @Override
public int inserir(Modelo modelo) {
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder
            .append("INSERT INTO modelo(descricao) ")
            .append("VALUES (?)");
    
    String insert = sqlBuilder.toString();
    int linha = 0;
    int codigoGerado = 0; // Inicializa o código gerado
    
    try {  
        // 1. Executa e recebe o ID gerado (pois DAOGenerico foi atualizado para retornar o ID)
        codigoGerado = DAOGenerico.executarComando(insert, modelo.getDescricao());
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        fecharConexao();
    }
    
    // 2. Agora, fora do bloco DB, verificamos se o código foi gerado
    if (codigoGerado > 0) {
        modelo.setCodigo(codigoGerado); // <--- ATUALIZA O OBJETO COM O ID REAL DO BANCO
        linha = 1; // Retorna 1 para indicar que a operação foi bem-sucedida
    }
    
    // Se codigoGerado for 0 (falha), linha continua 0.
    return linha;
}

    @Override
    public int editar(Modelo modelo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE modelo SET ")
                .append("descricao = ?")
                .append("WHERE codigo = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            
            linha = DAOGenerico.executarComando(update, modelo.getDescricao(), modelo.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagar(int codigo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException{
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM modelo ")
                .append("WHERE codigo = ?");
        
        String delete = sqlBuilder.toString();
        int linha = 0;
                 
         linha = DAOGenerico.executarComando(delete, codigo);
    

        return linha;
    }

    @Override
    public List<Modelo> listar() {
        String select = "SELECT * FROM modelo";

        List<Modelo> modelos = new ArrayList<Modelo>();

        try {        
            rset = DAOGenerico.executarConsulta(select);


            while (rset.next()) {

                Modelo modelo = new Modelo();
                modelo.setCodigo(rset.getInt("codigo"));
                modelo.setDescricao(rset.getString("descricao"));

                modelos.add(modelo);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return modelos;
    }

    @Override
    public Modelo listar(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void fecharConexao() {
        try {
            if (rset != null) {
                rset.close();
            }
            if (sql != null) {
                sql.close();
            }

            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
