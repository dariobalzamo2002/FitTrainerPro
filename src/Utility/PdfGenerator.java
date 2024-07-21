package Utility;

import Model.DTO.HeaderPDF;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javax.swing.table.TableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static void generatePdf(HeaderPDF headerPDF) 
    {
        Document document = new Document(PageSize.A4);
        
        try {
            // Crea PdfWriter e imposta l'evento per il footer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(headerPDF.getFilePath()));
            writer.setPageEvent(new FooterPageEvent());

            document.open();

            // Aggiungi intestazione
            Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph header = new Paragraph("Scheda di Allenamento", font);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // Crea una linea separatrice
            LineSeparator lineSeparator = new LineSeparator();
            document.add(new Paragraph(" ")); // Linea vuota
            document.add(lineSeparator);

            // Aggiungi dettagli del trainer e del cliente
            document.add(new Paragraph("Personal Trainer: " + headerPDF.getTrainerName()));
            document.add(new Paragraph("Cliente: " + headerPDF.getClientName()));
            document.add(new Paragraph("Data di Creazione: " + headerPDF.getCreationDate()));
            document.add(new Paragraph("Durata scheda: " + headerPDF.getDurata()));
            document.add(new Paragraph("Tipo di attività: " + headerPDF.getTipoAttivita()));
            document.add(new Paragraph("Frequenza allenamenti settimanali: " + headerPDF.getFrequenzaSettimanale()));
            document.add(new Paragraph(" ")); // Linea vuota

            // Aggiungi tabella
            PdfPTable pdfTable = new PdfPTable(headerPDF.getTable().getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Aggiungi intestazioni colonne
            TableModel model = headerPDF.getTable().getModel();
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

            // Chiudi il documento
            document.close();

            System.out.println("PDF creato con successo: " + headerPDF.getFilePath());
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Classe per gestire il footer
    static class FooterPageEvent extends PdfPageEventHelper {
        private static final String FOOTER_TEXT = "© 2024 Fit-trainer by Dario Balzamo. All rights reserved. Created with java v1.0";

        private BaseFont baseFont;
        private Font font;

        public FooterPageEvent() {
            try {
                // Carica il font (Arial, Helvetica, o qualsiasi altro font di tua scelta)
                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                font = new Font(baseFont, 10);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContent();
            float pageWidth = document.getPageSize().getWidth();
            float margin = 30; // Margine dal bordo della pagina

            canvas.beginText();
            canvas.setFontAndSize(baseFont, 10);

            // Aggiungi il testo del footer
            canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, FOOTER_TEXT, pageWidth / 2, margin, 0);

            canvas.endText();
        }
    }
    
}