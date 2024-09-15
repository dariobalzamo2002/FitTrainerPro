package Service;

import Model.Cliente;
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
        
        String sql = "Insert into schede_allenamento (id_cliente, data_emissione, durata, frequenzaSettimanale, tipoAttività) values(?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, schedaAllenamento.getCliente().getId());
            ps.setDate(2, new java.sql.Date(schedaAllenamento.getDataEmissione().getTime()));
            ps.setString(3, schedaAllenamento.getDurata());
            ps.setString(4, schedaAllenamento.getFrequenzaSettimanale());
            ps.setString(5, schedaAllenamento.getTipoAttivita());
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
            ps.setString(6, esercizio.getRecupero());
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

    
    
    
    public Long findIdAllenamentoByIdCliente(Long id_cliente) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = 0L; // Valore predefinito

        
        String sql ="SELECT id FROM schede_allenamento where id_cliente = ?";
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
    
    @Override
    public String deleteSchedaAllenamento(Long id_cliente) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Operazione di eliminazione fallita!";

        String sql = "Delete from schede_allenamento where id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_cliente);
            ps.execute();       
            
            response = "Eliminazione avvenuta con successo!";
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
    public void deleteEsercizioByIdAllenamento(Long id_scheda_allenamento){
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;

        String sql = "Delete from esercizi where id_scheda_allenamento = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_scheda_allenamento);
            ps.execute();       
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
    }
    
    @Override
    public SchedaAllenamento getSchedaAllenamento(Long id_cliente, Cliente cliente) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SchedaAllenamento schedaAllenamento = null;
        
        String sql ="SELECT * FROM schede_allenamento where id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_cliente);
            rs = ps.executeQuery();
            if (rs.next()) 
            {                
                schedaAllenamento = new SchedaAllenamento();
                schedaAllenamento.setId(rs.getLong("id"));
                schedaAllenamento.setCliente(cliente);
                schedaAllenamento.setDataEmissione(rs.getDate("data_emissione"));
                schedaAllenamento.setDurata(rs.getString("durata"));
                schedaAllenamento.setFrequenzaSettimanale(rs.getString("frequenzaSettimanale"));
                schedaAllenamento.setTipoAttivita(rs.getString("tipoAttività"));
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
        
        return schedaAllenamento;
    }
    
    @Override
    public String updateSchedaAllenamento(SchedaAllenamento schedaAllenamento) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Operazione di modifica fallita!";
        
        String sql ="UPDATE schede_allenamento SET data_emissione = ?, durata = ?, frequenzaSettimanale = ?, tipoAttività = ? WHERE id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(schedaAllenamento.getDataEmissione().getTime()));
            ps.setString(2, schedaAllenamento.getDurata());
            ps.setString(3, schedaAllenamento.getFrequenzaSettimanale());
            ps.setString(4, schedaAllenamento.getTipoAttivita());
            ps.setLong(5, schedaAllenamento.getCliente().getId());
            ps.executeUpdate();
            response = "Modifica avvenuta con successo!";
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
    
}