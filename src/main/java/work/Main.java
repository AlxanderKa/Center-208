package work;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {
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
                    line = line.strip().replace(",", ".");
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
    public static XYDataset createDataset(final int idx, List<Point> points)//Creating DataSet for Graphics
    {


        final XYSeries series1 = new XYSeries("Vx");
        final XYSeries series2 = new XYSeries("Vy");
        final XYSeries series3 = new XYSeries("Vz");
        for (int i = 0; i < points.size(); i++) {
            series1.add(points.get(i).getT(), points.get(i).getVx());
            series2.add(points.get(i).getT(), points.get(i).getVy());
            series3.add(points.get(i).getT(), points.get(i).getVz());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        if (idx == -1) {
            dataset.addSeries(series1);
            dataset.addSeries(series2);
            dataset.addSeries(series3);
        } else if (idx == 0) {
            dataset.addSeries(series1);
        } else if (idx == 1) {
            dataset.addSeries(series2);
        } else if (idx == 2) {
            dataset.addSeries(series3);
        }
        return dataset;
    }

    public static void main(String[] args) {

    }
}