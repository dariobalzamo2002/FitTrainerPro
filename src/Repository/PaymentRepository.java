package Repository;

import Model.Pagamento;
import java.util.List;


public interface PaymentRepository 
{    
    public String insertPayment(Pagamento payment);
    
    public List<Pagamento> findByClientID(Long id_cliente);
    
    public String deleteByClientID(Long id_cliente);
}
