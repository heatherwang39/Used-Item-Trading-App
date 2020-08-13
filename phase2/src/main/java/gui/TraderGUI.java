package main.java.gui;

import main.java.controller.*;
import main.java.model.account.*;
import main.java.model.item.AlreadyHiddenException;
import main.java.model.item.AlreadyNotHiddenException;
import main.java.model.item.ItemNotFoundException;
import main.java.model.meeting.MeetingAlreadyConfirmedException;
import main.java.model.meeting.MeetingIDException;
import main.java.model.meeting.TimeException;
import main.java.model.meeting.WrongMeetingAccountException;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.trade.*;
import main.java.system.StorageGateway;
import main.java.presenter.*;

import javax.swing.*;
// From: https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
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
    private JTabbedPane MainTabbedPane;
    public JPanel MainContainer;
    private JTextField txtUsernameOutput;
    private JTextField txtEmailOutput;
    private JTextField txtInventoryInput;
    private JTextField txtWishlistInput;
    private JTextField txtRequestItemInput;
    private JRadioButton rbtnTempTrade;
    private JRadioButton rbtnPermTrade;
    private JButton btnRequestTrade;
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
    private JTextArea txtAreaMessagesIncoming;
    private JTextArea txtAreaMessagesSent;
    private JTextArea txtAreaRequestSuggestTradesOutput;
    private JButton btnRequestSuggestionEnter;
    private JTextArea txtAreaMeetingAcceptedTrades;
    private JTextField txtMeetingAcceptedTrade;
    private JTextField txtMeetingSuggestInput;
    private JButton btnMeetingSuggest;
    private JTextArea txtAreaMeetingSuggestions;
    private JTextField txtMeetingSuggested;
    private JRadioButton rbtnMeetingAccept;
    private JRadioButton rbtnMeetingDeny;
    private JTextArea txtAreaMeetingOngoing;
    private JTextField txtMeetingOngoingOutput;
    private JButton btnMeetingOngoingEnter;
    private JPanel Meeting;
    private JButton btnMeetingEnterResponse;
    private JTextArea txtAreaMeetingCompleted;
    private JTextField txtMeetingTimeInput;
    private JRadioButton rbtnMeetingNext;
    private JRadioButton rbtnMeetingCompleted;
    private JTextField txtThresholdGildedInput;
    private JButton btnThresholdGuildedEnter;
    private JTextArea txtAreaAccountThresholds;
    private JRadioButton rbtnLend;
    private JRadioButton rbtnViewNextSuggestion;
    private JTextField txtFreezeUserInput;
    private JButton btnFreezeUser;
    private JTabbedPane tabbedPane2;
    private JTextField txtThresholdUsernameInput;
    private JTextField txtThresholdUserBorrowing;
    private JTextField txtThresholdUserIncompleted;
    private JTextField txtThresholdUserWeekly;
    private JTextField txtThresholdUserGilded;
    private JButton btnThresholdUserBorrowing;
    private JButton btnThresholdUserIncompleted;
    private JButton btnThresholdUserWeekly;
    private JButton btnThresholdUserGilded;
    private JTextArea accountInformationTextArea;
    private JButton exitButton;
    private JTextPane traderSystemTextPane;
    private JScrollPane scrlInventoryOutput;
    private JScrollPane scrlWishlistOutput;
    private JTabbedPane tabbedPane1;
    private JLabel MainLabel;
    private JTabbedPane MessagesTabbedPane;
    private JButton btnThresholdSetToDefault;
    private JTabbedPane tabbedPane3;
    private JTextField txtItemsUnhiddenOutput;
    private JTextField txtItemsHiddenOutput;
    private JButton btnItemsHide;
    private JButton btnItemsUnhide;
    private JTextArea txtAreaItemsUnhiddenOutput;
    private JTextArea txtAreaItemsHiddenOutput;
    private JTextField txtRequestLendSuggestedInput;
    private JTabbedPane tabbedPane4;
    private JPasswordField pswdLogin;
    private JTextField txtRequestSantaExplanation;
    private JTextField txtRequestSantaInput;
    private JButton btnRequestSantaEnter;
    private JTextArea txtAreaRequestSantaExplanation;

    private String user;
    private final StorageGateway storageGateway;


    public TraderGUI(StorageGateway storageGateway) {
        this.storageGateway = storageGateway;
        MainTabbedPane.removeAll();
        try {
            initializeLogin();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        txtItemsUnhiddenOutput.setEditable(false);
        txtItemsHiddenOutput.setEditable(false);
        txtAreaBrowseListingsOutput.setEditable(false);
        txtAreaFrozenUsers.setEditable(false);
        txtAreaLoggingOutput.setEditable(false);
        txtAreaUserListOutput.setEditable(false);
        txtAreaInventoryOutput.setEditable(false);
        txtAreaWishlistOutput.setEditable(false);
        txtAreaRequestsOutput.setEditable(false);
        txtAreaOffersOutput.setEditable(false);
        txtAreaRequestSuggestTradesOutput.setEditable(false);
        txtAreaActivityTradeOutput.setEditable(false);
        txtAreaActivityPartnerOutput.setEditable(false);
        txtAreaItemsUnhiddenOutput.setEditable(false);
        txtAreaItemsHiddenOutput.setEditable(false);
        txtAreaRequestSantaExplanation.setEditable(false);


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


        initializeRegister();
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

            char[] passChar = pswdLogin.getPassword();
            StringBuilder pass = new StringBuilder();
            for (char ch : passChar){ pass.append(ch); }

            try {
                switch (loginController.login(user, pass.toString())) {
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
        AccountController accountController = new AccountController(storageGateway,user);
        if (!accountController.getStatuses().contains("AWAY") && !accountController.getStatuses().contains("FROZEN")) {
            initializeRequest();
            initializeOffers();
            initializeMeeting();
        }
        if (!accountController.getStatuses().contains("FROZEN")) {
            initializeMessages();
        }
        if (!accountController.getStatuses().contains("NEW")) {
            initializeActivity();
        }
        initializeItems();
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

    private void initializeActivity() throws IOException, ClassNotFoundException, TradeNumberException {
        MainTabbedPane.insertTab("Activity", null, Activity, null, 3);
        ActivityController activityController = new ActivityController(storageGateway, user);

        List<List<Integer>> tradeList = activityController.recentItemsTraded();
        TradePresenter tradePresenter = new TradePresenter(storageGateway);
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

    private void initializeBrowse() throws IOException, ClassNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);
        BrowseController browseController = new BrowseController(storageGateway);

        MainTabbedPane.insertTab("Browse", null, Browse, null, 1);

        displayBrowse(browseController.getItemsString());
    }

    private void initializeOffers() throws IOException, ClassNotFoundException, TradeNumberException, ItemNotFoundException {
        MainTabbedPane.insertTab("Offers", null, Offers, null, 3);
        TradePresenter tradePresenter = new TradePresenter(storageGateway);
        OffersController offersController = new OffersController(storageGateway, user, tradePresenter);

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
                        MeetingController meetingController = new MeetingController(storageGateway, user);
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


    private void initializeRequest() throws IOException, ClassNotFoundException, ItemNotFoundException {
        AtomicInteger index = new AtomicInteger();
        MainTabbedPane.insertTab("Request", null, Request, null, 3);

        RequestController requestController = new RequestController(storageGateway, user);
        ItemPresenter itemPresenter = new ItemPresenter();

        List<List<HashMap<String, String>>> suggestionList = requestController.suggestAllItems();
        displayRequestSuggestions(suggestionList, itemPresenter);

        txtAreaRequestSantaExplanation.setText("Here you can offer an item you own for 'Secret Santa'. \n" +
                "When there is enough items, your item should be lent to random user that the program will pick. \n" +
                "Likewise you will receive an item from one of the other users.");

        btnRequestSantaEnter.addActionListener(e -> {
            Integer itemID = tryParse(txtRequestSantaInput.getText());
        });

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
                        requestController.createRequest(false, tradeAlgorithmName, tradeItemList);
                        showMessageDialog(null, "Request created!\n" +
                                "Items: " + tradeItemList.toString());

                        } catch (ItemNotFoundException | NoSuchTradeAlgorithmException | WrongTradeAccountException |
                            TradeCancelledException exception) {
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
                    String validRequest = requestController.checkValidRequest(requestedItem, offeredItem);
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
                        requestController.createRequest(rbtnPermTrade.isSelected(), tradeAlgorithmName, tradeItemsList);
                        showMessageDialog(null, "Request submitted!\n" +
                                "Items: " + tradeItemsList.toString());
                        rbtnPermTrade.setSelected(false);
                        rbtnTempTrade.setSelected(false);
                        txtRequestedItemInput.setText("");
                        txtRequestItemInput.setText("");
                    }
                } catch (ItemNotFoundException | NoSuchTradeAlgorithmException | TradeCancelledException |
                        WrongTradeAccountException | TradeNumberException exception) {
                    showMessageDialog(null, exception.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


    }

    private void initializeItems() throws IOException, ClassNotFoundException, ItemNotFoundException {
        MainTabbedPane.insertTab("Add Items", null, AddItems, null, 3);

        ItemsController itemsController = new ItemsController(storageGateway, user);
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

        BrowseController browseController = new BrowseController(storageGateway);

        displayUnhiddenInventory(itemsController.getUnhiddenInventory(), itemPresenter);
        displayHiddenInventory(itemsController.getHiddenInventory(), itemPresenter);

        btnItemsHide.addActionListener(e -> {

            try {
                List<HashMap<String, String>> unhiddenInventory = itemsController.getUnhiddenInventory();
                String item = unhiddenInventory.get(0).get("id");
                itemsController.hideItem(item);
                showMessageDialog(null, unhiddenInventory.get(0).get("name") + " has been successfully hidden");
                unhiddenInventory.remove(0);
                displayUnhiddenInventory(itemsController.getUnhiddenInventory(), itemPresenter);
                displayHiddenInventory(itemsController.getHiddenInventory(), itemPresenter);
                displayBrowse(browseController.getItemsString());

            } catch (AlreadyHiddenException | ItemNotFoundException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }catch (IndexOutOfBoundsException outOfBoundsException) {
                showMessageDialog(null, "There is no item to hide");
            }

        });

        btnItemsUnhide.addActionListener(e ->{

            try {
                List<HashMap<String, String>> hiddenInventory = itemsController.getHiddenInventory();
                String item = hiddenInventory.get(0).get("id");
                itemsController.unhideItem(item);
                showMessageDialog(null, hiddenInventory.get(0).get("name") + " has been successfully unhidden");
                hiddenInventory.remove(0);
                displayUnhiddenInventory(itemsController.getUnhiddenInventory(), itemPresenter);
                displayHiddenInventory(itemsController.getHiddenInventory(), itemPresenter);
                displayBrowse(browseController.getItemsString());

            } catch (ItemNotFoundException | AlreadyNotHiddenException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (IndexOutOfBoundsException outOfBoundsException) {
                showMessageDialog(null, "There is no item to unhide");
            }
        });
    }


    private void initializeMeeting() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Meeting", null, Meeting, null, 3);
        MeetingController meetingController = new MeetingController(storageGateway, user);
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
                BrowseController browseController = new BrowseController(storageGateway);
                displayBrowse(browseController.getItemsString());
            } catch (IOException | ClassNotFoundException | ItemNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });

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
                        BrowseController browseController = new BrowseController(storageGateway);
                        displayBrowse(browseController.getItemsString());
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
            } catch (ClassNotFoundException | IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void initializeThreshold() throws IOException, ClassNotFoundException {
        MainTabbedPane.insertTab("Trade Threshold", null, Threshold, null, 2);
        ThresholdController thresholdController = new ThresholdController(storageGateway);

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
        final int[] currUserIndex = {0};
        MainTabbedPane.insertTab("Un-Freeze", null, Freeze, null, 2);

        FreezeController freezeController = new FreezeController(storageGateway);

        btnFreezeEnter.addActionListener(e -> {
            List<List<String>> frozenUserList = new ArrayList<>();

            try {
                frozenUserList = freezeController.showAllFrozenUsers();
            } catch (AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            }

            if(!frozenUserList.isEmpty()){
                txtFrozenUser.setText(frozenUserList.get(currUserIndex[0]).get(0) + frozenUserList.get(currUserIndex[0]).get(1));
                if (rbtnUnfreezeUser.isSelected()) {
                    try {
                        freezeController.unfreezeUser(frozenUserList.get(0).get(0));
                        showMessageDialog(null, "User: " + frozenUserList.get(0).get(0) + " has been unfrozen!");
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
            } catch (AccountNotFoundException | WrongAccountTypeException exception) {
                showMessageDialog(null, exception.getMessage());
            } catch (IOException ioException){
                ioException.printStackTrace();
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

    private void displayUnhiddenInventory(List<HashMap<String, String>> unhiddenList, ItemPresenter itemPresenter){
        txtItemsUnhiddenOutput.setText("");
        txtAreaItemsUnhiddenOutput.setText("");
        List<String> formattedUnhiddenList = itemPresenter.formatItemsToListView(unhiddenList);
        for (String item : formattedUnhiddenList){
            txtAreaItemsUnhiddenOutput.append(item + "\n");
        }
        if (!formattedUnhiddenList.isEmpty()) {
            txtItemsUnhiddenOutput.setText(formattedUnhiddenList.get(0));
        }

    }

    private void displayHiddenInventory(List<HashMap<String, String>> hiddenList, ItemPresenter itemPresenter){
        txtItemsHiddenOutput.setText("");
        txtAreaItemsHiddenOutput.setText("");
        List<String> formattedHiddenList = itemPresenter.formatItemsToListView(hiddenList);
        for (String item : formattedHiddenList){
            txtAreaItemsHiddenOutput.append(item + "\n");
        }
        if (!formattedHiddenList.isEmpty()) {
            txtItemsHiddenOutput.setText(formattedHiddenList.get(0));
        }

    }

    private Integer tryParse(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {

            showMessageDialog(null, "Invalid Input! Please try again");
            return null;
            }
    }
}
