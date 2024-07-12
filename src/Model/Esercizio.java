package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Esercizio 
{
    private Long id;
    private SchedaAllenamento schedaAllenamento;
    private String nomeEsercizio;
    private int repEx1;
    private int repEx2;
    private int serie;
    private int recupero;
}