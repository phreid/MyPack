package ui.panel;

import model.Pack;
import model.PackList;
import ui.MyPackGUI;
import ui.controller.SinglePackButtonPanelController;
import ui.adapter.SinglePackTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Panel for the single pack menu.
 */
public class SinglePackMenuPanel extends JPanel {
    private JTable table;
    private SinglePackTableModel model;
    private MyPackGUI parent;
    private PieChartPanel chartPanel;
    SinglePackButtonPanelController controller;

    // EFFECTS: creates a new single pack menu panel
    public SinglePackMenuPanel(MyPackGUI parent, Pack pack) {
        super();
        setLayout(new BorderLayout());
        this.parent = parent;
        model = new SinglePackTableModel(pack);

        table = new PackTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension((int) (MyPackGUI.FRAME_WIDTH * 0.8), (int) (MyPackGUI.FRAME_HEIGHT * 0.3)));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            controller.enableAddItem();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.NORTH);

        setupPieChartPanel();
        setupSinglePackMenuButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates the pie chart panel and adds it to this panel
    private void setupPieChartPanel() {
        chartPanel = new PieChartPanel(model);
        add(chartPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates the single pack menu button panel and adds it to this panel
    private void setupSinglePackMenuButtonPanel() {
        SinglePackMenuButtonPanel buttonPanel = new SinglePackMenuButtonPanel();
        controller = new SinglePackButtonPanelController(parent, table, chartPanel, model, buttonPanel);
        buttonPanel.setController(controller);

        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * Custom JTable, implements row styling
     */
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
