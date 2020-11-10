package ui;

import model.Pack;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class PieChartPanel extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public PieChartPanel() {
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("One", 10);
        data.setValue("two", 100);

        chart = ChartFactory.createPieChart("Chart", data);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }
}
