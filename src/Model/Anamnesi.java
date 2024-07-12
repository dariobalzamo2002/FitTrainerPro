package Model;

import enums.StileDiVita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anamnesi 
{
    private Long id;
    private StileDiVita stileDiVita;
    private int frequenzaAllenamenti;
    private boolean isSmoker; 
    private String patologie;
    private String dismorfismo;
    private Cliente cliente;
}