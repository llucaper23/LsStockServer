package View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class Top10View extends JPanel {

    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH = 1250;

    public Top10View() {
        JPanel jpGraficas =new JPanel();


        // farem el set dels valors que tinguem en la nostre BD

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Visitas del sitio web 1
        for (int i = 0; i < 10; i++) {
            dataset.setValue(i*10, "Companyia", "Telefonica"+i);
        }

        JFreeChart chart = ChartFactory.createBarChart3D("", "Companyia", "Valor", dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize(new Dimension( MAX_WIDTH , MAX_HEIGHT ) );

        jpGraficas.add(chartPanel);

        this.add(jpGraficas,BorderLayout.CENTER);
    }



}
