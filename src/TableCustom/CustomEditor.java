package TableCustom;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class CustomEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JTextField textField;
    private JButton button;

    public CustomEditor() {
        panel = new JPanel(new BorderLayout());
        textField = new JTextField();
        button = new JButton("Salva");

        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText(value != null ? value.toString() : "");
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }
}

