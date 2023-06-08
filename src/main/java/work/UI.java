package work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class UI extends JFrame{
    private JButton startButton;
    private JPanel jPanel;
    JFileChooser fileChooser = new JFileChooser();
    public UI() {
        setContentPane(jPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        startButton.addActionListener(new ActionListener() {
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

            }
        });
    }

    public static void main(String[] args) {
        UI ui = new UI();
    }




}
