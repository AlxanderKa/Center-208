package work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame {
    private JPanel jPanel;
    private JButton startBut;
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
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (
                            IOException ee) {
                        ee.printStackTrace();
                    }
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




}
