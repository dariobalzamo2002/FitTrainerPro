package Model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento 
{
    private Long id;
    private Date dataDiPagamento;
    private Double importo;
    private Cliente cliente;
}
