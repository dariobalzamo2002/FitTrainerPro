package Service;

import Model.Esercizio;
import Model.SchedaAllenamento;
import Repository.SchedaRepository;
import Utility.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchedaService implements SchedaRepository
{
    private static SchedaService instance = null;
    private ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    
    public static SchedaService getInstance(){
        if(instance == null)
            instance = new SchedaService();
        return instance;
    }
    
    private SchedaService(){
        
    }
    

    @Override
    public String insertSchedaAllenamento(SchedaAllenamento schedaAllenamento) 
    {
        String response ="Operazione di inserimento fallita!";
        
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        
        String sql = "Insert into schede_allenamento (id_cliente, data_emissione) values(?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, schedaAllenamento.getCliente().getId());
            ps.setDate(2, new java.sql.Date(schedaAllenamento.getDataEmissione().getTime()));
            ps.execute();
            response = "Inserimento avvenuto con successo!";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return response;
    }

    @Override
    public String insertEsercizioo(Esercizio esercizio) 
    {
        
        String response ="Operazione di inserimento fallita!";
        
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        
        String sql = "Insert into esercizi (id_scheda_allenamento, nome_esercizio, rep_ex1, altro, serie, recupero, sessione) values(?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, esercizio.getSchedaAllenamento().getId());
            ps.setString(2, esercizio.getNomeEsercizio());
            ps.setString(3, esercizio.getRepEx1());
            if(esercizio.getAltro() != null)
                ps.setString(4, esercizio.getAltro());
            else
                ps.setString(4, null);
            ps.setInt(5, esercizio.getSerie());
            ps.setInt(6, esercizio.getRecupero());
            ps.setString(7, esercizio.getSessione());
            ps.execute();
            response = "Inserimento avvenuto con successo!";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return response;
    }

    @Override
    public Long findMaxId(Long id_cliente) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = 0L; // Valore predefinito

        
        String sql ="SELECT MAX(id) as 'id' FROM schede_allenamento where id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_cliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }
    
    
    
}