package ui;

import model.AbstractEntry;
import model.Pack;
import model.PackList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SinglePackMenuPanel extends JPanel {
    private JTable table;
    private SinglePackTableModel model;
    private MyPackGUI parent;
    private PieChartPanel chartPanel;
    SinglePackButtonPanelController controller;

    // EFFECTS: constructs a new SinglePackMenuPanel
    public SinglePackMenuPanel(MyPackGUI parent, Pack pack) {
        super();
        this.parent = parent;
        model = new SinglePackTableModel(pack);

        table = new PackTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.5)));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            controller.enableAddItem();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setupSinglePackMenuButtonPanel();
        setupPieChartPanel();
    }

    private void setupPieChartPanel() {
        chartPanel = new PieChartPanel();
        add(chartPanel);
    }

    private void setupSinglePackMenuButtonPanel() {
        SinglePackMenuButtonPanel buttonPanel = new SinglePackMenuButtonPanel();
        controller = new SinglePackButtonPanelController(parent, table, model, buttonPanel);
        buttonPanel.setController(controller);

        add(buttonPanel);
    }

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
}
