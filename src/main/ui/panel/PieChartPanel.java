package ui.panel;

import model.AbstractEntry;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import ui.adapter.SinglePackTableModel;

import javax.swing.*;
import java.util.List;

/**
 * The pie chart panel for the single pack menu.
 */
public class PieChartPanel extends JPanel {
    private JFreeChart chart;
    private DefaultPieDataset data;
    private ChartPanel chartPanel;
    private SinglePackTableModel model;

    // EFFECTS: creates a new pie chart panel with the data from model
    public PieChartPanel(SinglePackTableModel model) {
        this.model = model;
        data = new DefaultPieDataset();
        refreshData();

        chart = ChartFactory.createPieChart("Chart", data);
        chart.setTitle("Categories by Total Weight");
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    // MODIFIES: this
    // EFFECTS: refreshes the pie chart to show updated model data
    public void refreshData() {
        List<AbstractEntry> categories = model.getAllCategories();
        for (AbstractEntry category : categories) {
            data.setValue(category.getName(), category.getTotalWeight());
        }
    }
}
