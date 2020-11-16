package ui.panel;

import model.AbstractEntry;
import ui.controller.MainButtonPanelController;
import ui.adapter.MainTableModel;
import ui.MyPackGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel for the main menu. Holds all main menu components.
 */
public class MainMenuPanel extends JPanel {
    private JTable table;
    private MainTableModel model;
    private MyPackGUI parent;
    MainButtonPanelController controller;

    // EFFECTS: constructs a new MainMenuPanel
    public MainMenuPanel(MyPackGUI parent, List<AbstractEntry> entries) {
        super();
        this.parent = parent;

        setPreferredSize(parent.getSize());

        model = new MainTableModel(entries);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.5)));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setupMainMenuButtonPanel();

        table.getSelectionModel().addListSelectionListener(e -> {
            controller.setEditEnabled(true);
            controller.setDeleteEnabled(true);
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the main menu button panel and adds it to this panel
    private void setupMainMenuButtonPanel() {
        MainMenuButtonPanel buttonPanel = new MainMenuButtonPanel();
        controller = new MainButtonPanelController(parent, table, model, buttonPanel);
        buttonPanel.setController(controller);

        add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets the table panel's data to packList
    public void setTableData(List<AbstractEntry> packList) {
        model.setData(packList);
    }
}
