package ui.controller;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;
import ui.MyPackGUI;
import ui.adapter.SinglePackTableModel;
import ui.panel.PieChartPanel;
import ui.panel.SinglePackMenuButtonPanel;

import javax.swing.*;

/**
 * Controller for the button panel in the single pack menu.
 */
public class SinglePackButtonPanelController {
    private PieChartPanel chartPanel;
    private SinglePackTableModel model;
    private JTable table;
    private MyPackGUI parent;
    private SinglePackMenuButtonPanel buttonPanel;

    // EFFECTS: creates a new controller
    public SinglePackButtonPanelController(MyPackGUI parent, JTable table, PieChartPanel chartPanel,
                                           SinglePackTableModel model,
                                           SinglePackMenuButtonPanel buttonPanel) {
        this.parent = parent;
        this.model = model;
        this.table = table;
        this.buttonPanel = buttonPanel;
        this.chartPanel = chartPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds a new category to the model
    public void addCategory(PackList packList) {
        model.addCategory(packList);
        chartPanel.refreshData();
    }

    // MODIFIES: this
    // EFFECTS: sets the parent frame to show the main menu panel
    public void returnToMainMenu() {
        parent.showMainMenu();
    }

    // MODIFIES: this
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
    // MODIFIES: this
    // EFFECTS: adds packItem to the currently selected category
    public void addItem(PackItem packItem) {
        PackList packList = (PackList) model.getRowEntry(table.getSelectedRow());
        model.addItem(packList, packItem);
        chartPanel.refreshData();
    }
}
