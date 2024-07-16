package Repository;

import Model.Esercizio;
import Model.SchedaAllenamento;

public interface SchedaRepository 
{
    public String insertSchedaAllenamento(SchedaAllenamento schedaAllenamento);
    
    public String insertEsercizioo(Esercizio esercizio);
    
    public Long findMaxId(Long id_cliente);
}