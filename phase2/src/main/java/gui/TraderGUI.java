package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.trade.TradeNumberException;
import main.java.system2.StorageGateway;
import main.java.model.status.InvalidStatusTypeException;
import main.java.presenter.*;

import javax.swing.*;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
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
    private JTextField txtAdminPasswordInput;
    private JButton btnAddAdmin;
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
    private JTextArea txtAreaFrozenUsers;
    private JTextField txtFrozenUser;
    private JRadioButton rbtnUnfreezeUser;
    private JRadioButton rbtnIgnoreUser;
    private JButton btnFreezeEnter;
    private JTextField txtOffersOutput;
    private JRadioButton rbtnAcceptOffer;
    private JRadioButton rbtnDenyOffer;
    private JButton btnOfferEnter;
    private JTextField txtRequestedItemsInput;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea txtAreaLoggingOutput;
    private JTextArea txtAreaRequestsOutput;
    private JRadioButton rbtnAcceptRequest;
    private JRadioButton rbtnDenyRequest;
    private JButton btnRequestsEnter;
    private JTextField txtRequestsOutput;
    private JTextArea txtAreaOffersOutput;
    private JTabbedPane MessagesTabbedPane;
    private JTextField txtMessageRecipientInput;
    private JButton btnMessageUser;
    private JButton btnMessageAdmin;
    private JTextArea txtAreaMessageUserInput;
    private JTextArea txtAreaMessageAdminInput;
    private JTextArea txtAreaMessagesIncomingOutput;
    private JTextArea txtMessagesSentOutput;
    private JTextField txtMessageUserTitleInput;
    private JTextField txtMessageAdminTitleInput;
    private JTextField txtAdminEmailInput;
    private JTextField txtAdminUsernameInput;
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
    private AccountTabController accountTabController;
    private AddItemsController addItemsController;
    private BrowseController browseController;
    private MessageController messageController;



    private String user;
    private int currUserIndex = 0;

    public TraderGUI(StorageGateway storageGateway) {
        MainTabbedPane.removeAll();
        try {
            initializeLogin(storageGateway);
        } catch (IOException | ClassNotFoundException e) {
            showMessageDialog(null, e.getStackTrace());
        }
        currUserIndex = 0; // testing smth

    }


    private void initializeLogin(StorageGateway storageGateway) throws IOException, ClassNotFoundException {

        loginController = new LoginController(storageGateway);
        itemStorage = new ItemStorage();
        accountStorage = new AccountStorage();
        tradePresenter = new TradePresenter(storageGateway);
        freezeController = new FreezeController(storageGateway);
        browseController = new BrowseController(storageGateway);


        MainTabbedPane.insertTab("Main", null, Main, null, 0);

        // set all appropriate text fields and area to not be editable here
        txtUsernameOutput.setEditable(false);
        txtEmailOutput.setEditable(false);


        // this is so users cannot select two radio buttons simultaneously
        ButtonGroup tradeTypeButtonGroup = new ButtonGroup();
        tradeTypeButtonGroup.add(rbtnPermTrade);
        tradeTypeButtonGroup.add(rbtnTempTrade);

        ButtonGroup freezeButtonGroup = new ButtonGroup();
        freezeButtonGroup.add(rbtnUnfreezeUser);
        freezeButtonGroup.add(rbtnIgnoreUser);

        ButtonGroup offersButtonGroup = new ButtonGroup();
        offersButtonGroup.add(rbtnAcceptOffer);
        offersButtonGroup.add(rbtnDenyOffer);

        ButtonGroup requestsButtonGroup = new ButtonGroup();
        requestsButtonGroup.add(rbtnAcceptRequest);
        requestsButtonGroup.add(rbtnDenyRequest);



        btnRegisterMain.addActionListener(e -> {
            tabCleaner();
            MainTabbedPane.insertTab("Register", null, Register, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        btnLoginMain.addActionListener(e -> {
            tabCleaner();
            MainTabbedPane.insertTab("Login", null, Login, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        guestButton.addActionListener(e -> {
            tabCleaner();
            MainTabbedPane.insertTab("Browse", null, Browse, null, 1);

        });

        signOutButton.addActionListener(e -> {
            MainTabbedPane.removeAll();
            MainTabbedPane.insertTab("Main", null, Main, null, 0);
        });

        btnLogin.addActionListener(e -> {
            user = txtLoginUsername.getText();
            String pass = txtLoginPassword.getText();

            try {
                activityController = new ActivityController(storageGateway, user);
                addAdminController = new AddAdminController(storageGateway, user);
                requestController = new RequestController(storageGateway, user);
                accountTabController = new AccountTabController(storageGateway, user);
                addItemsController = new AddItemsController(storageGateway, user);
                messageController = new MessageController(storageGateway, user);

                if (loginController.login(user, pass).equals("USER")) {
                    MainTabbedPane.removeAll();

                    // done
                    MainTabbedPane.insertTab("Home", null, Home, null, 0);

                    // done but not tested
                    MainTabbedPane.insertTab("Account", null, Account, null, 1);
                    txtUsernameOutput.setText(user);
                    txtEmailOutput.setText(accountTabController.getEmailAddress()); // this is where the program soft-crashes
                    loginController.displayUserInventory(user, txtAreaInventoryOutput);
                    loginController.displayUserWishlist(user, txtAreaWishlistOutput);

                    // logic done but needs aesthetic change (use method in TradePresenter)
                    MainTabbedPane.insertTab("Activity", null, Activity, null, 2);
                    activityController.displayTradeActivity(user, txtAreaActivityTradeOutput);
                    activityController.displayPartnerActivity(user, txtAreaActivityPartnerOutput);

                    // Done but not tested
                    MainTabbedPane.insertTab("Browse", null, Browse, null, 3);
                    browseController.displayListings(txtAreaBrowseListingsOutput);

                    // Done but not tested
                    MainTabbedPane.insertTab("Offers", null, Offers, null, 4);
                    offersController.displayOffers(user, txtAreaOffersOutput);

                    // Partially done
                    MainTabbedPane.insertTab("Request", null, Request, null, 5);

                    // Done but not tested
                    MainTabbedPane.insertTab("Add Items", null, AddItems, null, 6);

                    // Partially done; need message presenter
                    MainTabbedPane.insertTab("Messages", null, Messages, null, 7);
                    List<HashMap<String, String>> incomingMessagesList = messageController.getInbox();
                    List<HashMap<String, String>> outgoingMessagesList = messageController.getOutbox();

                    for (HashMap<String, String> stringHashMap : incomingMessagesList) {

                    }

                    for (HashMap<String, String> stringHashMap : outgoingMessagesList) {

                    }


                } else if (loginController.login(user, pass).equals("ADMIN")) {
                    MainTabbedPane.removeAll();
                    // Done
                    MainTabbedPane.insertTab("Home", null, Home, null, 0);

                    // partially done
                    MainTabbedPane.insertTab("Requests", null, Requests, null, 1);
                    requestsController.displayRequests(txtAreaRequestsOutput);

                    // Done but not tested
                    MainTabbedPane.insertTab("Freeze", null, Freeze, null, 2);
                    List<List<String>> freezeUserList = freezeController.showAllFrozenUsers(3, 3 ,3); // THESE ARE TEMPORARY VALUES!!
                    for (List<String> strings : freezeUserList) {
                        txtAreaFrozenUsers.append(strings.get(0) + strings.get(1) + "\n");
                    }

                    // not done but really easy when we decide how to store thresholds
                    MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 3);

                    // done but not tested
                    MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 4);

                    // Partially done
                    MainTabbedPane.insertTab("Messages", null, Messages, null, 5);

                } else {
                    showMessageDialog(null, "Your account did not match any credentials in " +
                            "our system.");
                }
            } catch (AccountNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException | ClassNotFoundException | TradeNumberException | ItemNotFoundException exception) {
                showMessageDialog(null, exception.getStackTrace());
            } catch (InvalidStatusTypeException invalidStatusTypeException) {
                invalidStatusTypeException.printStackTrace();
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
            String name =  txtInventoryInput.getText();
            String description = txtAreaDescriptionInput.getText();
            List<String> tagList = Arrays.asList(txtTagsInput.getText().split("\\s*,\\s*")); // TEST THIS
            try {
                addItemsController.addInventoryItem(name, description, tagList);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
            List<String> requestedItemsList = Arrays.asList(txtRequestedItemsInput.getText().split("\\s*,\\s*")); // TEST THIS
            List<String> offeredItemsList = Arrays.asList(txtRequestItemInput.getText().split("\\s*,\\s*"));

            if (offeredItemsList.isEmpty()){ //then the user wants to borrow the items from the user

            }
            //requestController.createRequest(perm, tradeAlgorithimName, );
        });

        btnOfferEnter.addActionListener(e -> {
            offersController.offerResponse(user, txtOffersOutput, rbtnAcceptOffer, rbtnDenyOffer);
        });

        btnFreezeEnter.addActionListener(e -> {
            freezeController.freezeDecision(txtFrozenUser, txtAreaFrozenUsers, rbtnUnfreezeUser, rbtnIgnoreUser, currUserIndex);
        });

        btnRequestsEnter.addActionListener(e -> {
            requestsController.requestsResponse(txtRequestsOutput, rbtnAcceptRequest, rbtnDenyRequest);
        });

        btnAddAdmin.addActionListener(e -> {
            String usernameAdmin = txtAdminUsernameInput.getText();
            String passwordAdmin = txtAdminPasswordInput.getText();
            String emailAdmin = txtAdminEmailInput.getText();
            try {
                addAdminController.addAdmin(usernameAdmin, passwordAdmin, emailAdmin);
            } catch (IOException | InvalidUsernameException | InvalidPasswordException | InvalidEmailAddressException | EmailAddressInUseException | UsernameInUseException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
        });

        btnMessageUser.addActionListener(e -> {
            String messageTitle = txtMessageUserTitleInput.getText();
            String messageContent = txtAreaMessageUserInput.getText();
            List<String> recipientList = Arrays.asList(txtMessageRecipientInput.getText().split("\\s*,\\s*"));
            try {
                messageController.sendUserMessage(messageTitle, messageContent, recipientList);
            } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException | IOException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
        });

        btnMessageAdmin.addActionListener(e -> {
            String messageTitle = txtMessageAdminTitleInput.getText();
            String messageContent = txtAreaMessageAdminInput.getText();
            try {
                messageController.sendRequestToSystem(messageTitle, messageContent);
            } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException | IOException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
        });
    }


    private void tabCleaner(){
        if (MainTabbedPane.getTabCount() == 2) {
            MainTabbedPane.removeTabAt(1);
        }
    }


}
