package work;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import static work.Main.addFromFile;

public class TestTest extends JFrame {
    JScrollPane graphScroll;
    ChartPanel graph;
    JFreeChart chart;


    private JFreeChart createChart(List<Point> points) {
//        final JFreeChart chart = ChartFactory.createXYLineChart(
//                "Графики",
//                "T, c",                        // x axis label
//                null,                        // y axis label
//                null,                        // data
//                PlotOrientation.VERTICAL,
//                true,                        // include legend
//                true,                       // tooltips
//                false                        // urls
//        );
//
//        chart.setBackgroundPaint(Color.white);

        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Графики"));

//        plot.setBackgroundPaint(new Color(232, 232, 232));
//
//        plot.setDomainGridlinePaint(Color.gray);
//        plot.setRangeGridlinePaint(Color.gray);
//
//        // Определение отступа меток делений
//        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
//
//        // Скрытие осевых линий и меток делений
//        ValueAxis axis = plot.getDomainAxis();
//        axis.setAxisLineVisible(true);    // осевая линия
//
//        // Настройка NumberAxis
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setAxisLineVisible(true);
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        // Настройка XYSplineRenderer
        // Precision: the number of line segments between 2 points [default: 5]
        XYSplineRenderer r0 = new XYSplineRenderer(8);
        r0.setSeriesShapesVisible(5, true);


        XYSplineRenderer r1 = new XYSplineRenderer();
        r1.setPrecision(8);
        r1.setSeriesShapesVisible(0, true);

        XYSplineRenderer r2 = new XYSplineRenderer(8);

        r2.setSeriesShapesVisible(0, true);
        r2.setSeriesPaint(0, Color.orange);
        r2.setSeriesStroke(0, new BasicStroke(2.5f));


        XYDataset dataset0 = Main.createDataset(-1, points);
        XYDataset dataset1 = Main.createDataset(-2, points);
        NumberAxis rangeAxis1 = new NumberAxis("V, м/с");
        NumberAxis rangeAxis2 = new NumberAxis("Координата, м");
        rangeAxis2.setAutoRangeIncludesZero(false);
        XYPlot subplot1 = new XYPlot(dataset0, null, rangeAxis1, r1);
        XYPlot subplot2 = new XYPlot(dataset1, null, rangeAxis2, r0);


        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        subplot1.setBackgroundPaint(new Color(232, 232, 232));
        subplot1.setDomainGridlinePaint(Color.gray);
        subplot1.setRangeGridlinePaint(Color.gray);
        subplot1.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        subplot2.setBackgroundPaint(new Color(232, 232, 232));
        subplot2.setDomainGridlinePaint(Color.gray);
        subplot2.setRangeGridlinePaint(Color.gray);
        subplot2.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));


//        // Наборы данных
//        XYDataset dataset0 = Main.createDataset(0, points);
//        XYDataset dataset1 = Main.createDataset(1, points);
//        XYDataset dataset2 = Main.createDataset(2, points);
//
//
//        plot.setDataset(1, dataset1);
//        plot.setDataset(2, dataset2);
//
//        // Подключение Spline Renderer к наборам данных
//        plot.setRenderer(0, r0);
//        plot.setRenderer(1, r1);
//        plot.setRenderer(2, r2);


        plot.setGap(10.0);
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        return new JFreeChart(
                "Графики",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true
        );
    }

    private TestTest() {
        JFileChooser fileChooser = new JFileChooser();
        List<Point> point = new ArrayList<>();
        // JPanel mainPanel = new JPanel();
        JFrame frame = new JFrame();
        Container contents = new Box(BoxLayout.Y_AXIS);
        Box textBox = new Box(BoxLayout.Y_AXIS);
        Box tableBox = new Box(BoxLayout.Y_AXIS);
        Box graphBox = new Box(BoxLayout.Y_AXIS);
        Box catalogBox = new Box(BoxLayout.Y_AXIS);
        Box menuBox = new Box(BoxLayout.Y_AXIS);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem openLast = new JMenuItem("Открыть недавние");
        JMenuItem close = new JMenuItem("Закрыть");
        JMenuItem closeAll = new JMenuItem("Закрыть все");
        file.add(open);
        file.addSeparator();
        file.add(openLast);
        file.addSeparator();
        file.add(close);
        file.addSeparator();
        file.add(closeAll);

        JMenu viewMenu = new JMenu("Настройки");
        JMenuItem settings = new JMenuItem("Сохранить положение окон");
        viewMenu.add(settings);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(file);
        menuBar.add(viewMenu);
//        menuBox.add(menuBar);
//        setJMenuBar(menuBar);
//        contents.add(menuBox);

//        menuBox.add(menuBar);
//            contents.add(menuBox);


//        JLabel mainLabel = new JLabel("Траектории");
//        //  mainLabel.setBorder(BorderFactory.createLineBorder(Color.black));
//         //mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        JButton fileBut = new JButton("Файл");
//        JButton settingsBut = new JButton("Настройки");
//        contents.add(mainLabel);
//        contents.add(fileBut);
//        contents.add(settingsBut);


        final JSplitPane verticalSplit = new JSplitPane();
        verticalSplit.setDividerSize(8);
        verticalSplit.setDividerLocation(150);
        JSplitPane rHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        JSplitPane lHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        rHorizontal.setDividerLocation(100);
        lHorizontal.setDividerLocation(100);

        JLabel catalogLabel = new JLabel("Каталог");
        catalogBox.add(catalogLabel);

        JLabel fileLabel = new JLabel("Файл");
        JLabel filePathLabel = new JLabel();
        // JScrollPane fileScroll = new JScrollPane();
        JTextArea fileText = new JTextArea();
        textBox.add(fileLabel);
        textBox.add(new JScrollPane(filePathLabel));
        // fileScroll.add(fileText);
        //  tableBox.add(fileScroll);

        JLabel tableLabel = new JLabel("Таблица");
        // JScrollPane tableScroll = new JScrollPane();
        //JTable table = new JTable();
        tableBox.add(tableLabel);
        //  tableBox.add(tableScroll);

        JLabel graphLabel = new JLabel("Графики");
        JLabel coordinateLabel = new JLabel("Координаты");
        //ButtonGroup coordinateButs = new ButtonGroup();
        JCheckBox xm = new JCheckBox("X, м");
        JCheckBox ym = new JCheckBox("Z, м");
        JCheckBox zm = new JCheckBox("Y, м");
        JLabel speedLabel = new JLabel("Проекции скорости");
        // ButtonGroup speedButs = new ButtonGroup();
        JCheckBox vx = new JCheckBox("Vx, м/с");
        JCheckBox vy = new JCheckBox("Vy, м/с");
        JCheckBox vz = new JCheckBox("Vz, м/с");
        JLabel sliderLabel = new JLabel("Сглаживание");
        JSlider slider = new JSlider();
        // JScrollPane graphScroll = new JScrollPane();
        graphBox.add(graphLabel);
//        coordinateButs.add(xm);
//        coordinateButs.add(ym);
//        coordinateButs.add(zm);
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(coordinateLabel);
        box.add(xm);
        box.add(ym);
        box.add(zm);
        box.add(speedLabel);
//        speedButs.add(vx);
//        speedButs.add(vy);
//        speedButs.add(vz);
        box.add(speedLabel);
        box.add(vx);
        box.add(vy);
        box.add(vz);
        box.add(sliderLabel);
        box.add(slider);
        graphBox.add(box);


        lHorizontal.setTopComponent(catalogBox);
        lHorizontal.setBottomComponent(textBox);
        rHorizontal.setTopComponent(tableBox);
        rHorizontal.setBottomComponent(graphBox);
        verticalSplit.setLeftComponent(lHorizontal);
        verticalSplit.setRightComponent(rHorizontal);
        //frame.add(verticalSplit);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
        contents.add(verticalSplit);
        //frame.setJMenuBar(menuBar);
        //frame.pack();
        //contents.add(frame);
        setContentPane(contents);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    filePathLabel.setText(file.getPath());
                    List<Point> points = addFromFile(file.getPath());
                    point.addAll(points);
                    String[][] outPoints = new String[points.size()][];
                    for (int i = 0; i < points.size(); i++) {
                        fileText.append(points.get(i).toString() + "\n");//add string to area

                        //  text.setLineWrap(true);
                        outPoints[i] = points.get(i).toArray();//add data on table

                    }
                    textBox.add(new JScrollPane(fileText));
                    vx.setSelected(true);
                    vy.setSelected(true);
                    vz.setSelected(true);
                    JFreeChart chart = createChart(points);
                    ChartPanel graph = new ChartPanel(chart);

                    graph.setPreferredSize(new Dimension(560, 480));
                    graphBox.add(new JScrollPane(graph));
                    //graphScroll.add(graph);

                    JTable table1 = new JTable(outPoints, points.get(0).getName());//create data table
                    tableBox.add(new JScrollPane(table1));
                    //table = table1;
                    // tableScroll.add(table1);


                }
            }
        });
//        vx.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    xm.setSelected(false);
//                    ym.setSelected(false);
//                    zm.setSelected(false);
////                    chart = createChart(point, 1);
////                    graph = new ChartPanel(chart);
////                    graph.setPreferredSize(new java.awt.Dimension(560, 480));
////                    graphScroll = new JScrollPane(graph);
////                    graphBox.add(graphScroll);
//                } else {
////                    chart = createChart(null, 1);
////                    chart.fireChartChanged();
////                    ChartPanel graph1 = new ChartPanel(chart);
////                    graphScroll = new JScrollPane(graph1);
////                    graphBox.add(graphScroll);
////
//                }
//
//
//            }
//        });
//        vy.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    xm.setSelected(false);
//                    ym.setSelected(false);
//                    zm.setSelected(false);
////                    chart = createChart(point, 1);
////                    graph = new ChartPanel(chart);
////                    graph.setPreferredSize(new java.awt.Dimension(560, 480));
////                    graphScroll = new JScrollPane(graph);v
////                    graphBox.add(graphScroll);
//                } else {
////                    chart = createChart(null, 1);
////                    chart.fireChartChanged();
////                    ChartPanel graph1 = new ChartPanel(chart);
////                    graphScroll = new JScrollPane(graph1);
////                    graphBox.add(graphScroll);
////
//                }
//
//
//            }
//        });
//        vz.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    xm.setSelected(false);
//                    ym.setSelected(false);
//                    zm.setSelected(false);
////                    chart = createChart(point, 1);
////                    graph = new ChartPanel(chart);
////                    graph.setPreferredSize(new java.awt.Dimension(560, 480));
////                    graphScroll = new JScrollPane(graph);
////                    graphBox.add(graphScroll);
//                } else {
////                    chart = createChart(null, 1);
////                    chart.fireChartChanged();
////                    ChartPanel graph1 = new ChartPanel(chart);
////                    graphScroll = new JScrollPane(graph1);
////                    graphBox.add(graphScroll);
////
//                }
//
//
//            }
//        });


    }

    public static void main(String[] args) {
        setDefaultLookAndFeelDecorated(true);
        TestTest test = new TestTest();
    }
}
