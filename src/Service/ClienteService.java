package Service;

import Model.Anamnesi;
import Model.Cliente;
import Model.DTO.ProgressiClienteDTO;
import Model.Esercizio;
import Model.SchedaAntropometrica;
import Repository.ClienteRepository;
import Utility.ConnectionProvider;
import enums.Genere;
import enums.MetodoDiPagamento;
import enums.StileDiVita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ClienteService implements ClienteRepository
{
    private static ClienteService instance = null;
    private ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    private PaymentService paymentService = PaymentService.getInstance();
    private SchedaService schedaService = SchedaService.getInstance();
    
    
    public static ClienteService getInstance()
    {
        if(instance == null)
            instance = new ClienteService();
       
        return instance;
    }
    
    private ClienteService(){
        
    }

    // SERVIZI CLIENTE
    
    @Override
    public String insertCliente(Cliente cliente) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Operazione di inserimento fallita!";
        
        String sql ="Insert into clients values(?,?,?,?,?,?,?,?);";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, cliente.getId());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setLong(4, cliente.getCellulare());
            ps.setString(5, cliente.getSesso().toString());
            // Conversione java.util.date to java.sql.date
            ps.setDate(6, new java.sql.Date(cliente.getDataDiNascita().getTime()));
            ps.setString(7, cliente.getLuogoDiNascita());
            ps.setString(8, cliente.getMetodoDiPagamento().toString());
            ps.executeUpdate();
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
    public String deleteCliente(Long id) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        Long id_schedaAllenamento = 0L;
        String response = "Operazione di eliminazione fallita!";
        
        
        String sql1 = "Delete from anamnesi where id_cliente = ?";
        String sql2 = "Delete from schede_antropometriche where id_cliente = ?";
        String sql3 ="Delete from clients where id=?";
        
        
        paymentService.deleteByClientID(id);
        id_schedaAllenamento = schedaService.findIdByIdCliente(id);
        schedaService.deleteEsercizioByIdAllenamento(id_schedaAllenamento);
        schedaService.deleteSchedaAllenamento(id);
        try {
            ps = conn.prepareStatement(sql1);
            ps.setLong(1, id);
            ps.execute();
            
            ps = conn.prepareStatement(sql2);
            ps.setLong(1, id);
            ps.execute();
            
            ps = conn.prepareStatement(sql3);
            ps.setLong(1, id);
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
    public String updateCliente(Cliente cliente) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Operazione di modifica fallita!";
        
        String sql ="Update clients set nome=?, email=?, cellulare=?, genere=?, data_di_nascita=?, luogo_di_nascita=?, metodo_di_pagamento=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setLong(3, cliente.getCellulare());
            ps.setString(4, cliente.getSesso().toString());
            ps.setDate(5, new java.sql.Date(cliente.getDataDiNascita().getTime()));
            ps.setString(6, cliente.getLuogoDiNascita());
            ps.setString(7, cliente.getMetodoDiPagamento().toString());
            ps.setLong(8, cliente.getId());
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

    @Override
    public Cliente findById(Long id) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;
        
        String sql ="SELECT * FROM clients where id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) 
            {                
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setSesso(Genere.valueOf(Genere.class, rs.getString("genere")));
                cliente.setCellulare(rs.getLong("cellulare"));
                cliente.setDataDiNascita(rs.getDate("data_di_nascita"));
                cliente.setLuogoDiNascita(rs.getString("luogo_di_nascita"));
                cliente.setMetodoDiPagamento(MetodoDiPagamento.valueOf(MetodoDiPagamento.class, rs.getString("metodo_di_pagamento")));
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
        
        return cliente;
    }

    @Override
    public List<Cliente> findByAll() 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cliente> listaClienti = new ArrayList<>();
        
        String sql ="Select * from clients";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) 
            {                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setSesso(Genere.valueOf(Genere.class, rs.getString("genere")));
                cliente.setCellulare(rs.getLong("cellulare"));
                cliente.setDataDiNascita(rs.getDate("data_di_nascita"));
                cliente.setLuogoDiNascita(rs.getString("luogo_di_nascita"));
                cliente.setMetodoDiPagamento(MetodoDiPagamento.valueOf(MetodoDiPagamento.class, rs.getString("metodo_di_pagamento")));
                
                listaClienti.add(cliente);
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
        
        return listaClienti;
    }

    @Override
    public Long findMaxId() {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = 0L; // Valore predefinito

        
        String sql ="SELECT MAX(id) as 'id' FROM clients";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id") + 1;
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
    public Cliente findByName(String nome) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;
        
        String sql ="SELECT * FROM clients where nome = ?";       
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (rs.next()) 
            {                
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setSesso(Genere.valueOf(Genere.class, rs.getString("genere")));
                cliente.setCellulare(rs.getLong("cellulare"));
                cliente.setDataDiNascita(rs.getDate("data_di_nascita"));
                cliente.setLuogoDiNascita(rs.getString("luogo_di_nascita"));
                cliente.setMetodoDiPagamento(MetodoDiPagamento.valueOf(MetodoDiPagamento.class, rs.getString("metodo_di_pagamento")));
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
        
        return cliente;
    }

    @Override
    public void createAnamnesiCliente(Anamnesi anamnesi) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        
        String sql ="Insert into anamnesi (id_cliente, stile_di_vita, frequenza_allenamenti, is_smoker, patologie, disformismo) values(?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, anamnesi.getCliente().getId());
            ps.setString(2, anamnesi.getStileDiVita().toString());
            ps.setInt(3, anamnesi.getFrequenzaAllenamenti());
            ps.setBoolean(4, anamnesi.isSmoker());
            ps.setString(5, anamnesi.getPatologie());
            ps.setString(6, anamnesi.getDismorfismo());
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
    public String insertVisitaAntropometrica(SchedaAntropometrica schedaAntropometrica) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Errore durante l'operazione di salvataggio!";
        
        String sql ="Insert into schede_antropometriche (id_cliente, peso, altezza, imc, emilato, spalla, torace, vita, fianchi, braccio, polso, coscia, polpacci, collo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, schedaAntropometrica.getCliente().getId());
            ps.setFloat(2, schedaAntropometrica.getPeso());
            ps.setDouble(3, schedaAntropometrica.getAltezza());
            ps.setDouble(4, schedaAntropometrica.getImc());
            ps.setString(5, schedaAntropometrica.getEmilato().toString());
            ps.setFloat(6, schedaAntropometrica.getSpalla());
            ps.setFloat(7, schedaAntropometrica.getTorace());
            ps.setFloat(8, schedaAntropometrica.getVita());
            ps.setFloat(9, schedaAntropometrica.getFianchi());
            ps.setFloat(10, schedaAntropometrica.getBraccio());
            ps.setFloat(11, schedaAntropometrica.getPolso());
            ps.setFloat(12, schedaAntropometrica.getCoscia());
            ps.setFloat(13, schedaAntropometrica.getPolpacci());
            ps.setFloat(14, schedaAntropometrica.getCollo());
            ps.execute();
            response = "Salvataggio scheda antropometrica nel sistema!";
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
    public String deleteVisitaAntropometrica(Long id_cliente) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        String response = "Operazione di eliminazione fallita!";
        
        String sql ="Delete from schede_antropometriche where id_cliente=?";
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
    public ProgressiClienteDTO findByNameProgressiCliente(String name) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProgressiClienteDTO progressiClienteDTO = null;
        List<Esercizio> esercizi = new ArrayList<>();

        String sql = "SELECT * FROM progressi_cliente_view WHERE nome like ?";       
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,"%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                if (progressiClienteDTO == null) {
                    // Inizializzo al primo record l'oggetto DTO
                    progressiClienteDTO = new ProgressiClienteDTO();
                    progressiClienteDTO.setId(rs.getLong("id"));
                    progressiClienteDTO.setNome(rs.getString("nome"));
                    progressiClienteDTO.setEmail(rs.getString("email"));
                    progressiClienteDTO.setCellulare(rs.getString("cellulare"));
                    progressiClienteDTO.setDataDiNascita(rs.getDate("data_di_nascita"));
                    progressiClienteDTO.setLuogoDiNascita(rs.getString("luogo_di_nascita"));
                    progressiClienteDTO.setStileDiVita(StileDiVita.valueOf(StileDiVita.class, rs.getString("stile_di_vita")));
                    progressiClienteDTO.setFrequenzaAllenamenti(rs.getString("frequenza_allenamenti"));
                    progressiClienteDTO.setSmoker(rs.getBoolean("is_smoker"));
                    progressiClienteDTO.setPatologie(rs.getString("patologie"));
                    progressiClienteDTO.setDisformismo(rs.getString("disformismo"));
                    progressiClienteDTO.setPeso(rs.getFloat("peso"));
                    progressiClienteDTO.setAltezza(rs.getFloat("altezza"));
                    progressiClienteDTO.setImc(rs.getFloat("imc"));
                    progressiClienteDTO.setCollo(rs.getFloat("collo"));
                    progressiClienteDTO.setSpalla(rs.getFloat("spalla"));
                    progressiClienteDTO.setTorace(rs.getFloat("torace"));
                    progressiClienteDTO.setBraccio(rs.getFloat("braccio"));
                    progressiClienteDTO.setPolso(rs.getFloat("polso"));
                    progressiClienteDTO.setVita(rs.getFloat("vita"));
                    progressiClienteDTO.setFianchi(rs.getFloat("fianchi"));
                    progressiClienteDTO.setCoscia(rs.getFloat("coscia"));
                    progressiClienteDTO.setPolpacci(rs.getFloat("polpacci"));
                    // Inizializzo la lista degli esercizi
                    progressiClienteDTO.setEsercizi(esercizi);
                    progressiClienteDTO.setDataEmissione(rs.getDate("data_emissione"));
                    progressiClienteDTO.setDurata(rs.getString("durata"));
                    progressiClienteDTO.setFrequenzaSettimanale(rs.getString("frequenza_settimanale"));
                    progressiClienteDTO.setTipoAttivita(rs.getString("tipo_attività"));
                }

                // Aggiungo gli esercizi alla lista
                Esercizio esercizio = new Esercizio();
                esercizio.setNomeEsercizio(rs.getString("nome_esercizio"));
                esercizio.setAltro(rs.getString("altro"));
                esercizio.setRepEx1(rs.getString("rep_ex1"));
                esercizio.setSerie(rs.getInt("serie"));
                esercizio.setRecupero(rs.getString("recupero"));
                esercizio.setSessione(rs.getString("sessione"));
                esercizi.add(esercizio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return progressiClienteDTO;
    }
    
    
    
    
}