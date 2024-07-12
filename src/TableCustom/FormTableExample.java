package TableCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormTableExample extends JFrame {
    private CustomTableModel model;
    private JTable table;

    public FormTableExample() {
        setTitle("Form Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        String[] columnNames = {"Esercizio", "Ripetizioni", "Serie", "Recupero"};
        Object[][] data = {};

        model = new CustomTableModel(data, columnNames);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setCellEditor(new CustomEditor());
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Aggiungi Riga");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{"", "", "", ""});
            }
        });

        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormTableExample().setVisible(true);
            }
        });
    }
}

