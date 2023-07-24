package work;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
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
import java.io.File;
import java.util.*;
import java.util.List;

import static work.FileParser.*;


public class TestTest extends JFrame {

    String filename;
    JTabbedPane tabs;
    JMenuItem open;
    int tabbedCount;
    HashMap<String, String> openFiles = new HashMap<>();
    HashMap<String, Integer> countOpenFile = new HashMap<>();
    ArrayList<String> recentLinks = new ArrayList<>();

    public static JFreeChart createChart(List<Point> points) {


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

    public static void putLink(List<String> arr, String link) {
        if (arr.isEmpty()) {
            arr.add(link);
        } else {
            if (arr.contains(link)) {
                int n = arr.indexOf(link);
                arr.remove(n);
                arr.add(0, link);


            } else {
                arr.add(0, link);
            }
        }

    }

    public JSplitPane createTab() {
        JFileChooser fileChooser = new JFileChooser();
        Component[] components;

        final JSplitPane verticalSplit = new JSplitPane();
        verticalSplit.setDividerSize(8);
        verticalSplit.setDividerLocation(150);
        JSplitPane rHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        JSplitPane lHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        rHorizontal.setDividerLocation(100);
        lHorizontal.setDividerLocation(100);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (countOpenFile.containsKey(fileChooser.getSelectedFile().getPath())) {
                int res = JOptionPane.showConfirmDialog(TestTest.this,
                        "Файл уже открыт, хотите открыть еще раз?",
                        "Файл уже открыт",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    File file1 = fileChooser.getSelectedFile();
                    filename = JOptionPane.showInputDialog(
                            TestTest.this,
                            "<html><h2>Введите имя файла");
                    if (filename.isEmpty()) {
                        filename = String.format("Траектория %d", tabbedCount);
                        tabbedCount++;
                    }
                    countOpenFile.put(file1.getPath(), countOpenFile.get(file1.getPath()) + 1);
                    putLink(recentLinks, file1.getPath());
                    components = createComponent(file1.getPath());


                    verticalSplit.setLeftComponent(components[0]);
                    rHorizontal.setTopComponent(components[1]);
                    rHorizontal.setBottomComponent(components[2]);
                    verticalSplit.setRightComponent(rHorizontal);
                    tabs.add(filename, verticalSplit);

                    return verticalSplit;

                }
                if (res == JOptionPane.NO_OPTION) {
                    createTab();
                }
            } else {
                File file1 = fileChooser.getSelectedFile();
                filename = JOptionPane.showInputDialog(
                        TestTest.this,
                        "<html><h2>Введите имя файла");
                if (filename.isEmpty()) {
                    filename = String.format("Траектория %d", tabbedCount);
                    tabbedCount++;
                }
                openFiles.put(file1.getPath(), filename);
                countOpenFile.put(file1.getPath(), 1);


                putLink(recentLinks, file1.getPath());
                components = createComponent(file1.getPath());
                verticalSplit.setLeftComponent(components[0]);
                rHorizontal.setTopComponent(components[1]);
                rHorizontal.setBottomComponent(components[2]);
                verticalSplit.setRightComponent(rHorizontal);
                tabs.add(filename, verticalSplit);
                return verticalSplit;


            }
        }
        return null;
    }

    private TestTest() {
        ImageIcon icon = new ImageIcon();
        tabs = new JTabbedPane(
                JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedCount = 1;


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
                String name= tabs.getTitleAt(tabs.getSelectedIndex());
                 String[] path = new String[1];
                    openFiles.forEach((k,v)->{
                        if(v.equals(name)){
                            path[0] =k;
                        }
                    });
                    if(countOpenFile.get(path[0])>1){
                        countOpenFile.put(path[0], countOpenFile.get(path[0])-1);
                    }else {
                        openFiles.remove(path[0]);
                        countOpenFile.remove(path[0]);
                    }

                tabs.remove(tabs.getSelectedComponent());


            }
        });
        closeAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFiles.clear();
                countOpenFile.clear();
                tabs.removeAll();
                tabbedCount = 1;
            }
        });
        openLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] items = recentLinks.stream().toArray(String[]::new);
                Object result = JOptionPane.showInputDialog(
                        TestTest.this,
                        "Выберете недавний файл",
                        "Выбор файла",
                        JOptionPane.QUESTION_MESSAGE, icon, items, items[0]);
                Component[] components = new Component[3];
                if(countOpenFile.containsKey(result.toString())){
                    int res = JOptionPane.showConfirmDialog(TestTest.this,
                            "Файл уже открыт, хотите открыть его еще раз?",
                            "Файл уже открыт",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if(res==JOptionPane.YES_OPTION){
                        final JSplitPane verticalSplit = new JSplitPane();
                        verticalSplit.setDividerSize(8);
                        verticalSplit.setDividerLocation(150);
                        JSplitPane rHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
                        JSplitPane lHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
                        rHorizontal.setDividerLocation(100);
                        lHorizontal.setDividerLocation(100);

                        components = createComponent(result.toString());
                        verticalSplit.setLeftComponent(components[0]);
                        rHorizontal.setTopComponent(components[1]);
                        rHorizontal.setBottomComponent(components[2]);
                        verticalSplit.setRightComponent(rHorizontal);
                        filename = JOptionPane.showInputDialog(
                                TestTest.this,
                                "<html><h2>Введите имя файла");
                        if (filename.isEmpty()) {
                            filename = String.format("Траектория %d", tabbedCount);
                            tabbedCount++;
                        }
                        tabs.add(filename, verticalSplit);
                    }
                }




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

                JSplitPane verticalSplit = createTab();
                if (verticalSplit != null) {
                    tabs.add(filename, verticalSplit);
                }

            }
        });

    }


    public static void main(String[] args) {
        TestTest test = new TestTest();
    }
}
