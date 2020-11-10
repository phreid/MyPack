package ui;

import model.AbstractEntry;
import model.Pack;

import javax.swing.*;

public class MainButtonPanelController {
    private MainTableModel model;
    private JTable table;
    private MainMenuButtonPanel buttonPanel;
    private MyPackGUI parent;

    public MainButtonPanelController(MyPackGUI parent, JTable table,
                                     MainTableModel model, MainMenuButtonPanel buttonPanel) {
        this.model = model;
        this.table = table;
        this.parent = parent;
        this.buttonPanel = buttonPanel;
    }

    void addEntry(AbstractEntry entry) {
        model.addEntry(entry);
        model.fireTableDataChanged();
    }

    void deleteSelectedEntry() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeEntry(row);
            model.fireTableDataChanged();
        }
    }

    public void editSelectedEntry() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Pack pack = (Pack) model.getEntry(row);
            parent.openSinglePackMenu(pack);
        }
    }

    public void setEditEnabled(boolean b) {
        buttonPanel.setEditEnabled(b);
    }

    public void setDeleteEnabled(boolean b) {
        buttonPanel.setDeleteEnabled(b);
    }
}
