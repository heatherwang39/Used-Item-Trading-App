package main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TraderGUI extends JDialog{
    public JPanel MainContainer;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;

    public TraderGUI() {
        setContentPane(MainContainer);
        setModal(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Register");
                frame.setContentPane(new RegisterGUI().MainContainer);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Menu");
                frame.setContentPane(new LoginGUI().MainContainer);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                // have a pop-up here which takes in username and password
                // if user is admin: open admingui
                // if user is regular: open logingui

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TraderGUI");
        frame.setContentPane(new TraderGUI().MainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
