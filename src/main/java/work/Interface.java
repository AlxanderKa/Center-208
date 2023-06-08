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


public class Interface extends JFrame {
    private JPanel jPanel;
    private JButton startBut;
    private JTable table1;
    private JPanel panTable;
    private JScrollPane jScroll;

    private JTextField textField1;
    private Sourse openFile;
    JFileChooser fileChooser = new JFileChooser();

    public Interface() {
        setContentPane(jPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        startBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    openFile = new Sourse(file.getPath());
                    List<Point> points = addFromFile(file.getPath());
                    String[][] outPoints = new String[points.size()][];
                    for (int i = 0; i < points.size(); i++) {
                        outPoints[i] = points.get(i).toArray();
                    }
                    table1 = new JTable(outPoints, points.get(0).getName());
                    table1.setFillsViewportHeight(true);
                   /* try {
                        Desktop.getDesktop().open(file);

                    } catch (
                            IOException ee) {
                        ee.printStackTrace();
                    }*/
                }
//                String path = textField1.getText();
//                File file = new File(path);
//
            }
        });
    }

    public static void main(String[] args) {
        Interface form = new Interface();


    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
