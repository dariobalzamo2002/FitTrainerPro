package Utility;

import java.sql.*;

public final class ConnectionProvider 
{
     // URL del database, username e password
    private final String URL = "jdbc:mysql://localhost:3306/gym_fitness?serverTimezone=Europe/Rome";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String PATHJDBC = "com.mysql.cj.jdbc.Driver";
    
    private static ConnectionProvider instance = null;
    
    
   // Metodo per ottenere la connessione
    public Connection getConnection() {
        Connection conn = null;
        try {
            // Carica il driver JDBC
            Class.forName(PATHJDBC);
            // Crea la connessione
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Errore: Driver JDBC non trovato.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Errore: Connessione al database fallita.");
            e.printStackTrace();
        }
        return conn;
    }
    
    public static ConnectionProvider getInstance()
    {
        if(instance == null)
            instance = new ConnectionProvider();
        
        return instance;
    }
    
    private ConnectionProvider(){
        
    }
}
