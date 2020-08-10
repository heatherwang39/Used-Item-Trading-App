package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.trade.*;
import main.java.system.StorageGateway;
import main.java.model.status.InvalidStatusTypeException;
import main.java.presenter.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * TraderGUI is a GUI class that manages the entire user interface process.
 *
 * @author Charles Wang
 * @version %I%, %G%
 * @since Phase 2
 */



public class TraderGUI {
    private JTabbedPane MainTabbedPane;
    public JPanel MainContainer;
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
    private JTextField txtThresholdBorrowedInput;
    private JButton btnThresholdBorrowedEnter;
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
    private JTextField txtThresholdIncompleteInput;
    private JTextField txtThresholdWeeklyInput;
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
    private JTextField txtMessageUserTitleInput;
    private JTextField txtMessageAdminTitleInput;
    private JTextField txtAdminEmailInput;
    private JTextField txtAdminUsernameInput;
    private JPanel Logging;
    private JPanel UserList;
    private JTextArea txtAreaUserListOutput;
    private JTextField txtAccountStatuses;
    private JTextField txtRequestedItemInput;
    private JButton btnAccountSetAwayStatus;
    private JTextField txtUserListOutput;
    private JRadioButton rbtnUserListMute;
    private JRadioButton rbtnUserListNext;
    private JButton btnUserListEnter;
    private JButton btnThresholdIncompleteEnter;
    private JButton btnThresholdWeeklyEnter;
    private JLabel MainLabel;
    private JTextArea txtAreaMessagesIncoming;
    private JTextArea txtAreaMessagesSent;
    private JTextArea txtAreaRequestSuggestTradesOutput;
    private JButton btnRequestSuggestion;
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

        // Sets all appropriate text fields and areas to not be editable
        txtUsernameOutput.setEditable(false);
        txtEmailOutput.setEditable(false);
        txtOffersOutput.setEditable(false);
        txtAccountStatuses.setEditable(false);
        txtRequestsOutput.setEditable(false);
        txtAreaBrowseListingsOutput.setEditable(false);
        txtAreaFrozenUsers.setEditable(false);
        txtAreaLoggingOutput.setEditable(false);
        txtAreaUserListOutput.setEditable(false);
        txtAreaInventoryOutput.setEditable(false);
        txtAreaWishlistOutput.setEditable(false);
        txtAreaRequestsOutput.setEditable(false);
        txtAreaOffersOutput.setEditable(false);


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

        ButtonGroup userListButtonGroup = new ButtonGroup();
        userListButtonGroup.add(rbtnUserListMute);
        userListButtonGroup.add(rbtnUserListNext);

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
            try {
                initializeBrowse();
            } catch (IOException | ClassNotFoundException | ItemNotFoundException exception) {
                exception.printStackTrace();
            }

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
                        initializeMessages();
                        break;
                    default:
                        showMessageDialog(null, "Your account did not match any credentials in " +
                                "our system.");
                        break;
                }
            } catch (AccountNotFoundException | ItemNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException | ClassNotFoundException | TradeNumberException | WrongAccountTypeException exception) {
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
                txtRegisterEmail.setText("");
                txtRegisterUsername.setText("");
                txtRegisterPassword.setText("");
            } catch (UsernameInUseException | InvalidEmailAddressException | EmailAddressInUseException |
                    InvalidUsernameException | InvalidPasswordException | AccountNotFoundException | WrongAccountTypeException invalidLoginException) {
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

    private void initializeStatus() throws IOException, ClassNotFoundException, TradeNumberException, ItemNotFoundException, AccountNotFoundException, WrongAccountTypeException {
        StatusController statusController = new StatusController(user, storageGateway);
        if (!statusController.getStatuses().contains("AWAY") && !statusController.getStatuses().contains("FROZEN")) {
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

    private void initializeAccount() throws IOException, ClassNotFoundException, AccountNotFoundException, ItemNotFoundException, WrongAccountTypeException {
        MainTabbedPane.insertTab("Account", null, Account, null, 1);

        AccountController accountController = new AccountController(storageGateway, user);

        txtUsernameOutput.setText(user);
        txtEmailOutput.setText(accountController.getEmail());
        txtAreaInventoryOutput.setText(accountController.getInventoryString());
        txtAreaWishlistOutput.setText(accountController.getWishlistString());

        String statusString = accountController.getStatusString();
        txtAccountStatuses.setText(statusString);

        if (accountController.isAway()){
            btnAccountSetAwayStatus.setText("Remove Away Status");
        } else{
            btnAccountSetAwayStatus.setText("Add Away Status");
        }

        btnAccountSetAwayStatus.addActionListener(e -> {
            try {
                if (accountController.isAway()){
                    accountController.removeAwayStatus();
                    btnAccountSetAwayStatus.setText("Add Away Status");
                    showMessageDialog(null, "Away status removed! This will take effect once you re-login.");
                } else {
                    accountController.setAwayStatus();
                    btnAccountSetAwayStatus.setText("Remove Away Status");
                    showMessageDialog(null, "Away status added! This will take effect once you re-login");
                }
            } catch (AccountNotFoundException | WrongAccountTypeException | StatusNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeActivity() throws IOException, ClassNotFoundException, TradeNumberException {
        MainTabbedPane.insertTab("Activity", null, Activity, null, 3);
        ActivityController activityController = new ActivityController(storageGateway, user);

        List<List<Integer>> tradeList = activityController.recentItemsTraded();
        StringBuilder tradeString = new StringBuilder();
        for (List<Integer> integers : tradeList) {
            for (Integer integer : integers) {
                tradeString.append(", ").append(integer);
                txtAreaActivityTradeOutput.append(tradeString + "\n");
            }
        }

        List<String> partnerList = activityController.frequentTradingPartners();
        for (String s : partnerList) {
            txtAreaActivityPartnerOutput.append(s + "\n");
        }
    }

    private void initializeBrowse() throws IOException, ClassNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);
        BrowseController browseController = new BrowseController(storageGateway);

        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);

        txtAreaBrowseListingsOutput.setText(browseController.getItemsString());
    }

    private void initializeOffers() throws IOException, ClassNotFoundException, TradeNumberException, ItemNotFoundException {
        MainTabbedPane.insertTab("Offers", null, Offers, null, 3);
        TradePresenter tradePresenter = new TradePresenter(storageGateway);
        OffersController offersController = new OffersController(storageGateway, user, tradePresenter);

        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers()));

        btnOfferEnter.addActionListener(e -> {
            try{
                List<HashMap<String, List<String>>> unformattedOfferList = offersController.getOffers();
                List<String> offerList = tradePresenter.formatTradeForListView(unformattedOfferList);
                if (!offerList.isEmpty()) {
                    txtOffersOutput.setText(offerList.get(0));
                    if (rbtnAcceptOffer.isSelected()) {
                        offersController.acceptOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers()));
                        showMessageDialog(null, "Trade accepted! Log back in to see changes");
                    } else if (rbtnDenyOffer.isSelected()) {
                        offersController.rejectOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers()));
                        showMessageDialog(null, "Trade rejected!");
                    } else {
                        showMessageDialog(null, "Please select an option!");
                    }
                }
            } catch (TradeNumberException | ItemNotFoundException | WrongTradeAccountException |
                    TradeCancelledException | AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeRequest() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Request", null, Request, null, 3);

        RequestController requestController = new RequestController(storageGateway, user);

        btnRequest.addActionListener(e -> {
            if (!rbtnTempTrade.isSelected() && !rbtnPermTrade.isSelected()) {
                showMessageDialog(null, "Please select a type of trade for this request!");
            } else {
                List<Integer> tradeItemsList = new ArrayList<>();
                String requestedItem = txtRequestedItemInput.getText();
                String offeredItem = txtRequestItemInput.getText();


                try {
                    if (!requestController.checkValidRequest(user, requestedItem, offeredItem)) {
                        showMessageDialog(null, "Please enter a valid request"); // placeholder
                    } else {
                        Integer requestedItemID = Integer.parseInt(requestedItem);
                        Integer offeredItemID = Integer.parseInt(offeredItem);
                        if (offeredItem.equals("")) {
                            tradeItemsList.add(requestedItemID);
                        } else if (requestedItem.equals("")) {
                            tradeItemsList.add(offeredItemID);
                        } else {
                            tradeItemsList.add(requestedItemID);
                            tradeItemsList.add(offeredItemID);
                        }
                        TradeAlgorithmName tradeAlgorithmName = TradeAlgorithmName.CYCLE;
                        requestController.createRequest(rbtnPermTrade.isSelected(), tradeAlgorithmName, tradeItemsList);
                        showMessageDialog(null, "Request submitted!\n" +
                                "Items: " + tradeItemsList.toString());
                        rbtnPermTrade.setSelected(false);
                        rbtnTempTrade.setSelected(false);
                        txtRequestedItemInput.setText("");
                        txtRequestItemInput.setText("");
                    }
                } catch (ItemNotFoundException | NoSuchTradeAlgorithmException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        btnRequestSuggestion.addActionListener(e -> {

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
                showMessageDialog(null, "Item requested!");
                txtInventoryInput.setText("");
                txtAreaDescriptionInput.setText("");
                txtTagsInput.setText("");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnWishlistAddition.addActionListener(e -> {
            String itemID = txtWishlistInput.getText();
            try {
                addItemsController.addWishlistItem(itemID);
                showMessageDialog(null, "Item was added to wishlist! Changes will appear when you log back in.");
                txtWishlistInput.setText("");
            } catch (ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }       });
    }

    private void initializeMessages() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Messages", null, Messages, null, 3);
        MessageController messageController = new MessageController(storageGateway, user);
        MessagePresenter messagePresenter = new MessagePresenter();

        displayIncomingMessages(messageController, messagePresenter);
        displaySentMessages(messageController, messagePresenter);

        btnMessageUser.addActionListener(e -> {
            String messageTitle = txtMessageUserTitleInput.getText();
            String messageContent = txtAreaMessageUserInput.getText();
            List<String> recipientList = Arrays.asList(txtMessageRecipientInput.getText().split("\\s*,\\s*"));
            if (!messageController.validMessage(messageTitle, messageContent, recipientList)) {
                showMessageDialog(null,"Plead send a valid message to an existing user!");
            }
            else {
                try {
                    messageController.sendUserMessage(messageTitle, messageContent, recipientList);
                    displaySentMessages(messageController, messagePresenter); // we can also refresh incoming messages too here
                    displayIncomingMessages(messageController, messagePresenter);
                    txtMessageRecipientInput.setText("");
                    txtMessageUserTitleInput.setText("");
                    txtAreaMessageUserInput.setText("");
                    String recipientString = messageController.getRecipientString(recipientList);
                    showMessageDialog(null, "Message sent to: " + recipientString);

                } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        });

        btnMessageAdmin.addActionListener(e -> {
            String messageTitle = txtMessageAdminTitleInput.getText();
            String messageContent = txtAreaMessageAdminInput.getText();
            if (!messageController.validMessage(messageTitle, messageContent)) {
                showMessageDialog(null,"Plead send a valid message!");
            } else {
                try {
                    messageController.sendRequestToSystem(messageTitle, messageContent);
                    displayIncomingMessages(messageController, messagePresenter);
                    txtMessageAdminTitleInput.setText("");
                    txtAreaMessageAdminInput.setText("");
                    showMessageDialog(null, "Message sent to admins");
                } catch (EmptyTitleException | EmptyContentException | EmptyRecipientListException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }



    // Admin Tabs

    private void initializeRequests() throws IOException, ClassNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Requests", null, Requests, null, 2);

        ItemPresenter itemPresenter = new ItemPresenter();
        RequestsController requestsController = new RequestsController(storageGateway, itemPresenter);

        txtAreaRequestsOutput.setText(requestsController.getRequestsString());

        btnRequestsEnter.addActionListener(e -> {
            try {
                List<HashMap<String, String>> requestsList = requestsController.getRequests();
                List<String> formattedRequestsList = requestsController.getFormattedRequests();
                if (!requestsList.isEmpty()) {
                    HashMap<String, String> item = requestsList.get(0);
                    txtRequestsOutput.setText(formattedRequestsList.get(0));
                    if (rbtnAcceptRequest.isSelected()) {
                        showMessageDialog(null, "Item accepted!\nName: " + item.get("name") +
                                "\nDescription: " + item.get("description") +
                                "\nTags: " + item.get("tags"));
                        requestsController.verifyItem(Integer.parseInt(item.get("id")));
                        requestsList.remove(item);
                        formattedRequestsList.remove(formattedRequestsList.get(0));
                        txtAreaRequestsOutput.setText(requestsController.getRequestsString());
                    } else if (rbtnDenyRequest.isSelected()) {
                        showMessageDialog(null, "Item rejected!\nName: " + item.get("name") +
                                "\nDescription: " + item.get("description") +
                                "\nTags: " + item.get("tags"));
                        requestsController.rejectItem(Integer.parseInt(item.get("id")));
                        requestsList.remove(item);
                        formattedRequestsList.remove(formattedRequestsList.get(0));
                        txtAreaRequestsOutput.setText(requestsController.getRequestsString());
                    } else {
                        showMessageDialog(null, "Please accept or deny this request!");
                    }
                }
            } catch (ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeThreshold() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 2);
        ThresholdController thresholdController = new ThresholdController(storageGateway);
        btnThresholdBorrowedEnter.addActionListener(e -> {
            txtThresholdBorrowedInput.setText("");
            int newBorrowingThreshold = Integer.parseInt(txtThresholdBorrowedInput.getText());
            try {
                thresholdController.setBorrowingThreshold(newBorrowingThreshold);
                showMessageDialog(null, "New Borrowing Threshold has been set to: " + newBorrowingThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
        btnThresholdIncompleteEnter.addActionListener(e -> {
            txtThresholdIncompleteInput.setText("");
            int newIncompleteThreshold = Integer.parseInt(txtThresholdIncompleteInput.getText());
            try {
                thresholdController.setIncompleteThreshold(newIncompleteThreshold);
                showMessageDialog(null, "New Incompleted Trades Threshold has been set to: " + newIncompleteThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
        btnThresholdWeeklyEnter.addActionListener(e -> {
            txtThresholdWeeklyInput.setText("");
            int newWeeklyThreshold = Integer.parseInt(txtThresholdWeeklyInput.getText());
            try {
                thresholdController.setWeeklyThreshold(newWeeklyThreshold);
                showMessageDialog(null, "New Weekly Trade Threshold has been set to: " + newWeeklyThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void initializeUserList() throws IOException, ClassNotFoundException, AccountNotFoundException {
        AtomicInteger currUserIndex = new AtomicInteger();
        MainTabbedPane.insertTab("User List", null, UserList, null, 2);
        UserlistController userlistController = new UserlistController(storageGateway);
        List<String> userList = userlistController.showUsers();
        for (String s : userlistController.showUserStrings()) {
            txtAreaUserListOutput.append(s);
        }
        btnUserListEnter.addActionListener(e -> {
            if (!userList.isEmpty()) {
                txtUserListOutput.setText(userList.get(currUserIndex.get()));
                if (rbtnUserListMute.isSelected()) {
                    try {
                        userlistController.muteUser(userList.get(currUserIndex.get()));
                        showMessageDialog(null, "Account was muted!");
                    } catch (AccountNotFoundException | WrongAccountTypeException exception) {
                        showMessageDialog(null, exception.getMessage());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (rbtnUserListNext.isSelected()) {
                    currUserIndex.getAndIncrement();
                    if (currUserIndex.get() >= userList.size()) {
                        currUserIndex.set(0);
                    }
                    txtUserListOutput.setText(userList.get(currUserIndex.get()));
                } else {
                    showMessageDialog(null, "Please select an option!");
                }
            }
        });
    }

    private void initializeAddAdmin() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 2);

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

        btnFreezeEnter.addActionListener(e -> {
            List<List<String>> frozenUserList = null;

            try {
                frozenUserList = freezeController.showAllFrozenUsers(3, 3, 3);
            } catch (InvalidStatusTypeException | IOException | TradeNumberException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }

            if(frozenUserList != null && !frozenUserList.isEmpty()){
                txtFrozenUser.setText(frozenUserList.get(currUserIndex).get(0) + frozenUserList.get(currUserIndex).get(1));
                if (rbtnUnfreezeUser.isSelected()) {
                    try {
                        freezeController.unfreezeUser(frozenUserList.get(0).get(0));
                        showMessageDialog(null, "User: " + frozenUserList.get(0).get(0) + " has been unfrozen!");
                    } catch (IOException exception) {
                        showMessageDialog(null, exception.getStackTrace());
                    }
                } else if (rbtnIgnoreUser.isSelected()) {
                    currUserIndex++;

                } else {
                    showMessageDialog(null, "Please make a verdict!");
                }
            }
        });
    }

    private void tabCleaner(){
        if (MainTabbedPane.getTabCount() == 2) {
            MainTabbedPane.removeTabAt(1);
        }
    }

    private void displayIncomingMessages(MessageController msgController, MessagePresenter msgPresenter) {
        txtAreaMessagesIncoming.append("\n\n");
        List<HashMap<String, String>> incomingMessagesList = msgController.getInbox();
        List<List<String>> formattedIncomingMessagesList = msgPresenter.formatMessageToListView(incomingMessagesList);
        for (List<String> strings : formattedIncomingMessagesList) {
            txtAreaMessagesIncoming.append("-----------------------------------------------\n");
            txtAreaMessagesIncoming.append(strings.get(0) + ":\n    " +
                    strings.get(1) + "\n");
        }
    }

    private void displaySentMessages(MessageController msgController, MessagePresenter msgPresenter){
        txtAreaMessagesSent.append("\n\n");
        List<HashMap<String, String>> sentMessagesList = msgController.getOutbox();
        List<List<String>> formattedSentMessagesList = msgPresenter.formatMessageToListView(sentMessagesList);
        for (List<String> strings : formattedSentMessagesList) {
            txtAreaMessagesSent.append("-----------------------------------------------\n");
            txtAreaMessagesSent.append(strings.get(0) + ":\n    " +
                    strings.get(1) + "\n");
        }
    }
}
