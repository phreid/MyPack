package ui;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;

import javax.swing.*;

public class SinglePackButtonPanelController {
    private SinglePackTableModel model;
    private JTable table;
    private MyPackGUI parent;
    private SinglePackMenuButtonPanel buttonPanel;

    public SinglePackButtonPanelController(MyPackGUI parent, JTable table, SinglePackTableModel model,
                                           SinglePackMenuButtonPanel buttonPanel) {
        this.parent = parent;
        this.model = model;
        this.table = table;
        this.buttonPanel = buttonPanel;
    }

    // MODIFIES: model
    // EFFECTS: adds a new category to the model
    public void addCategory(PackList packList) {
        model.addCategory(packList);
    }

    // MODIFIES: parent
    // EFFECTS: sets the parent frame to show the main menu panel
    public void returnToMainMenu() {
        parent.showMainMenu();
    }

    // MODIFIES: buttonPanel
    // EFFECTS: enables the add item button if the currently selected row is a category
    public void enableAddItem() {
        if (table.getSelectedRow() == -1) {
            buttonPanel.setAddItemEnabled(false);
            return;
        }

        AbstractEntry entry = model.getRowEntry(table.getSelectedRow());
        if (entry instanceof Pack) {
            buttonPanel.setAddItemEnabled(false);
        } else if (entry instanceof PackList) {
            buttonPanel.setAddItemEnabled(true);
        } else {
            buttonPanel.setAddItemEnabled(false);
        }
    }

    // REQUIRES: the currently selected table row is a category
    // MODIFIES: model
    // EFFECTS: adds packItem to the currently selected category
    public void addItem(PackItem packItem) {
        PackList packList = (PackList) model.getRowEntry(table.getSelectedRow());
        model.addItem(packList, packItem);
    }
}
