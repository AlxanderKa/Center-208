package work;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static work.Main.addFromFile;

public class TestUI extends JFrame {

    private JFreeChart createChart(List<Point> points) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики",
                null,                        // x axis label
                null,                        // y axis label
                null,                        // data
                PlotOrientation.VERTICAL,
                true,                        // include legend
                true,                       // tooltips
                false                        // urls
        );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);

        // Определение отступа меток делений
        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        // Скрытие осевых линий и меток делений
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(true);    // осевая линия

        // Настройка NumberAxis
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(true);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Настройка XYSplineRenderer
        // Precision: the number of line segments between 2 points [default: 5]
        XYSplineRenderer r0 = new XYSplineRenderer(8);
        r0.setSeriesShapesVisible(0, true);

        XYSplineRenderer r1 = new XYSplineRenderer();
        r1.setPrecision(8);
        r1.setSeriesShapesVisible(0, true);

        XYSplineRenderer r2 = new XYSplineRenderer(8);

        r2.setSeriesShapesVisible(0, true);
        r2.setSeriesPaint(0, Color.orange);
        r2.setSeriesStroke(0, new BasicStroke(2.5f));

        // Наборы данных
        XYDataset dataset0 = Main.createDataset(0, points);
        XYDataset dataset1 = Main.createDataset(1, points);
        XYDataset dataset2 = Main.createDataset(2, points);

        plot.setDataset(0, dataset0);
        plot.setDataset(1, dataset1);
        plot.setDataset(2, dataset2);

        // Подключение Spline Renderer к наборам данных
        plot.setRenderer(0, r0);
        plot.setRenderer(1, r1);
        plot.setRenderer(2, r2);


        return chart;
    }

    public TestUI() {
        Box contents = new Box(BoxLayout.Y_AXIS);
        final JSplitPane splitHorizontal = new JSplitPane();
        splitHorizontal.setDividerSize(8);
        splitHorizontal.setDividerLocation(150);
        JSplitPane splitVerticalL = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        JSplitPane splitVerticalR = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitVerticalL.setDividerLocation(100);
        splitVerticalR.setDividerLocation(100);

        JButton start = new JButton();
        start.setText("Start");
        contents.add(start);
        JFileChooser fileChooser = new JFileChooser();


        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    List<Point> points = addFromFile(file.getPath());
                    String[][] outPoints = new String[points.size()][];
                    JTextArea text = new JTextArea(10, 100);

                    for (int i = 0; i < points.size(); i++) {
                        text.append(points.get(i).toString() + "\n");//add string to area
                        //  text.setLineWrap(true);
                        outPoints[i] = points.get(i).toArray();//add data on table

                    }
                    JFreeChart chart = createChart(points);
                    ChartPanel chartPanel = new ChartPanel(chart);

                    chartPanel.setPreferredSize(new java.awt.Dimension(560, 480));

                    JTable table1 = new JTable(outPoints, points.get(0).getName());//create data table
                    splitVerticalL.setTopComponent(new JScrollPane());
                    splitVerticalL.setBottomComponent(new JScrollPane(text));
                    splitVerticalR.setTopComponent(new JScrollPane(table1));
                    splitVerticalR.setBottomComponent(new JScrollPane(chartPanel));
                    splitHorizontal.setLeftComponent(splitVerticalL);
                    splitHorizontal.setRightComponent(splitVerticalR);
                    contents.add(splitHorizontal);
                    //  contents.add(chartPanel);
                    //  contents.add(new JScrollPane(table1));
                    // contents.add(new JScrollPane(text));
                }
            }

        });
        // System.out.println(points.stream().toString());

        setContentPane(contents);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        TestUI ui = new TestUI();
    }
}
