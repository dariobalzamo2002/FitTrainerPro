package Model.DTO;

import Model.Esercizio;
import enums.StileDiVita;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressiClienteDTO 
{
    private Long id;
    private String nome;
    private String email;
    private String cellulare;
    private Date dataDiNascita;
    private String luogoDiNascita;
    
    private StileDiVita stileDiVita;
    private String frequenzaAllenamenti;
    private boolean isSmoker;
    private String patologie;
    private String disformismo;
    
    private float peso;
    private float altezza;
    private float imc;
    private float collo;
    private float spalla;
    private float torace;
    private float braccio;
    private float polso;
    private float vita;
    private float fianchi;
    private float coscia;
    private float polpacci;
    
    private List<Esercizio> esercizi;
    private Date dataEmissione;
    
}
