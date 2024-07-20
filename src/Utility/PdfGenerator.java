package Utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfGenerator {

    public static void generatePdf(String trainerName, String clientName, 
            String creationDate, JTable table, String filePath) 
{
    Document document = new Document(PageSize.A4);
    try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Aggiungi intestazione
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph header = new Paragraph("Scheda di Allenamento", font);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        
        // Aggiungi dettagli del trainer e del cliente
        document.add(new Paragraph("Personal Trainer: " + trainerName));
        document.add(new Paragraph("Cliente: " + clientName));
        document.add(new Paragraph("Data di Creazione: " + creationDate));
        document.add(new Paragraph(" ")); // Linea vuota

        // Aggiungi tabella
        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        pdfTable.setWidthPercentage(100);

        // Aggiungi intestazioni colonne
        TableModel model = table.getModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            PdfPCell cell = new PdfPCell(new Phrase(model.getColumnName(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
        }

        // Aggiungi righe della tabella
        for (int rows = 0; rows < model.getRowCount(); rows++) {
            for (int cols = 0; cols < model.getColumnCount(); cols++) {
                Object cellValue = model.getValueAt(rows, cols);
                String cellString = (cellValue != null) ? cellValue.toString() : "";
                pdfTable.addCell(cellString);
            }
        }

        document.add(pdfTable);
        document.close();
    } catch (DocumentException | FileNotFoundException e) {
        e.printStackTrace();
    }
}

}

