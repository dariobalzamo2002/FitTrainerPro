package Model;

import enums.Emilato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedaAntropometrica
{
    private Long id;
    private Cliente cliente;
    private float peso;
    private double altezza;
    private double imc;
    private Emilato emilato;
    private float spalla;
    private float torace;
    private float vita;
    private float fianchi; 
    private float braccio; 
    private float polso; 
    private float coscia;
    private float polpacci;
    private float collo;
    
}