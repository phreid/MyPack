package ui;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private MyTableModel model;

    // EFFECTS: constructs a new TablePanel containing the provided list of AbstractEntries
    public TablePanel() {
        super();
        model = new MyTableModel();
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.5)));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: sets the table data to entryList and updates table
    public void setTableData(List<AbstractEntry> entries) {
        model.setTableData(entries);
    }

    private class MyTableModel extends AbstractTableModel {
        private List<String> colNames;
        private List<AbstractEntry> entries;

        // EFFECTS: creates a new table model
        public MyTableModel() {
            super();

            colNames = new ArrayList<>();
            colNames.add("Name");
            colNames.add("Description");
            colNames.add("Cost");
            colNames.add("Total Weight");
            colNames.add("Base Weight");
        }

        // MODIFIES: this
        // EFFECTS: sets the table data to entryList and updates table
        private void setTableData(List<AbstractEntry> entries) {
            this.entries = entries;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return entries.size();
        }

        @Override
        public int getColumnCount() {
            return colNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            AbstractEntry entry = entries.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return entry.getName();
                case 1:
                    return entry.getDescription();
                case 2:
                    return entry.getCost();
                case 3:
                    return entry.getTotalWeight();
                default:
                    return entry.getBaseWeight();
            }
        }

        @Override
        public String getColumnName(int column) {
            return colNames.get(column);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return Double.class;
                default:
                    return Integer.class;
            }
        }
    }
}
