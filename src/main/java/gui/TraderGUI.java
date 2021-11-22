package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.AlreadyHiddenException;
import main.java.model.item.AlreadyNotHiddenException;
import main.java.model.item.ItemInTradeException;
import main.java.model.item.ItemNotFoundException;
import main.java.model.meeting.MeetingAlreadyConfirmedException;
import main.java.model.meeting.MeetingIDException;
import main.java.model.meeting.TimeException;
import main.java.model.meeting.WrongMeetingAccountException;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.trade.*;
import main.java.system.StorageDepot;
import main.java.presenter.*;

import javax.swing.*;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public JPanel MainContainer;
    private JPanel Main;
    private JPanel Home;
    private JPanel Account;
    private JPanel Login;
    private JPanel Register;
    private JPanel Items;
    private JPanel AddAdmin;
    private JPanel Request;
    private JPanel Offers;
    private JPanel Activity;
    private JPanel Browse;
    private JPanel Requests;
    private JPanel Freeze;
    private JPanel Threshold;
    private JPanel Messages;
    private JPanel UserList;
    private JPanel Meeting;

    private JTabbedPane MainTabbedPane;
    private JTabbedPane ThresholdTabbedPane;
    private JTabbedPane MeetingTabbedPane;
    private JTabbedPane MessagesTabbedPane;
    private JTabbedPane ItemsTabbedPane;
    private JTabbedPane RequestTabbedPane;

    private JPasswordField pswdLogin;

    private JTextField txtUsernameOutput;
    private JTextField txtEmailOutput;
    private JTextField txtInventoryInput;
    private JTextField txtWishlistInput;
    private JTextField txtRequestItemInput;
    private JTextField txtThresholdBorrowedInput;
    private JTextField txtRegisterUsername;
    private JTextField txtRegisterEmail;
    private JTextField txtRegisterPassword;
    private JTextField txtLoginUsername;
    private JTextField txtAdminPasswordInput;
    private JTextField txtTagsInput;
    private JTextField txtFrozenUser;
    private JTextField txtOffersOutput;
    private JTextField txtThresholdIncompleteInput;
    private JTextField txtThresholdWeeklyInput;
    private JTextField txtRequestsOutput;
    private JTextField txtMessageRecipientInput;
    private JTextField txtAccountStatuses;
    private JTextField txtRequestedItemInput;
    private JTextField txtMessageUserTitleInput;
    private JTextField txtMessageAdminTitleInput;
    private JTextField txtAdminEmailInput;
    private JTextField txtAdminUsernameInput;
    private JTextField txtUserListOutput;
    private JTextField txtMeetingAcceptedTrade;
    private JTextField txtMeetingSuggestInput;
    private JTextField txtMeetingSuggested;
    private JTextField txtMeetingOngoingOutput;
    private JTextField txtMeetingTimeInput;
    private JTextField txtThresholdGildedInput;
    private JTextField txtFreezeUserInput;
    private JTextField txtThresholdUserWeekly;
    private JTextField txtThresholdUsernameInput;
    private JTextField txtThresholdUserGilded;
    private JTextField txtThresholdUserIncompleted;
    private JTextField txtThresholdUserBorrowing;
    private JTextField txtItemsUnhideInput;
    private JTextField txtItemsHideInput;
    private JTextField txtRequestLendSuggestedInput;
    private JTextField txtRequestRandomUser;

    private JTextArea txtAreaActivityTradeOutput;
    private JTextArea txtAreaActivityPartnerOutput;
    private JTextArea txtAreaDescriptionInput;
    private JTextArea txtAreaInventoryOutput;
    private JTextArea txtAreaWishlistOutput;
    private JTextArea txtAreaBrowseListingsOutput;
    private JTextArea txtAreaFrozenUsers;
    private JTextArea txtAreaRequestsOutput;
    private JTextArea txtAreaOffersOutput;
    private JTextArea txtAreaMessageUserInput;
    private JTextArea txtAreaMessageAdminInput;
    private JTextArea txtAreaUserListOutput;
    private JTextArea txtAreaMessagesIncoming;
    private JTextArea txtAreaMessagesSent;
    private JTextArea txtAreaRequestSuggestTradesOutput;
    private JTextArea txtAreaMeetingAcceptedTrades;
    private JTextArea txtAreaMeetingSuggestions;
    private JTextArea txtAreaMeetingOngoing;
    private JTextArea txtAreaMeetingCompleted;
    private JTextArea txtAreaAccountThresholds;
    private JTextArea txtAreaHiddenInventory;
    private JTextArea txtAreaUnhiddenInventory;

    private JButton btnLoginMain;
    private JButton btnGuestMain;
    private JButton btnRegisterMain;
    private JButton btnThresholdBorrowedEnter;
    private JButton btnRequestTrade;
    private JButton btnRegister;
    private JButton btnLogin;
    private JButton btnAddAdmin;
    private JButton btnInventoryRequest;
    private JButton btnWishlistAddition;
    private JButton btnFreezeEnter;
    private JButton btnOfferEnter;
    private JButton btnRequestsEnter;
    private JButton btnMessageUser;
    private JButton btnMessageAdmin;
    private JButton btnAccountSetAwayStatus;
    private JButton btnUserListEnter;
    private JButton btnThresholdIncompleteEnter;
    private JButton btnThresholdWeeklyEnter;
    private JButton btnRequestSuggestionEnter;
    private JButton btnMeetingSuggest;
    private JButton btnMeetingOngoingEnter;
    private JButton btnThresholdSetToDefault;
    private JButton btnMeetingEnterResponse;
    private JButton btnFreezeUser;
    private JButton btnThresholdGuildedEnter;
    private JButton btnThresholdUserBorrowing;
    private JButton btnThresholdUserIncompleted;
    private JButton btnThresholdUserWeekly;
    private JButton btnThresholdUserGilded;
    private JButton btnItemsHide;
    private JButton btnItemsUnhide;
    private JButton btnRequestRandomAddUser;
    private JButton btnRequestRandomOfferTrade;

    private JRadioButton rbtnTempTrade;
    private JRadioButton rbtnPermTrade;
    private JRadioButton rbtnUnfreezeUser;
    private JRadioButton rbtnIgnoreUser;
    private JRadioButton rbtnAcceptOffer;
    private JRadioButton rbtnDenyOffer;
    private JRadioButton rbtnAcceptRequest;
    private JRadioButton rbtnDenyRequest;
    private JRadioButton rbtnUserListMute;
    private JRadioButton rbtnUserListNext;
    private JRadioButton rbtnMeetingAccept;
    private JRadioButton rbtnMeetingDeny;
    private JRadioButton rbtnMeetingNext;
    private JRadioButton rbtnMeetingCompleted;
    private JRadioButton rbtnLend;
    private JRadioButton rbtnViewNextSuggestion;
    private JRadioButton rbtnRequestRandomPerm;
    private JRadioButton rbtnRequestRandomTemp;

    private JLabel lblLogoMain;
    private JLabel lblLogoHome;

    private String user;
    private final StorageDepot storageDepot;


    public TraderGUI(StorageDepot storageDepot) {
        this.storageDepot = storageDepot;
        MainTabbedPane.removeAll();
        initializeLogin();
    }

    private void initializeLogin() {

        LoginController loginController = new LoginController(storageDepot);


        MainTabbedPane.insertTab("Main", null, Main, null, 0);

        // Sets all appropriate text fields and areas to not be editable
        txtUsernameOutput.setEditable(false);
        txtEmailOutput.setEditable(false);
        txtOffersOutput.setEditable(false);
        txtAccountStatuses.setEditable(false);
        txtRequestsOutput.setEditable(false);
        txtAreaUnhiddenInventory.setEditable(false);
        txtAreaHiddenInventory.setEditable(false);
        txtAreaBrowseListingsOutput.setEditable(false);
        txtAreaFrozenUsers.setEditable(false);
        txtAreaUserListOutput.setEditable(false);
        txtAreaInventoryOutput.setEditable(false);
        txtAreaWishlistOutput.setEditable(false);
        txtAreaRequestsOutput.setEditable(false);
        txtAreaOffersOutput.setEditable(false);
        txtAreaRequestSuggestTradesOutput.setEditable(false);
        txtAreaActivityTradeOutput.setEditable(false);
        txtAreaActivityPartnerOutput.setEditable(false);


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

        ButtonGroup requestButtonGroup = new ButtonGroup();
        requestButtonGroup.add(rbtnLend);
        requestButtonGroup.add(rbtnViewNextSuggestion);

        ButtonGroup meetingSuggestionButtonGroup = new ButtonGroup();
        meetingSuggestionButtonGroup.add(rbtnMeetingAccept);
        meetingSuggestionButtonGroup.add(rbtnMeetingDeny);

        ButtonGroup meetingOngoingButtonGroup = new ButtonGroup();
        meetingOngoingButtonGroup.add(rbtnMeetingCompleted);
        meetingOngoingButtonGroup.add(rbtnMeetingNext);

        ButtonGroup randomButtonGroup = new ButtonGroup();
        randomButtonGroup.add(rbtnRequestRandomPerm);
        randomButtonGroup.add(rbtnRequestRandomTemp);


        initializeRegister();
        btnLoginMain.addActionListener(e -> {
            tabCleaner();
            MainTabbedPane.insertTab("Login", null, Login, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });

        btnGuestMain.addActionListener(e -> {
            tabCleaner();
            try {
                initializeBrowse();
            } catch (ItemNotFoundException exception) {
                exception.printStackTrace();
            }

        });

        btnLogin.addActionListener(e -> {
            String user = txtLoginUsername.getText();

            char[] passChar = pswdLogin.getPassword();
            StringBuilder pass = new StringBuilder();
            for (char ch : passChar){ pass.append(ch); }

            try {
                switch (loginController.login(user, pass.toString())) {
                    case "USER":
                        this.user = user;
                        MainTabbedPane.removeAll();
                        MainTabbedPane.insertTab("Home", null, Home, null, 0);
                        initializeStatus();
                        initializeBrowse();
                        initializeAccount();
                        break;
                    case "ADMIN":
                        this.user = user;
                        MainTabbedPane.removeAll();
                        MainTabbedPane.insertTab("Home", null, Home, null, 0);
                        initializeAddAdmin();
                        initializeThreshold();
                        initializeUserList();
                        initializeFreeze();
                        initializeMessages();
                        initializeItemRequests();
                        initializeBrowse();
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
                MainTabbedPane.removeAll();
                MainTabbedPane.insertTab("Main", null, Main, null, 0);
            } catch (UsernameInUseException | InvalidEmailAddressException | EmailAddressInUseException |
                    InvalidUsernameException | InvalidPasswordException invalidLoginException) {
                showMessageDialog(null, invalidLoginException.getMessage());
                initializeRegister();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                initializeRegister();
            }
        });
    }

    private void initializeRegister(){
        btnRegisterMain.addActionListener(e -> {
            tabCleaner();
            MainTabbedPane.insertTab("Register", null, Register, null, 1);
            MainTabbedPane.setSelectedIndex(1);
        });
    }

    private void initializeStatus() throws IOException, ClassNotFoundException, TradeNumberException, ItemNotFoundException, AccountNotFoundException, WrongAccountTypeException {
        AccountController accountController = new AccountController(storageDepot,user);
        if (!accountController.getStatuses().contains("AWAY") && !accountController.getStatuses().contains("FROZEN")) {
            initializeMeeting();
            initializeOffers();
            initializeTradeRequest();
        }
        if (!accountController.getStatuses().contains("FROZEN")) {
            initializeMessages();
        }
        if (!accountController.getStatuses().contains("NEW")) {
            initializeActivity();
        }
        initializeItems();
    }

    private void initializeAccount() throws AccountNotFoundException, ItemNotFoundException, WrongAccountTypeException {
        MainTabbedPane.insertTab("Account", null, Account, null, 1);

        AccountController accountController = new AccountController(storageDepot, user);

        txtUsernameOutput.setText(user);
        txtEmailOutput.setText(accountController.getEmail());
        txtAreaInventoryOutput.setText(accountController.getInventoryString());
        txtAreaWishlistOutput.setText(accountController.getWishlistString());

        String statusString = accountController.getStatusString();
        txtAccountStatuses.setText(statusString);
        
        txtAreaAccountThresholds.setText(accountController.getThresholdsString());
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

    private void initializeActivity() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Activity", null, Activity, null, 1);
        ActivityController activityController = new ActivityController(storageDepot, user);

        List<List<Integer>> tradeList = activityController.recentItemsTraded();
        TradePresenter tradePresenter = new TradePresenter(storageDepot);
        try {
            for (String s : tradePresenter.formatItemsInTradeForListView(tradeList)) {
                txtAreaActivityTradeOutput.append(s + "\n");
            }
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }

        List<String> partnerList = activityController.frequentTradingPartners();
        for (String s : partnerList) {
            txtAreaActivityPartnerOutput.append(s + "\n");
        }
    }

    private void initializeBrowse() throws ItemNotFoundException {
        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);
        BrowseController browseController = new BrowseController(storageDepot);

        displayBrowse(browseController.getItemsString());
    }

    private void initializeOffers() throws IOException, ClassNotFoundException, TradeNumberException, ItemNotFoundException {
        MainTabbedPane.insertTab("Offers", null, Offers, null, 1);
        TradePresenter tradePresenter = new TradePresenter(storageDepot);
        OffersController offersController = new OffersController(storageDepot, user);

        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers(), "OFFERS"));

        btnOfferEnter.addActionListener(e -> {
            try{
                List<HashMap<String, List<String>>> unformattedOfferList = offersController.getOffers();
                List<String> offerList = tradePresenter.formatTradeForListView(unformattedOfferList, "OFFERS");
                if (!offerList.isEmpty()) {
                    txtOffersOutput.setText(offerList.get(0));
                    if (rbtnAcceptOffer.isSelected()) {
                        offersController.acceptOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers(), "OFFERS"));
                        showMessageDialog(null, "Trade accepted!");
                        MeetingController meetingController = new MeetingController(storageDepot, user);
                        displaySuggestMeetings(meetingController);
                    } else if (rbtnDenyOffer.isSelected()) {
                        offersController.rejectOffer(Integer.parseInt(unformattedOfferList.get(0).get("id").get(0)));
                        txtAreaOffersOutput.setText(tradePresenter.formatTradeString(offersController.getOffers(), "OFFERS"));
                        showMessageDialog(null, "Trade rejected!");
                    } else {
                        showMessageDialog(null, "Please select an option!");
                    }
                }
            } catch (TradeNumberException | ItemNotFoundException | WrongTradeAccountException |
                    TradeCancelledException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (ClassNotFoundException | IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    private void initializeTradeRequest() throws IOException, ClassNotFoundException, ItemNotFoundException {
        AtomicInteger index = new AtomicInteger();
        MainTabbedPane.insertTab("Trade Request", null, Request, null, 1);

        TradeRequestController tradeRequestController = new TradeRequestController(storageDepot, user);
        RandomTradeRequestController randomTradeRequestController = new RandomTradeRequestController(storageDepot, user);
        ItemPresenter itemPresenter = new ItemPresenter();

        List<List<HashMap<String, String>>> suggestionList = tradeRequestController.suggestAllItems();
        displayRequestSuggestions(suggestionList, itemPresenter);

        btnRequestSuggestionEnter.addActionListener(e -> {
            displayRequestSuggestions(suggestionList, itemPresenter);
            TradeAlgorithmName tradeAlgorithmName = TradeAlgorithmName.CYCLE;
            if (!suggestionList.isEmpty()) {
                if (rbtnLend.isSelected()) {
                // lend the items

                List<Integer> tradeItemList = new ArrayList<>();

                {
                    try {
                        for (HashMap<String, String> individualItem : suggestionList.get(index.get())) {
                            tradeItemList.add(Integer.parseInt(individualItem.get("id")));
                        }
                        tradeRequestController.createRequest(false, tradeAlgorithmName, tradeItemList);
                        showMessageDialog(null, "Request created!\n" +
                                "Items: " + tradeItemList.toString());

                        } catch (ItemNotFoundException | NoSuchTradeAlgorithmException | WrongTradeAccountException |
                            TradeCancelledException | ItemAlreadyInActiveTradeException exception) {
                        showMessageDialog(null, exception.getMessage());
                        } catch (TradeNumberException | IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                    suggestionList.remove(0);
                    if (index.get() == suggestionList.size()) {
                        index.set(0);
                    }
                    if (suggestionList.isEmpty()) {
                        displayRequestSuggestions(suggestionList, itemPresenter);
                    } else {
                        displayRequestSuggestions(Collections.singletonList(suggestionList.get(index.get())), itemPresenter);
                    }

                } else if(rbtnViewNextSuggestion.isSelected()){
                    index.getAndIncrement();
                    if (index.get() == suggestionList.size()) {
                        index.set(0);
                    }
                    displayRequestSuggestions(Collections.singletonList(suggestionList.get(index.get())), itemPresenter);
                } else {
                    showMessageDialog(null, "Please select an option!");
                }
            }


        });

        btnRequestTrade.addActionListener(e -> {
            if (!rbtnTempTrade.isSelected() && !rbtnPermTrade.isSelected()) {
                showMessageDialog(null, "Please select a type of trade for this request!");
            } else {
                List<Integer> tradeItemsList = new ArrayList<>();
                String requestedItem = txtRequestedItemInput.getText();
                String offeredItem = txtRequestItemInput.getText();

                try {
                    String validRequest = tradeRequestController.checkValidRequest(requestedItem, offeredItem);
                    if (!validRequest.equals("VALID")) {
                        showMessageDialog(null, validRequest);
                    } else {
                        if (!offeredItem.isEmpty()) {
                            Integer offeredItemID = Integer.parseInt(offeredItem);
                            tradeItemsList.add(offeredItemID);
                        } else {
                            tradeItemsList.add(null);
                        }
                        if (!requestedItem.isEmpty()) {
                            Integer requestedItemID = Integer.parseInt(requestedItem);
                            tradeItemsList.add(requestedItemID);
                        }
                        TradeAlgorithmName tradeAlgorithmName = TradeAlgorithmName.CYCLE;
                        tradeRequestController.createRequest(rbtnPermTrade.isSelected(), tradeAlgorithmName, tradeItemsList);
                        showMessageDialog(null, "Request submitted!\n" +
                                "Items: " + tradeItemsList.toString());
                        rbtnPermTrade.setSelected(false);
                        rbtnTempTrade.setSelected(false);
                        txtRequestedItemInput.setText("");
                        txtRequestItemInput.setText("");
                    }
                } catch (ItemNotFoundException | NoSuchTradeAlgorithmException | TradeCancelledException |
                        WrongTradeAccountException | TradeNumberException | ItemAlreadyInActiveTradeException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        btnRequestRandomAddUser.addActionListener(e -> {
            String trader = txtRequestRandomUser.getText();
            try {
                randomTradeRequestController.addTrader(trader);
            } catch (UserAlreadyAddedException | NoValidItemsFromGivenUserException | AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            }
        });

        btnRequestRandomOfferTrade.addActionListener(e -> {
            if (!rbtnRequestRandomPerm.isSelected() && !rbtnRequestRandomTemp.isSelected()){
                showMessageDialog(null, "Please select an option!");
            } else{
                try {
                    randomTradeRequestController.createRequest(rbtnRequestRandomPerm.isSelected());
                } catch (ItemAlreadyInActiveTradeException | NoSuchTradeAlgorithmException | TradeNumberException | WrongTradeAccountException | TradeCancelledException | TooFewTradersException | NoValidItemsFromGivenUserException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }


    private void initializeItems() {
        MainTabbedPane.insertTab("Items", null, Items, null, 1);

        ItemsController itemsController = new ItemsController(storageDepot, user);
        ItemPresenter itemPresenter = new ItemPresenter();

        // Add items tab
        btnInventoryRequest.addActionListener(e -> {
            String name =  txtInventoryInput.getText();
            String description = txtAreaDescriptionInput.getText();
            List<String> tagList = Arrays.asList(txtTagsInput.getText().split("\\s*,\\s*")); // TEST THIS
            try {
                itemsController.addInventoryItem(name, description, tagList);
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
                if (itemsController.ownsItem(user, itemID)) {
                    showMessageDialog(null, "You can't wishlist your own item!");
                }
                else {
                    itemsController.addWishlistItem(itemID);
                    showMessageDialog(null, "Item was added to wishlist! Changes will appear when you log back in.");
                    txtWishlistInput.setText("");
                }
            } catch (ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        // Hide/Unhide Items Tab

        BrowseController browseController = new BrowseController(storageDepot);
        displayHiddenUnhidden(itemsController, itemPresenter);

        btnItemsHide.addActionListener(e -> {
            String itemID = txtItemsHideInput.getText();
            try {
                if (itemsController.ownsItem(user, itemID)) {
                    itemsController.hideItem(itemID);
                    showMessageDialog(null, "Item has been hidden");
                    txtItemsHideInput.setText("");
                    displayHiddenUnhidden(itemsController, itemPresenter);
                    displayBrowse(browseController.getItemsString());
                }
                else showMessageDialog(null, "That item does not belong to you");
            } catch (AlreadyHiddenException | ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Your input was not a number");
            }

        });

        btnItemsUnhide.addActionListener(e ->{
            String itemID = txtItemsUnhideInput.getText();
            try {
                if (itemsController.ownsItem(user, itemID)) {
                    itemsController.unhideItem(itemID);
                    showMessageDialog(null, "Item has been unhidden");
                    txtItemsUnhideInput.setText("");
                    displayHiddenUnhidden(itemsController, itemPresenter);
                    displayBrowse(browseController.getItemsString());
                } else showMessageDialog(null, "That item does not belong to you");
            } catch (AlreadyNotHiddenException | ItemNotFoundException | ItemInTradeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Your input was not a number");
            }

        });
    }


    private void initializeMeeting() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Meeting", null, Meeting, null, 1);
        MeetingController meetingController = new MeetingController(storageDepot, user);
        displaySuggestMeetings(meetingController);
        displayMeetingSuggestions(meetingController);
        displayOngoingMeetings(meetingController, 0);
        displayCompletedMeetings(meetingController);

        final int[] currOngoingIndex = {0};
        btnMeetingSuggest.addActionListener(e -> {
            List<HashMap<String, List<String>>> acceptedTradesListUnformatted = new ArrayList<>();
            try {
                acceptedTradesListUnformatted = meetingController.getAcceptedTradesUnformatted();
                acceptedTradesListUnformatted.addAll(meetingController.getUnfinishedTradesUnformatted());
            } catch (TradeNumberException | MeetingIDException tradeNumberException) {
                showMessageDialog(null, tradeNumberException.getMessage());
            }

            String suggestedPlace = txtMeetingAcceptedTrade.getText();
            String suggestedTimeStr = txtMeetingTimeInput.getText();
            // from https://www.java67.com/2016/04/how-to-convert-string-to-localdatetime-in-java8-example.html
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            try {
                LocalDateTime suggestedTime = LocalDateTime.parse(suggestedTimeStr, dateTimeFormatter);

                if (!acceptedTradesListUnformatted.isEmpty()) {
                    meetingController.suggestMeeting(Integer.parseInt(acceptedTradesListUnformatted.get(0).get("id").get(0)), suggestedPlace, suggestedTime);
                    showMessageDialog(null, "The meeting has been suggested!");
                }
            } catch (DateTimeParseException dateTimeParseException) {
                showMessageDialog(null, "The input meeting time should be in \"yyyy-MM-dd HH:mm\" format");
            } catch (TradeNumberException | TradeCancelledException | MaxNumMeetingsExceededException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (WrongMeetingAccountException | MeetingIDException | IOException ioException) {
                ioException.printStackTrace();
            }
            txtMeetingAcceptedTrade.setText("");
            txtMeetingTimeInput.setText("");
            displaySuggestMeetings(meetingController);
        });

        btnMeetingEnterResponse.addActionListener(e ->{
            List<HashMap<String, List<String>>> meetingSuggestionsListUnformatted;

            try {
                meetingSuggestionsListUnformatted = meetingController.getSuggestedMeetingsUnformatted();
                if (!meetingSuggestionsListUnformatted.isEmpty()){
                    if (rbtnMeetingAccept.isSelected()){
                        meetingController.acceptMeeting(Integer.parseInt(meetingSuggestionsListUnformatted.get(0).get("id").get(0)));
                        showMessageDialog(null, "Suggested meeting is accepted!");
                    } else if (rbtnMeetingDeny.isSelected()){
                        meetingController.rejectMeeting(Integer.parseInt(meetingSuggestionsListUnformatted.get(0).get("id").get(0)));
                        showMessageDialog(null, "Suggested meeting is rejected");
                        displaySuggestMeetings(meetingController);
                    } else{
                        showMessageDialog(null, "Please either accept or deny this suggestion");
                    }
                }
            } catch (WrongMeetingAccountException | MeetingIDException | MeetingAlreadyConfirmedException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
            displayMeetingSuggestions(meetingController);
            displayOngoingMeetings(meetingController, currOngoingIndex[0]);
        });


        btnMeetingOngoingEnter.addActionListener(e -> {
            List<HashMap<String, List<String>>> ongoingMeetingsListUnformatted = new ArrayList<>();
            try {
                ongoingMeetingsListUnformatted = meetingController.getOngoingMeetingsUnformatted();
            } catch (MeetingIDException exception) {
                showMessageDialog(null, exception.getMessage());
            }
            if (!ongoingMeetingsListUnformatted.isEmpty()){
                if (rbtnMeetingCompleted.isSelected()) {
                    try {
                        meetingController.confirmMeeting(Integer.parseInt(ongoingMeetingsListUnformatted.get(currOngoingIndex[0]).get("id").get(0)));
                        showMessageDialog(null, "Meeting has been confirmed!");
                        currOngoingIndex[0] = 0;
                    } catch (WrongMeetingAccountException | MeetingIDException | TimeException exception) {
                        showMessageDialog(null, exception.getMessage());
                    } catch (IOException ioException){
                        ioException.printStackTrace();
                    }
                } else if (rbtnMeetingNext.isSelected()){
                    if (currOngoingIndex[0] < ongoingMeetingsListUnformatted.size() - 1) currOngoingIndex[0]++;
                    else currOngoingIndex[0] = 0;
                } else{
                    showMessageDialog(null, "Please select an option!");
                }
            }
            displayOngoingMeetings(meetingController, currOngoingIndex[0]);
            displayCompletedMeetings(meetingController);
            displaySuggestMeetings(meetingController);
            try {
                BrowseController browseController = new BrowseController(storageDepot);
                displayBrowse(browseController.getItemsString());
            } catch(ItemNotFoundException itemNotFoundException){
                showMessageDialog(null, itemNotFoundException.getMessage());
            }
        });
    }


    private void initializeMessages() {
        MainTabbedPane.insertTab("Messages", null, Messages, null, 1);
        MessageController messageController = new MessageController(storageDepot, user);
        MessagePresenter messagePresenter = new MessagePresenter();

        displayIncomingMessages(messageController, messagePresenter);
        displaySentMessages(messageController, messagePresenter);

        btnMessageUser.addActionListener(e -> {
            String messageTitle = txtMessageUserTitleInput.getText();
            String messageContent = txtAreaMessageUserInput.getText();
            List<String> recipientList = Arrays.asList(txtMessageRecipientInput.getText().split("\\s*,\\s*"));
            if (!messageController.validMessage(messageTitle, messageContent, recipientList)) {
                showMessageDialog(null,"Please send a valid message to an existing user!");
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
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }



    // Admin Tabs

    private void initializeItemRequests() throws ItemNotFoundException {
        MainTabbedPane.insertTab("Item Requests", null, Requests, null, 1);

        ItemPresenter itemPresenter = new ItemPresenter();
        ItemRequestsController itemRequestsController = new ItemRequestsController(storageDepot, itemPresenter);

        txtAreaRequestsOutput.setText(itemRequestsController.getRequestsString());

        btnRequestsEnter.addActionListener(e -> {
            try {
                List<HashMap<String, String>> requestsList = itemRequestsController.getRequests();
                List<String> formattedRequestsList = itemRequestsController.getFormattedRequests();
                if (!requestsList.isEmpty()) {
                    HashMap<String, String> item = requestsList.get(0);
                    txtRequestsOutput.setText(formattedRequestsList.get(0));
                    if (rbtnAcceptRequest.isSelected()) {
                        showMessageDialog(null, "Item accepted!\nName: " + item.get("name") +
                                "\nDescription: " + item.get("description") +
                                "\nTags: " + item.get("tags"));
                        itemRequestsController.verifyItem(Integer.parseInt(item.get("id")));
                        requestsList.remove(item);
                        formattedRequestsList.remove(formattedRequestsList.get(0));
                        txtAreaRequestsOutput.setText(itemRequestsController.getRequestsString());
                        BrowseController browseController = new BrowseController(storageDepot);
                        displayBrowse(browseController.getItemsString());
                    } else if (rbtnDenyRequest.isSelected()) {
                        showMessageDialog(null, "Item rejected!\nName: " + item.get("name") +
                                "\nDescription: " + item.get("description") +
                                "\nTags: " + item.get("tags"));
                        itemRequestsController.rejectItem(Integer.parseInt(item.get("id")));
                        requestsList.remove(item);
                        formattedRequestsList.remove(formattedRequestsList.get(0));
                        txtAreaRequestsOutput.setText(itemRequestsController.getRequestsString());
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


    private void initializeThreshold() {
        MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 1);
        ThresholdController thresholdController = new ThresholdController(storageDepot);

        btnThresholdSetToDefault.addActionListener(e -> {
            try {
                thresholdController.setAllThresholdsFromTextFile();
                showMessageDialog(null, "All thresholds have been set to to the file default (set from thresholds.txt");
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });

        btnThresholdBorrowedEnter.addActionListener(e -> {
            try {
                int newBorrowingThreshold = Integer.parseInt(txtThresholdBorrowedInput.getText());
                thresholdController.setBorrowingThreshold(newBorrowingThreshold);
                showMessageDialog(null, "New Borrowing Threshold has been set to: " + newBorrowingThreshold);
                txtThresholdBorrowedInput.setText("");
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException | IOException exception) {
                showMessageDialog(null,exception.getMessage());
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });

        btnThresholdIncompleteEnter.addActionListener(e -> {
            try {
                int newIncompleteThreshold = Integer.parseInt(txtThresholdIncompleteInput.getText());
                thresholdController.setIncompleteThreshold(newIncompleteThreshold);
                showMessageDialog(null, "New Incompleted Trades Threshold has been set to: " + newIncompleteThreshold);
                txtThresholdIncompleteInput.setText("");
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException | IOException exception) {
                showMessageDialog(null,exception.getMessage());
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });
        btnThresholdWeeklyEnter.addActionListener(e -> {
            try {
                int newWeeklyThreshold = Integer.parseInt(txtThresholdWeeklyInput.getText());
                thresholdController.setWeeklyThreshold(newWeeklyThreshold);
                showMessageDialog(null, "New Weekly Trade Threshold has been set to: " + newWeeklyThreshold);
                txtThresholdWeeklyInput.setText("");
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException | IOException exception) {
                showMessageDialog(null,exception.getMessage());
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });
        btnThresholdGuildedEnter.addActionListener(e -> {
            try {
                int newGildedThreshold = Integer.parseInt(txtThresholdGildedInput.getText());
                thresholdController.setGildedThreshold(newGildedThreshold);
                showMessageDialog(null, "New Threshold for Users to obtain the Guilded Status set to: " + newGildedThreshold);
                txtThresholdGildedInput.setText("");
            } catch (AccountNotFoundException | WrongAccountTypeException | NegativeThresholdException exception) {
                 showMessageDialog(null,exception.getMessage());
            } catch (IOException ioException){
                 ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });


        btnThresholdUserBorrowing.addActionListener(e -> {
            try {
                String thresholdUser = txtThresholdUsernameInput.getText();
                int userBorrowingThreshold = Integer.parseInt(txtThresholdUserBorrowing.getText());
                thresholdController.setBorrowingThresholdForUser(thresholdUser, userBorrowingThreshold);
                showMessageDialog(null, "Borrowing Threshold successfully set to " +
                        userBorrowingThreshold + " items for user: " + thresholdUser);
                txtThresholdUserBorrowing.setText("");
            } catch (AccountNotFoundException | NegativeThresholdException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });

        btnThresholdUserIncompleted.addActionListener(e -> {
            try {
                String thresholdUser = txtThresholdUsernameInput.getText();
                int userIncompletedThreshold = Integer.parseInt(txtThresholdUserIncompleted.getText());
                thresholdController.setIncompleteThresholdForUser(thresholdUser, userIncompletedThreshold);
                showMessageDialog(null, "Incompletd Trades Threshold successfully set to " +
                        userIncompletedThreshold + " trades for user: " + thresholdUser);
                txtThresholdUserIncompleted.setText("");
            } catch (AccountNotFoundException | NegativeThresholdException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });

        btnThresholdUserWeekly.addActionListener(e -> {
            try {
                String thresholdUser = txtThresholdUsernameInput.getText();
                int userWeeklyThreshold = Integer.parseInt(txtThresholdUserWeekly.getText());
                thresholdController.setWeeklyThresholdForUser(thresholdUser, userWeeklyThreshold);
                showMessageDialog(null, "Weekly Trades Threshold successfully set to " +
                        userWeeklyThreshold + " trades for user: " + thresholdUser);
                txtThresholdUserWeekly.setText("");
            } catch (AccountNotFoundException | NegativeThresholdException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });
        btnThresholdUserGilded.addActionListener(e -> {
            try {
                String thresholdUser = txtThresholdUsernameInput.getText();
                int userGildedThreshold = Integer.parseInt(txtThresholdUserGilded.getText());
                thresholdController.setGildedThresholdForUser(thresholdUser, userGildedThreshold);
                showMessageDialog(null, "Threshold for User to obtain Gilded Status " +
                        "successfully set to " + userGildedThreshold + " trades for user: "
                        + thresholdUser);
                txtThresholdUserGilded.setText("");
            } catch (AccountNotFoundException | NegativeThresholdException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "Input a valid number please!");
            }
        });
    }


    private void initializeUserList() throws AccountNotFoundException {
        AtomicInteger currUserIndex = new AtomicInteger();
        MainTabbedPane.insertTab("User List", null, UserList, null, 1);
        UserlistController userlistController = new UserlistController(storageDepot);
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

    private void initializeAddAdmin() {
        MainTabbedPane.insertTab("Add Admin", null, AddAdmin, null, 1);

        AddAdminController addAdminController = new AddAdminController(storageDepot);

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


    private void initializeFreeze() {
        final int[] currUserIndex = {0};
        MainTabbedPane.insertTab("Un-Freeze", null, Freeze, null, 1);

        FreezeController freezeController = new FreezeController(storageDepot);

        List<List<String>> initialFrozenUserList = new ArrayList<>();
        try {
            initialFrozenUserList = freezeController.showAllFrozenUsers();
        } catch (AccountNotFoundException | WrongAccountTypeException exception) {
            showMessageDialog(null, exception.getMessage());
        }
        for (List<String> user: initialFrozenUserList) {
            txtAreaFrozenUsers.append(String.join(", ", user) + "\n");
        }

        btnFreezeEnter.addActionListener(e -> {
            List<List<String>> frozenUserList = new ArrayList<>();
            try {
                frozenUserList = freezeController.showAllFrozenUsers();
            } catch (AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            }
            if(!frozenUserList.isEmpty()){
                txtFrozenUser.setText(String.join(", ", frozenUserList.get(currUserIndex[0])));
                if (rbtnUnfreezeUser.isSelected()) {
                    try {
                        freezeController.unfreezeUser(frozenUserList.get(0).get(0));
                        showMessageDialog(null, "User: " + frozenUserList.get(0).get(0) + " has been unfrozen!");
                        txtAreaFrozenUsers.setText("");
                        frozenUserList.remove(0);
                        for (List<String> user: frozenUserList) {
                            txtAreaFrozenUsers.append(String.join(", ", user) + "\n");
                        }

                    } catch (IOException | StatusNotFoundException | AccountNotFoundException | WrongAccountTypeException exception) {
                        showMessageDialog(null, exception.getMessage());
                    }

                } else if (rbtnIgnoreUser.isSelected()) {
                    currUserIndex[0]++;

                } else {
                    showMessageDialog(null, "Please make a verdict!");
                }
            }
        });

        btnFreezeUser.addActionListener(e -> {
           String userToFreeze = txtFreezeUserInput.getText();
            try {
                freezeController.freezeUser(userToFreeze);
                showMessageDialog(null, userToFreeze + " frozen!");
                List<List<String>> frozenUserList = freezeController.showAllFrozenUsers();
                for (List<String> user: frozenUserList) {
                    txtAreaFrozenUsers.append(String.join(", ", user) + "\n");
                }
            } catch (AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        });
    }




    // Helper methods

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

    private void displaySuggestMeetings(MeetingController meetingController){
        txtAreaMeetingAcceptedTrades.setText("Accepted Trades (without attached meetings)");
        List<String> acceptedTradesList = new ArrayList<>();
        List<String> unfinishedTradesList = new ArrayList<>();
        try {
            acceptedTradesList = meetingController.getAcceptedTrades();
            unfinishedTradesList = meetingController.getUnfinishedTrades();
        } catch (TradeNumberException | ItemNotFoundException | MeetingIDException tradeNumberException) {
            tradeNumberException.printStackTrace();
        }
        if (!acceptedTradesList.isEmpty()) {
            for (String s : acceptedTradesList) {
                txtAreaMeetingAcceptedTrades.append("\n ---------------------- \n" + s);
            }
            txtMeetingSuggestInput.setText("");
            txtMeetingSuggestInput.setText(acceptedTradesList.get(0));
        }
        if (!unfinishedTradesList.isEmpty()) {
            txtAreaMeetingAcceptedTrades.append("\n ---------------------- \n\nUnfinished Trades (set a meeting to trade back the items!)");
            for (String s : unfinishedTradesList) {
                txtAreaMeetingAcceptedTrades.append("\n ---------------------- \n" + s);
            }
            if (txtMeetingSuggestInput.getText().isEmpty()) {
                txtMeetingSuggestInput.setText("");
                txtMeetingSuggestInput.setText(unfinishedTradesList.get(0));
            }
        }
    }

    private void displayMeetingSuggestions(MeetingController meetingController){
        txtAreaMeetingSuggestions.setText("Meeting Suggestions");
        List<String> meetingSuggestionsList = new ArrayList<>();
        try {
            meetingSuggestionsList = meetingController.getSuggestedMeetings();
        } catch (MeetingIDException meetingIDException) {
            meetingIDException.printStackTrace();
        }
        if (!meetingSuggestionsList.isEmpty()){
            for (String s: meetingSuggestionsList){
                txtAreaMeetingSuggestions.append("\n ---------------------- \n" + s);
            }
            txtMeetingSuggested.setText("");
            txtMeetingSuggested.setText(meetingSuggestionsList.get(0));
        }
    }

    private void displayOngoingMeetings(MeetingController meetingController, int currMeetingIndex){
        txtAreaMeetingOngoing.setText("Ongoing Meetings");
        List<String> ongoingMeetingsList = new ArrayList<>();
        try {
            ongoingMeetingsList = meetingController.getOngoingMeetings();
        } catch (MeetingIDException meetingIDException) {
            meetingIDException.printStackTrace();
        }

        if(!ongoingMeetingsList.isEmpty()){
            for (String s : ongoingMeetingsList){
                txtAreaMeetingOngoing.append("\n ---------------------- \n" + s);
            }
            txtMeetingOngoingOutput.setText(ongoingMeetingsList.get(currMeetingIndex));
        }
    }

    private void displayCompletedMeetings(MeetingController meetingController){
        txtAreaMeetingCompleted.setText("");
        List<String> completedMeetingsList = new ArrayList<>();
        try {
            completedMeetingsList = meetingController.getCompletedMeetings();
        } catch (MeetingIDException meetingIDException) {
            meetingIDException.printStackTrace();
        }

        if (!completedMeetingsList.isEmpty()) {
            for (String s : completedMeetingsList){
                txtAreaMeetingCompleted.append("\n ---------------------- \n" + s);
            }
        }
    }

    private void displayRequestSuggestions(List<List<HashMap<String, String>>> unformattedSuggestionList, ItemPresenter itemPresenter) {
        txtAreaRequestSuggestTradesOutput.setText("");
        if (unformattedSuggestionList.isEmpty()) {
            txtAreaRequestSuggestTradesOutput.setText("No Suggestions Available (nothing in your inventory is in anyone elses wishlist, or vice versa)");
        } else {
            for (List<HashMap<String, String>> subSuggestionList : unformattedSuggestionList) {
                for (String s : itemPresenter.formatItemsToListView(subSuggestionList)) {
                    txtAreaRequestSuggestTradesOutput.append(s + "\n");
                }
                txtAreaRequestSuggestTradesOutput.append("----------------------- \n");
            }
        }
    }

    private void displayBrowse(String items) {
        txtAreaBrowseListingsOutput.setText(items);
    }

    private void displayHiddenUnhidden(ItemsController itemsController, ItemPresenter itemPresenter) {
        try {
            List<String> hiddenInventory = itemPresenter.formatItemsToListView(itemsController.getHiddenInventory());
            List<String> unhiddenInventory = itemPresenter.formatItemsToListView(itemsController.getUnhiddenInventory());
            txtAreaHiddenInventory.setText("Items currently hidden:\n");
            for (String itemData : hiddenInventory) {
                txtAreaHiddenInventory.append(itemData);
                txtAreaHiddenInventory.append("-----------------------\n");
            }
            txtAreaUnhiddenInventory.setText("Items currently unhidden:\n");
            for (String itemData : unhiddenInventory) {
                txtAreaUnhiddenInventory.append(itemData);
                txtAreaUnhiddenInventory.append("-----------------------\n");
            }
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
    }
}
