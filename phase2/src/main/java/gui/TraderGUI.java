package main.java.gui;

import main.java.controller.LoginController;
import main.java.model.account.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
import static javax.swing.JOptionPane.showMessageDialog;


public class TraderGUI {
    private AccountStorage as;
    private LoginController lc;
    private JTabbedPane MainTabbedPane;
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
    private JButton btnLoginMain;
    private JButton btnRegisterMain;
    private JTextField textField5;
    private JButton enterButton;
    private JPanel Main;
    private JPanel Home;
    private JPanel Account;
    private JButton guestButton;
    private JTextField txtRegisterUsername;
    private JTextField txtRegisterEmail;
    private JTextField txtRegisterPassword;
    private JTextField txtLoginUsername;
    private JTextField txtLoginPassword;
    private JPanel Login;
    private JPanel Register;
    private JButton btnRegister;
    private JButton btnLogin;
    private JPanel AddItems;
    private JPanel AddAdmin;
    private JPanel Request;
    private JPanel Offers;
    private JPanel Activity;
    private JPanel Browse;
    private JPanel Requests;
    private JPanel Freeze;
    private JPanel TradeThreshold;
    private JTextArea accountInformationTextArea;

    public TraderGUI() {
        //accountInformationTextArea.setEditable(false);
        //accountInformationTextArea.setText("."); // display all user information here


        MainTabbedPane.removeAll();
        MainTabbedPane.insertTab("Main", null, Main, null, 0);
        btnRegisterMain.addActionListener(e -> {
            if (MainTabbedPane.getTabCount() == 2){
                MainTabbedPane.removeTabAt(1);
            }
            MainTabbedPane.insertTab("Register", null, Register, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        btnLoginMain.addActionListener(e -> {
            if (MainTabbedPane.getTabCount() == 2){
                MainTabbedPane.removeTabAt(1);
            }
            MainTabbedPane.insertTab("Login", null, Login, null, 1);
            MainTabbedPane.setSelectedIndex(1);



        });

        guestButton.addActionListener(e -> {

        });

        signOutButton.addActionListener(e -> {
            MainTabbedPane.removeAll();
            MainTabbedPane.insertTab("Main", null, Main, null, 0);
        });


        btnLogin.addActionListener(e -> {
            //Account acc = null;
            String user = txtLoginUsername.getText();
            String pass = txtLoginPassword.getText();
            txtLoginUsername.setText(" ");
            txtLoginPassword.setText(" ");
            try {
                MainTabbedPane.removeAll();
                MainTabbedPane.insertTab("Home", null, Home, null, 0);
                Account acc = lc.login(user, pass);

                // this wall of MainTabbedPanes will be moved to a helper class: TabController
                if (as.getType(acc.getUsername()).equals("ADMIN")) {
                    MainTabbedPane.insertTab("Requests", null, Requests, null, 1);
                    MainTabbedPane.insertTab("Freeze", null, Freeze, null, 2);
                    MainTabbedPane.insertTab("Trade Threshold", null, TradeThreshold, null, 3);
                    MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 4);
                } else {
                    MainTabbedPane.insertTab("Account", null, Account, null, 1);
                    MainTabbedPane.insertTab("Activity", null, Activity, null, 2);
                    MainTabbedPane.insertTab("Browse", null, Browse, null, 3);
                    MainTabbedPane.insertTab("Offers", null, Offers, null, 4);
                    MainTabbedPane.insertTab("Request", null, Request, null, 5);
                    MainTabbedPane.insertTab("Add Items", null, AddItems, null, 6);
                }

            } catch (AccountNotFoundException accountNotFoundException) {
                accountNotFoundException.printStackTrace();
            }

            });

        btnRegister.addActionListener(e -> {
            String email = txtRegisterEmail.getText();
            String user = txtRegisterUsername.getText();
            String pass = txtRegisterPassword.getText();
            try {
                lc.register(user, pass, email);
            } catch (InvalidLoginException | UsernameInUseException | InvalidEmailAddressException |
                    EmailAddressInUseException invalidLoginException) {
                showMessageDialog(null, invalidLoginException.getMessage());
            }
        });
    }

}
