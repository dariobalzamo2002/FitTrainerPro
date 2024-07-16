package Model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedaAllenamento 
{
    private Long id;
    private Cliente cliente;
    private Date dataEmissione;
}