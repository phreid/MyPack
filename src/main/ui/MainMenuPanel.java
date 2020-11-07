package ui;

import model.AbstractEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainMenuPanel extends JPanel {
    private JTable table;
    private MainTableModel model;
    MainMenuButtonPanel buttonPanel;
    ButtonPanelController controller;

    // EFFECTS: constructs a new MainTablePanel
    public MainMenuPanel(List<AbstractEntry> entries) {
        super();
        model = new MainTableModel(entries);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.5)));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setupMainMenuButtonPanel();
    }

    private void setupMainMenuButtonPanel() {
        MainMenuButtonPanel buttonPanel = new MainMenuButtonPanel();
        ButtonPanelController controller = new ButtonPanelController(table, model, buttonPanel);
        buttonPanel.setController(controller);

        add(buttonPanel);
    }
//
//    // MODIFIES: this
//    // EFFECTS: sets the table data to entryList and updates table
//    public void setTableData(List<AbstractEntry> entries) {
//        model.setTableData(entries);
//    }

    MainTableModel getModel() {
        return model;
    }
}
