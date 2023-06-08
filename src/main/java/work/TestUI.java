package work;

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

    public TestUI() {
        Box contents = new Box(BoxLayout.Y_AXIS);
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
                        text.append(points.get(i).toString()+"\n");
                      //  text.setLineWrap(true);
                        outPoints[i] = points.get(i).toArray();

                    }
                    JTable table1 = new JTable(outPoints, points.get(0).getName());
                    contents.add(new JScrollPane(table1));
                    contents.add(new JScrollPane(text));
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
