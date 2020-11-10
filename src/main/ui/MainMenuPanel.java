package ui;

import model.AbstractEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class MainMenuPanel extends JPanel {
    private JTable table;
    private MainTableModel model;
    private MyPackGUI parent;
    MainButtonPanelController controller;

    // EFFECTS: constructs a new MainTablePanel
    public MainMenuPanel(MyPackGUI parent, List<AbstractEntry> entries) {
        super();
        this.parent = parent;
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

    private void setupMainMenuButtonPanel() {
        MainMenuButtonPanel buttonPanel = new MainMenuButtonPanel();
        controller = new MainButtonPanelController(parent, table, model, buttonPanel);
        buttonPanel.setController(controller);

        add(buttonPanel);
    }
}
