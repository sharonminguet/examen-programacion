/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package euromillones;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Euromillones extends JPanel {
    
    @Override public void paint(Graphics g){
    super.paint(g);
    Graphics2D misgraficos = (Graphics2D) g;
    int basegrafica = 360;
    misgraficos.drawLine(10, 10, 10, 360);            // Linea vertical
    misgraficos.drawLine(10, basegrafica, 360, basegrafica);   // Linea horizontal
    int[] barras = new int[]{100,300,200,200,100,200,50,200,25,50,100};
    
        String url = "jdbc:sqlite:C:\\Users\\Casa\\Desktop\\Euromillones.db";
        String user = "";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the SQLite database.");
            try (Statement statement = connection.createStatement()) {      
                String query = "SELECT COUNT(n1) as total, n1 AS numero GROUP BY n1 ORDER BY COUNT(n1) DESC LIMIT 1";     
                String[] coleccion = new String[]{"n1","n2","n3","n4","n5"};
                int contador = 0;
                for(String elemento : coleccion){
                    contador++;
                    ///////////////////////////////1
                    query = "SELECT COUNT("+elemento+") as total,"+elemento+" AS numero FROM Euromillones GROUP BY "+elemento+" ORDER BY COUNT("+elemento+") DESC LIMIT 1";
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        // Process the result set using a while loop
                        while (resultSet.next()) {
                            // Retrieve data from the result set
                            int total = resultSet.getInt("total");
                            int numero = resultSet.getInt("numero");
                            System.out.println("Mira, para el numero "+elemento+", debes jugar al numero "+numero+" porque ha salido un total de "+total+" veces");
                            misgraficos.fillRect(contador*30+20, basegrafica-total, 20, total);
                        }
                    }
                }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       
    } catch (SQLException ex) {
        Logger.getLogger(Euromillones.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
    
    public static void main(String[] args) throws SQLException {
        JFrame marco = new JFrame("grafica");
        Euromillones mimarco = new Euromillones();
        marco.add(mimarco);
        marco.setSize(400,400);
        marco.setVisible(true);
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
    
    

