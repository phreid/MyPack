package ui;

import model.AbstractEntry;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MainTableModel extends AbstractTableModel {
    private List<String> colNames;
    private List<AbstractEntry> entries;

    // EFFECTS: creates a new table model
    public MainTableModel(List<AbstractEntry> entries) {
        super();

        colNames = new ArrayList<>();
        colNames.add("Name");
        colNames.add("Description");
        colNames.add("Cost");
        colNames.add("Total Weight");
        colNames.add("Base Weight");

        this.entries = entries;
        fireTableDataChanged();
    }

//    // MODIFIES: this
//    // EFFECTS: sets the table data to entryList and updates table
//    void setTableData(List<AbstractEntry> entries) {
//        this.entries = entries;
//        fireTableDataChanged();
//    }

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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex < 2;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        AbstractEntry entry = entries.get(rowIndex);
        switch (columnIndex) {
            case 0:
                entry.setName((String) value);
                break;
            default:
                entry.setDescription((String) value);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addEntry(AbstractEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(int row) {
        entries.remove(row);
    }

    public AbstractEntry getEntry(int row) {
        return entries.get(row);
    }
}
