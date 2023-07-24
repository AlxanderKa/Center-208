package work;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static work.TestTest.createChart;

public class FileParser extends JFrame {
    //Scanning data from a file and creating a set of trajectory points
    public static List<Point> addFromFile(String path) {
        ArrayList<Point> points = new ArrayList<>();
        // Path path = Path.of(s);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path), "UTF-8"));
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    line = line.trim().replace(",", ".");
                    String[] dotdata = line.split("\\s+");
                    Point dot = new Point(Double.parseDouble(dotdata[0]), Double.parseDouble(dotdata[1]),
                            Double.parseDouble(dotdata[2]), Double.parseDouble(dotdata[3]),
                            Double.parseDouble(dotdata[4]), Double.parseDouble(dotdata[5]),
                            Double.parseDouble(dotdata[6]));

                    points.add(dot);


                }

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("no file in/out");
        }
        return points;
    }

    //Creating DataSet from trajectory List to data rot Graph
    public static XYDataset createDataset(final int idx, List<Point> points) {
        if (points == null) {
            return null;
        }


        final XYSeries series1 = new XYSeries("Vx");
        final XYSeries series2 = new XYSeries("Vy");
        final XYSeries series3 = new XYSeries("Vz");
        final XYSeries series4 = new XYSeries("B");
        final XYSeries series5 = new XYSeries("L");
        final XYSeries series6 = new XYSeries("H");

        for (int i = 0; i < points.size(); i++) {
            series1.add(points.get(i).getT(), points.get(i).getVx());
            series2.add(points.get(i).getT(), points.get(i).getVy());
            series3.add(points.get(i).getT(), points.get(i).getVz());
            series4.add(points.get(i).getT(), points.get(i).getB());
            series5.add(points.get(i).getT(), points.get(i).getL());
            series6.add(points.get(i).getT(), points.get(i).getH());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        switch (idx) {
            case -1:
                dataset.addSeries(series1);
                dataset.addSeries(series2);
                dataset.addSeries(series3);
                break;
            case -2:
                dataset.addSeries(series4);
                dataset.addSeries(series5);
                dataset.addSeries(series6);
                break;
            case -3:

            case 1:
                dataset.addSeries(series1);
                break;
            case 2:
                dataset.addSeries(series2);
                break;
            case 3:
                dataset.addSeries(series3);
                break;
            case 4:
                dataset.addSeries(series4);
                break;
            case 5:
                dataset.addSeries(series5);
                break;
            case 6:
                dataset.addSeries(series6);
                break;
        }

        return dataset;
    }

    //Creating text field with data from file
    public static Box createText(String path) {
        JTextArea fileText = new JTextArea();
        Box fileBox = new Box(BoxLayout.Y_AXIS);
        List<Point> points = addFromFile(path);
        for (int i = 0; i < points.size(); i++) {
            fileText.append(points.get(i).toString() + "\n");
        }
        JLabel fileLabel = new JLabel("Файл");
        JLabel filePathLabel = new JLabel();
        fileBox.add(fileLabel);
        fileBox.add(new JScrollPane(filePathLabel));
        filePathLabel.setText(path);
        fileBox.add(new JScrollPane(fileText));


        return fileBox;
    }

    //Creating table with data from file
    public static Box createTable(String path) {
        Box tableBox = new Box(BoxLayout.Y_AXIS);
        List<Point> points = addFromFile(path);
        String[][] outPoints = new String[points.size()][];
        for (int i = 0; i < points.size(); i++) {
            outPoints[i] = points.get(i).toArray();//add data on table

        }
        JLabel tableLabel = new JLabel("Таблица");
        tableBox.add(tableLabel);
        JTable table = new JTable(outPoints, points.get(0).getName());
        tableBox.add(new JScrollPane(table));
        return tableBox;
    }

    //Creating graph with data from file
    public static Box createGraph(String path) {
        Box graphBox = new Box(BoxLayout.Y_AXIS);
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
        List<Point> points = addFromFile(path);
        JFreeChart chart = createChart(points);
        ChartPanel graph = new ChartPanel(chart);

        graph.setPreferredSize(new Dimension(560, 480));
        graphBox.add(new JScrollPane(graph));
        return graphBox;
    }

    //Creating text field, table, graph with data from file
    public static Component[] createComponent(String path) {
        Component[] components = new Component[4];
        List<Point> points = addFromFile(path);
        Box fileBox = new Box(BoxLayout.Y_AXIS);
        Box tableBox = new Box(BoxLayout.Y_AXIS);
        Box graphBox = new Box(BoxLayout.Y_AXIS);


        JTextArea fileText = new JTextArea();
        String[][] outPoints = new String[points.size()][];
        for (int i = 0; i < points.size(); i++) {
            fileText.append(points.get(i).toString() + "\n");

            outPoints[i] = points.get(i).toArray();
        }
        JLabel fileLabel = new JLabel("Файл");
        JLabel filePathLabel = new JLabel();
        fileBox.add(fileLabel);
        fileBox.add(new JScrollPane(filePathLabel));
        filePathLabel.setText(path);
        fileBox.add(new JScrollPane(fileText));

        JTable table = new JTable(outPoints, points.get(0).getName());
        tableBox.add(new JScrollPane(table));

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
        box.add(speedLabel);
        box.add(vx);
        box.add(vy);
        box.add(vz);
        box.add(sliderLabel);
        box.add(slider);
        graphBox.add(box);
        JFreeChart chart = createChart(points);
        ChartPanel graph = new ChartPanel(chart);
        graph.setPreferredSize(new Dimension(560, 480));
        graphBox.add(new JScrollPane(graph));

        components[0] = fileBox;
        components[1] = tableBox;
        components[2] = graphBox;


        return components;
    }
}

