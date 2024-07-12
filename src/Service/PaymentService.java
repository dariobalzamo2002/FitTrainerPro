package Service;

import Model.Cliente;
import Model.Pagamento;
import Repository.PaymentRepository;
import Utility.ConnectionProvider;
import enums.Genere;
import enums.MetodoDiPagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PaymentService implements PaymentRepository
{
    private static PaymentService instance = null;
    private ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    
    private PaymentService(){
        
    }
    
    public static PaymentService getInstance(){
        if(instance == null)
            instance = new PaymentService();
        return instance;
    }
    
    @Override
    public String insertPayment(Pagamento payment) 
    {
        String response ="Operazione di inserimento fallita!";
        
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        
        String sql = "Insert into payments (data_di_pagamento, importo, id_cliente) values(?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(payment.getDataDiPagamento().getTime()));
            ps.setDouble(2, payment.getImporto());
            ps.setLong(3, payment.getCliente().getId());
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
    public List<Pagamento> findByClientID(Long id_cliente) 
    {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pagamento> listapagamenti = new ArrayList<>();
        
        String sql ="Select * from payments where id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_cliente);
            rs = ps.executeQuery();
            
            while (rs.next()) 
            {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getLong("id"));
                pagamento.setDataDiPagamento(rs.getDate("data_di_pagamento"));
                pagamento.setImporto(rs.getDouble("importo"));
                listapagamenti.add(pagamento);
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
        
        return listapagamenti;
    }

    @Override
    public String deleteByClientID(Long id_cliente) {
        Connection conn = connectionProvider.getConnection();
        PreparedStatement ps = null;
        
        String response = "Operazione di cancellazione fallita!";
        
        String sql = "Delete from payments where id_cliente = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id_cliente);
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
        
        return response;
    }
    
}