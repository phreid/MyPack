package ui;

import model.AbstractEntry;

import javax.swing.*;

public class ButtonPanelController {
    private MainTableModel model;
    private JTable table;
    private MainMenuButtonPanel buttonPanel;

    public ButtonPanelController(JTable table, MainTableModel model, MainMenuButtonPanel buttonPanel) {
        this.model = model;
        this.table = table;
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
}
