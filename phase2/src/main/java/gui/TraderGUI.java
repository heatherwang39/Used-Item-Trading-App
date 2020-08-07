package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.status.StatusNotFoundException;
import main.java.model.trade.TradeNumberException;
import main.java.system2.StorageGateway;
import main.java.model.status.InvalidStatusTypeException;
import main.java.presenter.*;

import javax.swing.*;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * TraderGUI is a controller class that manages the entire user interface process.
 *
 * @author Charles Wang
 * @version %I%, %G%
 * @since Phase 2
 */




public class TraderGUI {
    private JTabbedPane MainTabbedPane;
    public JPanel MainContainer;
    private JButton signOutButton;
    private JButton exitButton;
    private JTextPane traderSystemTextPane;
    private JTextField txtUsernameOutput;
    private JTextField txtEmailOutput;
    private JTextField txtInventoryInput;
    private JTextField txtWishlistInput;
    private JTextField txtRequestItemInput;
    private JRadioButton rbtnTempTrade;
    private JRadioButton rbtnPermTrade;
    private JButton btnRequest;
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
    private JPanel Threshold;
    private JTextField txtAdminPromoteInput;
    private JButton btnPromote;
    private JScrollPane scrlInventoryOutput;
    private JScrollPane scrlWishlistOutput;
    private JTextArea txtAreaInventoryOutput;
    private JTextArea txtAreaWishlistOutput;
    private JButton btnInventoryRequest;
    private JButton btnWishlistAddition;
    private JPanel Messages;
    private JTextArea txtAreaActivityTradeOutput;
    private JTextArea txtAreaActivityPartnerOutput;
    private JTextArea txtAreaDescriptionInput;
    private JTextField txtTagsInput;
    private JTextArea txtAreaBrowseListingsOutput;
    private JTextArea txtAreaFreezeUsers;
    private JTextField txtFreezeUser;
    private JRadioButton rbtnFreezeUser;
    private JRadioButton rbtnIgnoreUser;
    private JButton btnFreezeEnter;
    private JTextField textField1;
    private JRadioButton rbtnAcceptOffer;
    private JRadioButton rbtnDenyOffer;
    private JButton btnOfferEnter;
    private JTextArea accountInformationTextArea;

    private LoginController loginController;
    private ItemStorage itemStorage;
    private AccountStorage accountStorage;
    private TradePresenter tradePresenter;
    private RequestController requestController;
    private ActivityController activityController;
    private AddAdminController addAdminController;
    private FreezeController freezeController;
    private OffersController offersController;
    private RequestsController requestsController;


    public TraderGUI(StorageGateway storageGateway) {
        MainTabbedPane.removeAll();
        try {
            initializeLogin(storageGateway);
        } catch (IOException | ClassNotFoundException e) {
            showMessageDialog(null, e.getStackTrace());
        }
    }


    private void initializeLogin(StorageGateway storageGateway) throws IOException, ClassNotFoundException {

        loginController = new LoginController(storageGateway);
        itemStorage = new ItemStorage(null);
        accountStorage = new AccountStorage(null);
        tradePresenter = new TradePresenter(storageGateway);


        final String[] username = new String[1];
        final String[] password = new String[1];
        String user = username[0];
        String pass = password[0];

        MainTabbedPane.insertTab("Main", null, Main, null, 0);

        // set all appropriate text fields and area to not be editable here
        txtUsernameOutput.setEditable(false);
        txtEmailOutput.setEditable(false);


        // this is so users cannot select two radio buttons simultaneously
        ButtonGroup tradeTypeButtonGroup = new ButtonGroup();
        tradeTypeButtonGroup.add(rbtnPermTrade);
        tradeTypeButtonGroup.add(rbtnTempTrade);

        ButtonGroup freezeButtonGroup = new ButtonGroup();
        freezeButtonGroup.add(rbtnFreezeUser);
        freezeButtonGroup.add(rbtnIgnoreUser);



        btnRegisterMain.addActionListener(e -> {
            if (MainTabbedPane.getTabCount() == 2) {
                MainTabbedPane.removeTabAt(1);
            }
            MainTabbedPane.insertTab("Register", null, Register, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        btnLoginMain.addActionListener(e -> {
            if (MainTabbedPane.getTabCount() == 2) {
                MainTabbedPane.removeTabAt(1);
            }
            MainTabbedPane.insertTab("Login", null, Login, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        guestButton.addActionListener(e -> {
            if (MainTabbedPane.getTabCount() == 2) {
                MainTabbedPane.removeTabAt(1);
            }
            MainTabbedPane.insertTab("Browse", null, Browse, null, 1);
        });

        signOutButton.addActionListener(e -> {
            MainTabbedPane.removeAll();
            MainTabbedPane.insertTab("Main", null, Main, null, 0);
        });

        btnLogin.addActionListener(e -> {
            username[0] = txtLoginUsername.getText();
            password[0] = txtLoginPassword.getText();
            txtLoginUsername.setText("");
            txtLoginPassword.setText("");

            try {
                LoginAccount acc = loginController.login(user, pass);
                ActivityController activityController = new ActivityController(storageGateway, user);
                AddAdminController addAdminController = new AddAdminController(storageGateway, user);
                RequestController requestController = new RequestController(storageGateway, user);
                FreezeController freezeController = new FreezeController(storageGateway, user);


                if (acc.getType().equals("USER")) {
                    MainTabbedPane.removeAll();

                    // done
                    MainTabbedPane.insertTab("Home", null, Home, null, 0);


                    // done but not tested
                    MainTabbedPane.insertTab("Account", null, Account, null, 1);
                    txtUsernameOutput.setText(acc.getUsername());
                    txtEmailOutput.setText(acc.getEmailAddress());
                    for (int i = 0; i < itemStorage.getVerifiedInventory(user).size(); i++){
                        txtAreaInventoryOutput.append(itemStorage.getVerifiedInventory(user).get(i).getName() + "\n");
                    }
                    for (int i = 0; i < itemStorage.getWishlist(user).size(); i++){
                        txtAreaWishlistOutput.append(itemStorage.getWishlist(user).get(i).getName() + "\n");
                    }


                    // logic done but needs aesthetic change (use method in TradePresenter)
                    MainTabbedPane.insertTab("Activity", null, Activity, null, 2);
                    List<List<Integer>> tradeList = activityController.recentItemsTraded();
                    List<String> partnerList = activityController.frequentTradingPartners();
                    String tradeString = "";
                    for (List<Integer> integers : tradeList) {
                        for (Integer integer : integers) {
                            tradeString += ", " + integer;
                            txtAreaActivityTradeOutput.append(tradeString + "\n");
                        }
                    }
                    for (String s : partnerList) {
                        txtAreaActivityPartnerOutput.append(s + "\n");
                    }

                    // Done but not tested
                    MainTabbedPane.insertTab("Browse", null, Browse, null, 3);
                    List<HashMap<String, String>> listingList = itemStorage.getVerifiedItemsData();
                    for (int i = 0; i < listingList.size(); i++){
                        for (String str : listingList.get(i).keySet()){
                            txtAreaBrowseListingsOutput.append(str + listingList.get(i).get(str) + "\n");
                        }
                    }

                    // Not done
                    MainTabbedPane.insertTab("Offers", null, Offers, null, 4);
//                    List<HashMap<String, List<String>>> offerList = offersController.getOffers();
//                    for (HashMap<String, List<String>> stringListHashMap : offerList) {
//                        for (String str : stringListHashMap.keySet()) {
//
//                        }
//                    }

                    // Partially done
                    MainTabbedPane.insertTab("Request", null, Request, null, 5);

                    // Partially done
                    MainTabbedPane.insertTab("Add Items", null, AddItems, null, 6);

                    // Not done
                    MainTabbedPane.insertTab("Messages", null, Messages, null, 7);

                } else if (acc.getType().equals("ADMIN")) {
                    MainTabbedPane.removeAll();
                    // Done
                    MainTabbedPane.insertTab("Home", null, Home, null, 0);

                    // Not done
                    MainTabbedPane.insertTab("Requests", null, Requests, null, 1);

                    // Done but not tested
                    MainTabbedPane.insertTab("Freeze", null, Freeze, null, 2);
                    List<List<String>> freezeUserList = freezeController.showUsersToFreeze(3, 3 ,3); // THESE ARE TEMPORARY VALUES!!
                    for (List<String> strings : freezeUserList) {
                        txtAreaFreezeUsers.append(strings.get(0) + strings.get(1) + "\n");
                    }

                    // waiting to see how thresholds are stored first.
                    MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 3);

                    // done but not tested
                    MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 4);
                } else {
                    showMessageDialog(null, "Your account did not match any credentials in " +
                            "our system.");
                }
            } catch (AccountNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (TradeNumberException tradeNumberException) {
                tradeNumberException.printStackTrace();
            } catch (ItemNotFoundException itemNotFoundException) {
                itemNotFoundException.printStackTrace();
            }
        });

        btnRegister.addActionListener(e -> {
            String registerEmail = txtRegisterEmail.getText();
            String registerUser = txtRegisterUsername.getText();
            String registerPass = txtRegisterPassword.getText();
            try {
                loginController.register(registerUser, registerPass, registerEmail);
                showMessageDialog(null, "Welcome to the system, " + registerUser + "!");
            } catch (UsernameInUseException | InvalidEmailAddressException | EmailAddressInUseException |
                    InvalidStatusTypeException | InvalidUsernameException | InvalidPasswordException invalidLoginException) {
                showMessageDialog(null, invalidLoginException.getMessage());
            } catch (IOException ioException) {
                showMessageDialog(null, ioException.getMessage() + "\n" +
                        Arrays.toString(ioException.getStackTrace()));
            } finally {
            }
        });

        btnInventoryRequest.addActionListener(e -> {

        });

        btnWishlistAddition.addActionListener(e -> {
            String itemID = txtWishlistInput.getText();
            try {
                itemStorage.addWishList(user, Integer.parseInt(itemID));
            } catch (ItemNotFoundException itemNotFoundException) {
                itemNotFoundException.printStackTrace();
            }
        });

        btnRequest.addActionListener(e -> {
            boolean perm = false;
            if (rbtnPermTrade.isSelected()){
                perm = true;
            } else if(rbtnTempTrade.isSelected()){
                perm = false;
            } else{
                showMessageDialog(null, "Please select a type of trade for this request!");
            }
            //requestController.createRequest();
        });

        btnOfferEnter.addActionListener(e -> {

        });


        int currUserNum = 0;
        btnFreezeEnter.addActionListener(e -> {
            List<List<String>> freezeUserList = freezeController.showUsersToFreeze(3, 3 ,3); // THESE ARE TEMPORARY VALUES!!
            txtFreezeUser.setText(freezeUserList.get(currUserNum).get(0)); // this prints out the user's name
            if (rbtnFreezeUser.isSelected()){
                try {
                    freezeController.freezeUser(freezeUserList.get(currUserNum).get(0));
                } catch (InvalidStatusTypeException invalidStatusTypeException) {
                    invalidStatusTypeException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            /*else if (rbtnIgnoreUser.isSelected()) {
                try {
                freezeController.unfreezeUser(freezeUserList.get(currUserNum).get(0));
                // I am confused about what should happen here if the admin decides to let the user free from
                // the system's recommendation to freeze them. Do we just keep that user there (the list) forever
                // until the admin decides to freeze em? In any case, unfreezing them isnt the correct move

                } catch (StatusNotFoundException statusNotFoundException) {
                    statusNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                 }
                }
                */
        });

        btnPromote.addActionListener(e -> {

            String usernameAdmin = txtAdminPromoteInput.getText();
            try {
                LoginAccount userAdmin = accountStorage.getAccount(usernameAdmin);
                addAdminController.addAdmin(usernameAdmin, userAdmin.getPassword(), userAdmin.getEmailAddress());
            } catch (AccountNotFoundException accountNotFoundException) {
                accountNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InvalidEmailAddressException invalidEmailAddressException) {
                invalidEmailAddressException.printStackTrace();
            } catch (InvalidPasswordException invalidPasswordException) {
                invalidPasswordException.printStackTrace();
            } catch (EmailAddressInUseException emailAddressInUseException) {
                emailAddressInUseException.printStackTrace();
            } catch (InvalidUsernameException invalidUsernameException) {
                invalidUsernameException.printStackTrace();
            } catch (UsernameInUseException usernameInUseException) {
                usernameInUseException.printStackTrace();
            }
        });

    }


}
