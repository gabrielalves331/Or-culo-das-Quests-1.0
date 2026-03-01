package util;

import dao.GrafoDAO;
import java.io.FileWriter;

public class GeradorGrafoJson {
     public static void main(String[] args) {

        try {
            GrafoDAO dao = new GrafoDAO();
            String json = dao.gerarJsonGrafo();

            FileWriter writer = new FileWriter("grafo.json");
            writer.write(json);
            writer.close();

            System.out.println("Arquivo grafo.json gerado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
