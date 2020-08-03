package main.java.gui;
import javax.swing.*;

public class TraderGUI extends JDialog{
    public JPanel MainContainer;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;

    public TraderGUI() {
        setContentPane(MainContainer);
        setModal(true);

        registerButton.addActionListener(e -> {
            JFrame frame = new JFrame("Register");
            frame.setContentPane(new RegisterGUI().MainContainer);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
        loginButton.addActionListener(e -> {
            JFrame frame = new JFrame("Menu");
            frame.setContentPane(new LoginGUI().MainContainer);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            // have a pop-up here which takes in username and password
            // if user is admin: open admingui
            // if user is regular: open logingui
        });

        exitButton.addActionListener(e -> dispose());
    }
}
