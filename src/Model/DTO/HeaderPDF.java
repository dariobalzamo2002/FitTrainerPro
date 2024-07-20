package Model.DTO;

import java.util.Date;
import javax.swing.JTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderPDF 
{
    private String clientName;
    private String trainerName;
    private Date creationDate;
    private String durata; 
    private String frequenzaSettimanale;
    private String tipoAttivita;
    private String filePath;
    private JTable table;
}