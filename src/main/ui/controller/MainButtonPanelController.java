package ui.controller;

import model.AbstractEntry;
import model.Pack;
import ui.adapter.MainTableModel;
import ui.MyPackGUI;
import ui.panel.MainMenuButtonPanel;

import javax.swing.*;

/**
 * Controller for the main menu button panel.
 */
public class MainButtonPanelController {
    private MainTableModel model;
    private JTable table;
    private MainMenuButtonPanel buttonPanel;
    private MyPackGUI parent;

    // EFFECTS: Constructs a new controller
    public MainButtonPanelController(MyPackGUI parent, JTable table,
                                     MainTableModel model, MainMenuButtonPanel buttonPanel) {
        this.model = model;
        this.table = table;
        this.parent = parent;
        this.buttonPanel = buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds entry to the main menu table model
    public void addEntry(AbstractEntry entry) {
        model.addEntry(entry);
        model.fireTableDataChanged();
    }

    // MODIFIES: this
    // EFFECTS: if a table row is currently selected,
    //          deletes the currently selected entry. otherwise, does nothing
    public void deleteSelectedEntry() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeEntry(row);
            model.fireTableDataChanged();
        }
    }

    // MODIFIES: this
    // EFFECTS: if a table row is currently selected, opens the edit pack menu.
    //          otherwise, does nothing
    public void editSelectedEntry() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Pack pack = (Pack) model.getEntry(row);
            parent.openSinglePackMenu(pack);
        }
    }

    // MODIFIES: this
    // EFFECTS: enables the edit button
    public void setEditEnabled(boolean b) {
        buttonPanel.setEditEnabled(b);
    }

    // MODIFIES: this
    // EFFECTS: enables the delete button
    public void setDeleteEnabled(boolean b) {
        buttonPanel.setDeleteEnabled(b);
    }

    // MODIFIES: this
    // EFFECTS: saves the user's data to file
    public void save(String name) {
        parent.saveAndExit(name);
    }

    // MODIFIES: this
    // EFFECTS: loads the user's data from file
    public void load(String name) {
        parent.load(name);
    }

    // MODIFIES: this
    // EFFECTS: exits the application
    public void exit() {
        parent.exit();
    }
}
