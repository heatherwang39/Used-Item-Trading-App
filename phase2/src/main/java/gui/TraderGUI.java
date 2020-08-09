package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.status.StatusNotFoundException;
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
    private JTextField txtRequestedItemsInput;
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
    private JTextArea txtAreaMessagesIncomingOutput;
    private JTextArea txtMessagesSentOutput;
    private JTextField txtMessageUserTitleInput;
    private JTextField txtMessageAdminTitleInput;
    private JTextField txtAdminEmailInput;
    private JTextField txtAdminUsernameInput;
    private JPanel Logging;
    private JPanel UserList;
    private JTextArea txtAreaUserListOutput;
    private JTextField txtAccountStatuses;
    private JButton btnAccountSetAwayStatus;
    private JTextField txtAccountAwayStatus;
    private JTextField txtUserListOutput;
    private JRadioButton rbtnUserListMute;
    private JRadioButton rbtnUserListNext;
    private JButton btnUserListEnter;
    private JButton btnThresholdIncompleteEnter;
    private JButton btnThresholdWeeklyEnter;
    private JLabel MainLabel;
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
                        break;
                    default:
                        showMessageDialog(null, "Your account did not match any credentials in " +
                                "our system.");
                        break;
                }
            } catch (AccountNotFoundException | ItemNotFoundException accountNotFoundException) {
                showMessageDialog(null, accountNotFoundException.getMessage());
            } catch (IOException | ClassNotFoundException | TradeNumberException exception) {
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

    private void initializeStatus() throws IOException, ClassNotFoundException, TradeNumberException {
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

    private void initializeAccount() throws IOException, ClassNotFoundException, AccountNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Account", null, Account, null, 1);

        AccountController accountController = new AccountController(storageGateway, user);

        txtUsernameOutput.setText(user);
        txtEmailOutput.setText(accountController.getEmail());
        txtAreaInventoryOutput.setText(accountController.getInventoryString());
        txtAreaWishlistOutput.setText(accountController.getWishlistString());

        String statusString = accountController.getStatusString();
        txtAccountStatuses.setText(statusString);

        if (accountController.isAway()){
            txtAccountAwayStatus.setText("You are marked as 'away'. Click the button to the right to return");
        } else{
            txtAccountAwayStatus.setText("Click the button to the right to be marked as 'away'");
        }

        btnAccountSetAwayStatus.addActionListener(e -> {
            if (accountController.isAway()){
                try {
                    accountController.removeAwayStatus();
                } catch (StatusNotFoundException statusNotFoundException) {
                    statusNotFoundException.printStackTrace();
                }
            } else{
                try {
                    accountController.setAwayStatus();
                } catch (InvalidStatusTypeException invalidStatusTypeException) {
                    invalidStatusTypeException.printStackTrace();
                }
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

    private void initializeOffers() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Offers", null, Offers, null, 3);

        TradePresenter tradePresenter = new TradePresenter(storageGateway);
        OffersController offersController = new OffersController(storageGateway, user, tradePresenter);

        btnOfferEnter.addActionListener(e -> {
            List<HashMap<String, List<String>>> unformattedOfferList = null;
            List<String> offerList = null;
            try {
                unformattedOfferList = offersController.getOffers();
                offerList = tradePresenter.formatTradeForListView(unformattedOfferList);
            } catch (ItemNotFoundException | TradeNumberException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
            assert offerList != null;
            txtOffersOutput.setText(offerList.get(0));
            try {
                if (rbtnAcceptOffer.isSelected()){
                    offersController.acceptOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                } else if (rbtnDenyOffer.isSelected()){
                    offersController.rejectOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                } else{
                    // we should consider adding a "decide later" option
                }
            } catch (TradeNumberException | StatusNotFoundException | IOException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
        });
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

        btnWishlistAddition.addActionListener(e -> {
            String itemID = txtWishlistInput.getText();
            try {
                addItemsController.addWishlistItem(user, itemID);
                showMessageDialog(null, "Item was added to wishlist! Changes will appear when you log back in.");
            } catch (ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }       });
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
            int newBorrowingThreshold = Integer.parseInt(txtThresholdBorrowedInput.getText());
            try {
                thresholdController.setBorrowingThreshold(newBorrowingThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
        btnThresholdIncompleteEnter.addActionListener(e -> {
            int newIncompleteThreshold = Integer.parseInt(txtThresholdIncompleteInput.getText());
            try {
                thresholdController.setIncompleteThreshold(newIncompleteThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
        btnThresholdWeeklyEnter.addActionListener(e -> {
            int newWeeklyThreshold = Integer.parseInt(txtThresholdWeeklyInput.getText());
            try {
                thresholdController.setWeeklyThreshold(newWeeklyThreshold);
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void initializeUserList() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("User List", null, UserList, null, 2);
        UserlistController userlistController = new UserlistController(storageGateway);
        btnUserListEnter.addActionListener(e -> {

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

            if(frozenUserList != null){
                txtFrozenUser.setText(frozenUserList.get(currUserIndex).get(0) + frozenUserList.get(currUserIndex).get(1));
                if (rbtnUnfreezeUser.isSelected()) {
                    try {
                        freezeController.unfreezeUser(frozenUserList.get(0).get(0));

                    } catch (IOException | StatusNotFoundException exception) {
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
}
