package work;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


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

    public static void main(String[] args) {


    }
}