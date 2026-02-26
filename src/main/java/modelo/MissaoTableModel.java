package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MissaoTableModel extends AbstractTableModel {
    
    private final List<Missao> missoes;
    
    
    private final String[] colunas = {
    "ID", "Título", "Origem", "Dificuldade", "Recompensa", "Status"
};


   
    public MissaoTableModel(List<Missao> missoes) {
        this.missoes = missoes;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    
    @Override
    public int getRowCount() {
        return missoes.size();
    }
    
   
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
  
    @Override
public Object getValueAt(int rowIndex, int columnIndex) {

    Missao missao = missoes.get(rowIndex);

    return switch (columnIndex) {
        case 0 -> missao.getId();
        case 1 -> missao.getTitulo();
        case 2 -> missao.getOrigem();
        case 3 -> missao.getDificuldade();
        case 4 -> missao.getRecompensa();
        case 5 -> switch (missao.getStatus()) {
            case "DISPONIVEL" -> "Disponível";
            case "EM_ANDAMENTO" -> "Em andamento";
            case "CONCLUIDA" -> "Concluída";
            default -> missao.getStatus();
        };
        default -> null;
    };
}

   
    public Missao getMissao(int rowIndex) {
        return missoes.get(rowIndex);
    }
    
    
@Override
public Class<?> getColumnClass(int columnIndex) {
    return switch (columnIndex) {
        case 0 -> Integer.class;
        default -> String.class;
    };
}

}