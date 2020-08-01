package main.java.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextPane traderSystemAdminTextPane;
    private JButton exitButton;
    private JButton signOutButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;

    public AdminGUI() {
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("TraderGUI");
                frame.setContentPane(new TraderGUI().MainContainer);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
