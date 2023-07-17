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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static work.FileParser.addFromFile;
import static work.FileParser.createDataset;


public class TestUI extends JFrame {
    String filename;
    JTabbedPane tabs;
    JMenuItem open;
    int a;

    private JFreeChart createChart(List<Point> points) {


        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Графики"));


        XYSplineRenderer r0 = new XYSplineRenderer(8);
        r0.setSeriesShapesVisible(5, true);


        XYSplineRenderer r1 = new XYSplineRenderer();
        r1.setPrecision(8);
        r1.setSeriesShapesVisible(0, true);

        XYSplineRenderer r2 = new XYSplineRenderer(8);

        r2.setSeriesShapesVisible(0, true);
        r2.setSeriesPaint(0, Color.orange);
        r2.setSeriesStroke(0, new BasicStroke(2.5f));


        XYDataset dataset0 = createDataset(-1, points);
        XYDataset dataset1 = createDataset(-2, points);
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


        plot.setGap(10.0);
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        return new JFreeChart(
                "Графики",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true
        );

    }

    public TestUI() {
        tabs = new JTabbedPane(
                JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        a = 1;


        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        open = new JMenuItem("Открыть");
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
        setJMenuBar(menuBar);
        addInputListeners();
        getContentPane().add(tabs);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void addInputListeners() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                List<Point> point = new ArrayList<>();


                Box textBox = new Box(BoxLayout.Y_AXIS);
                Box tableBox = new Box(BoxLayout.Y_AXIS);
                Box graphBox = new Box(BoxLayout.Y_AXIS);
                final JSplitPane verticalSplit = new JSplitPane();
                verticalSplit.setDividerSize(8);
                verticalSplit.setDividerLocation(150);
                JSplitPane rHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
                JSplitPane lHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
                rHorizontal.setDividerLocation(100);
                lHorizontal.setDividerLocation(100);


                JLabel fileLabel = new JLabel("Файл");
                JLabel filePathLabel = new JLabel();
                // JScrollPane fileScroll = new JScrollPane();
                JTextArea fileText = new JTextArea();
                textBox.add(fileLabel);
                textBox.add(new JScrollPane(filePathLabel));


                JLabel tableLabel = new JLabel("Таблица");
                ;
                tableBox.add(tableLabel);


                JLabel graphLabel = new JLabel("Графики");
                JLabel coordinateLabel = new JLabel("Координаты");

                JCheckBox xm = new JCheckBox("X, м");
                JCheckBox ym = new JCheckBox("Z, м");
                JCheckBox zm = new JCheckBox("Y, м");
                JLabel speedLabel = new JLabel("Проекции скорости");

                JCheckBox vx = new JCheckBox("Vx, м/с");
                JCheckBox vy = new JCheckBox("Vy, м/с");
                JCheckBox vz = new JCheckBox("Vz, м/с");
                JLabel sliderLabel = new JLabel("Сглаживание");
                JSlider slider = new JSlider();

                graphBox.add(graphLabel);

                Box box = new Box(BoxLayout.X_AXIS);
                box.add(coordinateLabel);
                box.add(xm);
                box.add(ym);
                box.add(zm);
                box.add(speedLabel);
                ;
                box.add(speedLabel);
                box.add(vx);
                box.add(vy);
                box.add(vz);
                box.add(sliderLabel);
                box.add(slider);
                graphBox.add(box);
                int returnValue = fileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file1 = fileChooser.getSelectedFile();
                    filename = JOptionPane.showInputDialog(
                            TestUI.this,
                            "<html><h2>Введите имя файла");
                    if(filename.isEmpty()){
                        filename = String.format("Траектория %d", a);
                        a++;
                    }

                    filePathLabel.setText(file1.getPath());
                    List<Point> points = addFromFile(file1.getPath());
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


                    JTable table1 = new JTable(outPoints, points.get(0).getName());//create data table
                    tableBox.add(new JScrollPane(table1));

                }
                rHorizontal.setTopComponent(tableBox);
                rHorizontal.setBottomComponent(graphBox);
                verticalSplit.setLeftComponent(textBox);
                verticalSplit.setRightComponent(rHorizontal);
                tabs.add(filename, verticalSplit);


            }
        });

    }


    public static void main(String[] args) {
        TestUI ui = new TestUI();
    }
}
