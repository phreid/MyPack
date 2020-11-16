package ui.adapter;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for the single pack menu table.
 */
public class SinglePackTableModel extends AbstractTableModel {
    private List<String> colNames;
    private List<AbstractEntry> entries;
    private Pack pack;

    private static final String TAB = "    ";

    // EFFECTS: creates a new table model
    public SinglePackTableModel(Pack pack) {
        super();
        this.pack = pack;
        this.entries = getAllEntries(pack);

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
    // EFFECTS: refreshes the data in entries to reflect what's currently in pack
    private void refreshData() {
        this.entries = getAllEntries(pack);
        fireTableDataChanged();
    }

    // MODIFIES: this
    // EFFECTS: adds category to this
    public void addCategory(PackList packList) {
        pack.add(packList);
        refreshData();
    }

    // EFFECTS: returns all the entries in pack, including pack itself.
    private List<AbstractEntry> getAllEntries(Pack pack) {
        List<AbstractEntry> result = new ArrayList<>();
        result.add(pack);

        for (int i = 0; i < pack.size(); i++) {
            AbstractEntry entry = pack.get(i);
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
    public AbstractEntry getRowEntry(int row) {
        return entries.get(row);
    }

    // EFFECTS: returns entry's name with appropriate indentation level
    private String getTabbedName(AbstractEntry entry) {
        String name = entry.getName();

        if (entry instanceof Pack) {
            return name;
        } else if (entry instanceof PackList) {
            return TAB + name;
        } else {
            return TAB + TAB + name;
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

    // MODIFIES: this
    // EFFECTS: adds packItem to packList and refreshes the data
    public void addItem(PackList packList, PackItem packItem) {
        packList.add(packItem);
        refreshData();
    }

    // EFFECTS: returns all the categories in the model
    public List<AbstractEntry> getAllCategories() {
        List<AbstractEntry> result = new ArrayList<>();
        for (int i = 0; i < pack.size(); i++) {
            AbstractEntry entry = pack.get(i);
            if (! (entry instanceof PackItem)) {
                result.add(entry);
            }
        }
        return result;
    }
}

