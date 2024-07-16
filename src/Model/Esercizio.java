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
    private String repEx1;
    private String altro;
    private int serie;
    private int recupero;
    private String sessione;
}