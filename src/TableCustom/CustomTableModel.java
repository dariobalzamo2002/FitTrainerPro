package TableCustom;

import javax.swing.table.DefaultTableModel;

class CustomTableModel extends DefaultTableModel {
    public CustomTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Rendi tutte le celle modificabili
        return true;
    }
}
