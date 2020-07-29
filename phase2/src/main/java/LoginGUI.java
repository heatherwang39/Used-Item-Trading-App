package main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class LoginGUI {
    private JTabbedPane tabbedPane1;
    public JPanel MainContainer;
    private JButton signOutButton;
    private JButton exitButton;
    private JTextPane traderSystemTextPane;
    private JTextField textField2;
    private JTextField txt;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JRadioButton temporaryTradeRadioButton;
    private JRadioButton permanentTradeRadioButton;
    private JButton requestButton;
    private JTextArea accountInformationTextArea;

    public LoginGUI() {
        accountInformationTextArea.setEditable(false);
        accountInformationTextArea.setText("."); // display all user information here

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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


    }
}
