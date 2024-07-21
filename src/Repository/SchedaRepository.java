package Repository;

import Model.Esercizio;
import Model.SchedaAllenamento;

public interface SchedaRepository 
{
    public String insertSchedaAllenamento(SchedaAllenamento schedaAllenamento);
    
    public String insertEsercizioo(Esercizio esercizio);
    
    public Long findMaxId(Long id_cliente);
    
    public Long findIdByIdCliente(Long id_cliente);
    
    public String deleteSchedaAllenamento(Long id_cliente);
    
    public void deleteEsercizioByIdAllenamento(Long id_scheda_allenamento);
}