package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.trade.Trade;
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
    private JPanel Logging;
    private JPanel UserList;
    private JTextArea accountInformationTextArea;

    private String user;
    private final StorageGateway storageGateway;
    private int currUserIndex = 0;

    public TraderGUI(StorageGateway storageGateway) {
        this.storageGateway = storageGateway;
        MainTabbedPane.removeAll();
        try {
            initializeLogin();
        } catch (IOException | ClassNotFoundException e) {
            showMessageDialog(null, e.getStackTrace());
        }
    }

    private void initializeLogin() throws IOException, ClassNotFoundException {

        LoginController loginController = new LoginController(storageGateway);

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
            initializeBrowse();

        });

        signOutButton.addActionListener(e -> {
            MainTabbedPane.removeAll();
            MainTabbedPane.insertTab("Main", null, Main, null, 0);
        });

        btnLogin.addActionListener(e -> {
            String user = txtLoginUsername.getText();
            String pass = txtLoginPassword.getText();
            txtLoginUsername.setText("");
            txtLoginPassword.setText("");
            try {
                switch (loginController.login(user, pass)) {
                    case "USER":
                        this.user = user;
                        MainTabbedPane.removeAll();
                        MainTabbedPane.insertTab("Home", null, Home, null, 0);
                        initializeBrowse();
                        initializeAccount();
                        initializeStatus();
                        break;
                    case "ADMIN":
                        this.user = user;
                        MainTabbedPane.removeAll();
                        MainTabbedPane.insertTab("Home", null, Home, null, 0);
                        initializeBrowse();
                        initializeRequests();
                        initializeThreshold();
                        initializeUserList();
                        initializeAddAdmin();
                        initializeLogging();
                        initializeFreeze();
                        break;
                    default:
                        showMessageDialog(null, "Your account did not match any credentials in " +
                                "our system.");
                        break;
                }
            } catch (AccountNotFoundException | ItemNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
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
                ioException.printStackTrace();
            }
            txtLoginUsername.setText("");
            txtLoginPassword.setText("");
            MainTabbedPane.removeAll();
            MainTabbedPane.insertTab("Main", null, Main, null, 0);
        });
    }

    private void initializeBrowse() {
        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);
    }

    private void initializeStatus() throws IOException, ClassNotFoundException {
        StatusController statusController = new StatusController(user, storageGateway);
        if (!statusController.getStatuses().contains("AWAY") || !statusController.getStatuses().contains("FROZEN")) {
            initializeRequest();
            initializeOffers();
        }
        if (!statusController.getStatuses().contains("FROZEN")) {
            initializeMessages();
        }
        if (!statusController.getStatuses().contains("NEW")) {
            initializeActivity();
        }
        initializeAddItems();
    }

    private void initializeAccount() throws IOException, ClassNotFoundException {
        AccountController accountController = new AccountController(storageGateway, user);

        MainTabbedPane.insertTab("Account", null, Account, null, 1);
    }

    private void initializeActivity() {
        MainTabbedPane.insertTab("Activity", null, Activity, null, 3);
    }

    private void initializeOffers() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Offers", null, Offers, null, 3);

        TradePresenter tradePresenter = new TradePresenter(storageGateway);
        OffersController offersController = new OffersController(storageGateway, user, tradePresenter);

        btnOfferEnter.addActionListener(e -> offersController.offerResponse(user, txtOffersOutput, rbtnAcceptOffer, rbtnDenyOffer));
    }

    private void initializeRequest() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Request", null, Request, null, 3);

        RequestController requestController = new RequestController(storageGateway, user);

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
    }

    private void initializeAddItems() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Add Items", null, AddItems, null, 3);

        AddItemsController addItemsController = new AddItemsController(storageGateway, user);

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
    }

    private void initializeMessages() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Messages", null, Messages, null, 3);

        MessageController messageController = new MessageController(storageGateway, user);

        btnMessageAdmin.addActionListener(e -> {
            String messageTitle = txtMessageAdminTitleInput.getText();
            String messageContent = txtAreaMessageAdminInput.getText();
            try {
                messageController.sendRequestToSystem(messageTitle, messageContent);
            } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        btnMessageUser.addActionListener(e -> {
            String messageTitle = txtMessageUserTitleInput.getText();
            String messageContent = txtAreaMessageUserInput.getText();
            List<String> recipientList = Arrays.asList(txtMessageRecipientInput.getText().split("\\s*,\\s*"));
            try {
                messageController.sendUserMessage(messageTitle, messageContent, recipientList);
            } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    // Admin Tabs

    private void initializeRequests() throws IOException, ClassNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Requests", null, Requests, null, 2);

        ItemPresenter itemPresenter = new ItemPresenter();
        RequestsController requestsController = new RequestsController(storageGateway, itemPresenter);

        for (String s : requestsController.getFormattedRequests()) {
            // text area appends each line of the formatted list
            txtAreaRequestsOutput.append(s);
        }

        btnRequestsEnter.addActionListener(e -> {
            try {
                List<HashMap<String, String>> requestsList = requestsController.getRequests();
                List<String> formattedRequestsList = requestsController.getFormattedRequests();
                txtRequestsOutput.setText(formattedRequestsList.get(0));
                if (requestsList != null) {
                    requestsList.remove(requestsList.get(0));
                    formattedRequestsList.remove(formattedRequestsList.get(0));
                    if (rbtnAcceptRequest.isSelected()) {
                        requestsController.verifyItem(Integer.parseInt(requestsList.get(0).get("id")));

                    } else if (rbtnDenyRequest.isSelected()) {
                        requestsController.rejectItem(Integer.parseInt(requestsList.get(0).get("id")));

                    } else {
                        requestsList.add(requestsList.get(0));
                        formattedRequestsList.add(formattedRequestsList.get(0));
                        showMessageDialog(null, "Please accept or deny this request!");
                    }
                }
                requestsController.requestsResponse(txtRequestsOutput, rbtnAcceptRequest, rbtnDenyRequest);
            } catch (ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeThreshold() {
        MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 2);
    }

    private void initializeUserList() {
        MainTabbedPane.insertTab("User List", null, UserList, null, 2);
    }

    private void initializeAddAdmin() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 4);

        AddAdminController addAdminController = new AddAdminController(storageGateway);

        btnAddAdmin.addActionListener(e -> {
            String usernameAdmin = txtAdminUsernameInput.getText();
            String passwordAdmin = txtAdminPasswordInput.getText();
            String emailAdmin = txtAdminEmailInput.getText();
            try {
                addAdminController.addAdmin(usernameAdmin, passwordAdmin, emailAdmin);
                showMessageDialog(null, "Admin added : (" + usernameAdmin + ") (" + passwordAdmin + ")");
                txtAdminUsernameInput.setText("");
                txtAdminPasswordInput.setText("");
                txtAdminEmailInput.setText("");
            } catch (InvalidUsernameException | InvalidPasswordException | InvalidEmailAddressException |
                    EmailAddressInUseException | UsernameInUseException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeLogging() {
        MainTabbedPane.insertTab("Logging", null, Logging, null, 2);
    }


    private void initializeFreeze() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Un-Freeze", null, Freeze, null, 2);

        FreezeController freezeController = new FreezeController(storageGateway);

        btnFreezeEnter.addActionListener(e -> freezeController.freezeDecision(txtFrozenUser, txtAreaFrozenUsers, rbtnUnfreezeUser, rbtnIgnoreUser, currUserIndex));
    }


    private void tabCleaner(){
        if (MainTabbedPane.getTabCount() == 2) {
            MainTabbedPane.removeTabAt(1);
        }
    }
}
