package Repository;

import Model.Cliente;
import Model.Esercizio;
import Model.SchedaAllenamento;

public interface SchedaRepository 
{
    public String insertSchedaAllenamento(SchedaAllenamento schedaAllenamento);
    
    public String insertEsercizioo(Esercizio esercizio);
    
    public Long findIdAllenamentoByIdCliente(Long id_cliente);
    
    public String deleteSchedaAllenamento(Long id_cliente);
    
    public void deleteEsercizioByIdAllenamento(Long id_scheda_allenamento);
    
    public SchedaAllenamento getSchedaAllenamento(Long id_cliente, Cliente cliente);
            
    public String updateSchedaAllenamento(SchedaAllenamento schedaAllenamento);
}