package Repository;

import Model.Anamnesi;
import Model.Cliente;
import Model.DTO.ProgressiClienteDTO;
import Model.SchedaAntropometrica;
import java.util.List;

public interface ClienteRepository 
{
    public String insertCliente(Cliente cliente);
    
    public String deleteCliente(Long id);
    
    public String updateCliente(Cliente cliente);
    
    public Cliente findById(Long id);
    
    public Cliente findByName(String nome);
    
    public List<Cliente> findByAll();
    
    public Long findMaxId();
    
    public void createAnamnesiCliente(Anamnesi anamnesi);
    
    public String insertVisitaAntropometrica(SchedaAntropometrica schedaAntropometrica);
    
    public String deleteVisitaAntropometrica(Long id_cliente);
    
    public ProgressiClienteDTO findByNameProgressiCliente(String name);
}