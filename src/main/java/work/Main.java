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

    public static XYDataset createDataset(final int idx, List<Point> points)//Creating DataSet for Graphics
    {
        if(points==null){
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

    public static void main(String[] args) {

    }
}