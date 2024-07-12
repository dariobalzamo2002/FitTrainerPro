package Model;

import enums.Genere;
import enums.MetodoDiPagamento;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente 
{
    private Long id;
    private String nome;
    private String email;
    private Long cellulare;
    private Genere sesso;
    private Date dataDiNascita;
    private String luogoDiNascita;
    private MetodoDiPagamento metodoDiPagamento;
    
}