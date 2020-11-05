package ui;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SinglePackTablePanel extends JPanel {
    private JTable table;
    private MyTableModel model;

    // EFFECTS: constructs a new TablePanel containing the provided list of AbstractEntries
    public SinglePackTablePanel() {
        super();
        model = new MyTableModel();
        table = new PackTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.5)));
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);
        table.setShowGrid(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }
    
    // MODIFIES: this
    // EFFECTS: sets the table data to entryList and updates table
    public void setTableData(Pack pack) {
        List<AbstractEntry> entries = getAllEntries(pack);
        model.setTableData(entries);
    }

    // EFFECTS: returns all the entries in packList, including packList itself.
    //          ui helper function, allows to users select PackItems directly from the
    //          pack menu without needing to select a category first
    private List<AbstractEntry> getAllEntries(PackList packList) {
        List<AbstractEntry> result = new ArrayList<>();
        result.add(packList);

        for (int i = 0; i < packList.size(); i++) {
            AbstractEntry entry = packList.get(i);
            result.add(entry);
            if (entry.hasChildren()) {
                PackList list = (PackList) entry;
                for (int j = 0; j < list.size(); j++) {
                    result.add(list.get(j));
                }
            }
        }

        return result;
    }

    // https://tips4java.wordpress.com/2010/01/24/table-row-rendering/
    private class PackTable extends JTable {

        // EFFECTS: creates a new PackTable
        public PackTable(AbstractTableModel model) {
            super(model);
        }

        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);

            if (model.getRowEntry(row) instanceof PackList) {
                Border bottom = new MatteBorder(0, 0, 1, 0, Color.BLACK);
                component.setBorder(bottom);
            }

            return component;
        }
    }

    private class MyTableModel extends AbstractTableModel {
        private List<String> colNames;
        private List<AbstractEntry> entries;
        private Pack pack;

        private String tab = "    ";

        // EFFECTS: creates a new table model
        public MyTableModel() {
            super();

            colNames = new ArrayList<>();
            colNames.add("Name");
            colNames.add("Description");
            colNames.add("Cost");
            colNames.add("Total Weight");
            colNames.add("Base Weight");
            colNames.add("Worn?");
            colNames.add("Consumable?");
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
                    return getTabbedName(entry);
                case 1:
                    return entry.getDescription();
                case 2:
                    return entry.getCost();
                case 3:
                    return entry.getTotalWeight();
                case 4:
                    return entry.getBaseWeight();
                case 5:
                    return getIsWorn(entry);
                default:
                    return getIsConsumable(entry);
            }
        }

        // EFFECTS: returns the entry in the given row
        private AbstractEntry getRowEntry(int row) {
            return entries.get(row);
        }

        private String getTabbedName(AbstractEntry entry) {
            String name = entry.getName();

            if (entry instanceof Pack) {
                return name;
            } else if (entry instanceof PackList) {
                return tab + name;
            } else {
                return tab + tab + name;
            }
        }

        // EFFECTS: if entry is not a PackItem, return false. otherwise
        //          return true if entry is consumable
        private Boolean getIsConsumable(AbstractEntry entry) {
            if (entry.hasChildren()) {
                return false;
            } else {
                return ((PackItem) entry).isConsumable();
            }
        }

        // EFFECTS: if entry is not a PackItem, return false. otherwise
        //          return true if entry is worn
        private Boolean getIsWorn(AbstractEntry entry) {
            if (entry.hasChildren()) {
                return false;
            } else {
                return ((PackItem) entry).isWorn();
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
                case 3:
                case 4:
                    return Integer.class;
                default:
                    return Boolean.class;
            }
        }


    }
}

