package main.java;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TraderSystem {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";

    private final AccountStorage am;
    private final TradeStorage tm;
    private final ItemStorage im;
    private final BufferedReader input;
    private String lastInput;
    private int tradeThreshold; //TODO: have configuration file set this
    private int weeklyThreshold;
    private int incompleteThreshold;


    /**
     * Creates a new TraderSystem.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public TraderSystem(BufferedReader keyboard) throws IOException, ClassNotFoundException {

        input = keyboard;
        tm = new TradeStorage(tradesPath);
        im = new ItemStorage(itemsPath);
        am = new AccountStorage(accountsPath);

        /**
        //TODO:delete this whole try clause after we're done with register.
            am.createAdminAccount("admin", "admin", "admin@trader.org");
            am.createUserAccount("Sarah.alk", "123456", "sarah@trader.org", false);
         */
    }

    /**
     * A method used to recieve input from the user.
     *
     * @return the user's input
     */
    public String getInput() {
        try {
            lastInput = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Improper input detected.");
        }
        return lastInput;
        //TODO: I'm working on eliminating .readLine from every other method so they don't throw IOexception
    }

    /**
     * Checks if username and password are valid alphanumeric + dash/underscore strings
     * @param username input username
     * @param password input password
     * @return a boolean representing whether the combination is valid
     */
    public Account signIn() throws IOException {
        try {
            System.out.println("Username: ");
            String username = getInput();
            System.out.println("Password: ");
            String pw = getInput();

            if (am.tryLogin(username, pw)) {
                return am.getAccount(username);
            } throw new AccountNotFoundException();
        } catch (AccountNotFoundException e) {
            System.out.println("Invalid Username or Password. Please try again.");
        }
        return signIn();

    }

    /**
     * Registers a new user
     *
     * @return the registered user's account instance
     * @throws IOException
     */
    public Account register() throws IOException {
        Account acc = null;
         //TODO make this method have less duplicate lines for errors
        String email;
        boolean emailChecker = false;
        do {
            System.out.println("Enter email: ");
            email = getInput();
            try {
                emailChecker = am.emailChecker(email);
            } catch (EmailInUseException e) {
                System.out.println("This email address is already in use. Please try again.");
            } catch (InvalidEmailException e) {
                System.out.println("Invalid email. Please try again.");
            }
        } while (!emailChecker);

        String username;
        boolean usernameChecker = false;
        do {
            System.out.println("Enter Username: ");
            username = getInput();
            try {
                usernameChecker = am.usernameChecker(username);
            } catch (UsernameInUseException e) {
                System.out.println("This Username is already in use. Please choose another Username.");
            } catch (InvalidLoginException e) {
                System.out.println("Invalid Username format.\n Please try again with letters, numbers, periods and special characters only.");
            }
        } while (!usernameChecker);

        String pw;
        boolean pwChecker = false;
        do {
            System.out.println("Enter Password: ");
            pw = getInput();
            try {
                if (!am.isInvalidLogin(username, pw)){
                    pwChecker = true;
                } else {
                    throw new InvalidLoginException();
                }
            } catch (InvalidLoginException e){
                System.out.println("Invalid Password format.\n Please try again with letters, numbers, periods and special characters only.");
            }
        } while (!pwChecker);

        try {
            acc = am.createUserAccount(username, pw, email, false);
        } catch (InvalidLoginException | InvalidEmailException | EmailInUseException | //I need to clean this smell.
                UsernameInUseException e) {
            e.printStackTrace(); // TODO: check if I can just override .toString in the exception, instead of  writing the following print lines
        }
        return acc;
    }

    /**
     * Prints out information about account
     * @param account an instance of Account
     * @throws IOException
     */
    public void accountInformation(Account account) throws IOException {
        System.out.println("Username: " + account.getUsername());
        System.out.println("Email Address: " + account.getEmail());
        System.out.println("-----------------");
        try {
            viewInventory(account);
            System.out.println("-----------------");
            viewWishlist(account);
        } catch (AccountNotFoundException e) {
            System.out.println("Your account is missing/deleted from the system. Please restart this program.");
        }
    }

    /**
     * Displays the inventory of account
     *
     * @param account an instance of Account
     * @throws IOException
     * @throws AccountNotFoundException
     */
    public void viewInventory(Account account) throws IOException, AccountNotFoundException {
        EntityDisplay ed = new EntityDisplay("Your Inventory");
        List<Integer> contraband = new ArrayList<>();
        for (int id : account.getInventory()) {
            try {
                ed.insert(im.getItem(id));
            } catch (ItemNotFoundException e) {
                contraband.add(id);
            }
        }
        if (!contraband.isEmpty()) {
            am.removeInventory(account.getUsername(), contraband);
            System.out.println(contraband.size() + " item(s) were found to invalid and have " +
                    "been removed from your inventory.");
        }
        ed.display();
    }

    /**
     * Displays the wishlist of account
     *
     * @param account an instance of Account
     * @throws IOException
     * @throws AccountNotFoundException
     */
    public void viewWishlist(Account account) throws IOException, AccountNotFoundException {
        EntityDisplay ed = new EntityDisplay("Your Wishlist");
        List<Integer> contraband = new ArrayList<>();
        for (int id : account.getWishlist()) {
            try {
                ed.insert(im.getItem(id));
            } catch (ItemNotFoundException e) {
                contraband.add(id);
            }
        }
        if (!contraband.isEmpty()) {
            am.removeWishlist(account.getUsername(), contraband);
            System.out.println(contraband.size() + " item(s) were found to invalid and have " +
                    "been removed from your wishist.");
        }
        ed.display();
    }

    /**
     * Adds an item to account
     *
     * @param account an instance of Account
     * @throws IOException
     */
    public void addItem(Account account) throws IOException {
        System.out.println("Enter the name of the item:");
        String name = getInput();
        System.out.println("Enter a short description:");
        String description = getInput();
        System.out.println("Enter the type of item (Book, Clothing, misc.):");
        switch (input.readLine().toLowerCase()) {
            case "book":
                System.out.println("Enter the name of the author:");
                String author = getInput();
                try {
                    Item item = im.newBook(name, description, author);
                    am.addInventory(account.getUsername(), item.getID());
                } catch (IOException | AccountNotFoundException e) {
                    System.out.println("Unable to read file/Account not found. Please restart the program.");
                }
                System.out.println("Book Item " + name + " was successfully added!");
                break;
            case "clothing":
                System.out.println("Enter the name of the brand:");
                String brand = getInput();
                try {
                    Item item = im.newClothing(name, description, brand);
                    am.addInventory(account.getUsername(), item.getID());
                } catch (IOException | AccountNotFoundException e) {
                    System.out.println("Unable to read file/Account not found. Please restart the program.");
                }
                System.out.println("Clothing Item " + name + " was successfully added!");
                break;
            default:
                try {
                    Item item = im.newMiscItem(name, description);
                    am.addInventory(account.getUsername(), item.getID());
                } catch (IOException | AccountNotFoundException e) {
                    System.out.println("Unable to read file/Account not found. Please restart the program.");
                }
                System.out.println("Misc Item " + name + " was successfully added!");
                break;
        }
    }

    /**
     * Displays every available item listing
     */
    public void browseListings() {
        EntityDisplay ed = new EntityDisplay("Available Items");
        for (Item item: im.getVerifiedItems()){
            ed.insert(item);
        }
        ed.display();
    }

    /**
     * Adds a new admin account
     *
     * @throws IOException
     */
    public void addAdmin() throws IOException {
        System.out.println("Enter the username of the user you would like to promote to admin: ");
        String username = getInput();
        try {
            Account user = am.getAccount(username);
            if (user.isAdmin()) System.out.println("User with corresponding username is already an admin.");
            else {
                am.removeUserAccount(username);
                am.createAdminAccount(username, user.getPassword(), user.getEmail());
                System.out.println("User has been promoted to an admin account.");
            }
        } catch (AccountNotFoundException e) {
            System.out.println("User with corresponding username was not found in the database.");
        } catch (InvalidLoginException e) {
            System.out.println("The corresponding username is invalid.");
        } catch (InvalidEmailException e) {
            System.out.println("The corresponding email is invalid.");
        } catch (EmailInUseException e) {
            System.out.println("The corresponding email is already in use.");
        } catch (UsernameInUseException e) {
            System.out.println("The corresponding username is already in use.");
        } catch (IOException e) {
            System.out.println("File could not be updated.");
        }
    }

    /**
     * Allows a user with the given username to check their activity. There are two activities they can check:
     * (1) Their three most recently traded items
     * (2) Their three most frequent trading partners
     * @param account account of the user who requested their activity
     * @throws IOException user input is invalid
     */
    public void showActivity(Account account) throws IOException {
        String username = account.getUsername();
        System.out.println("Which activity would you like to see?\n1. Most recent traded items\n2. Most frequent trading partners");
        String option = input.readLine();
        switch(option) {
            case "1":
                System.out.println("Here are the most recent items traded:");
                List<List<Integer>> threeRecentItems = tm.recentTradedItems(username);
                if (threeRecentItems.isEmpty()) System.out.println("Empty");
                else{
                    for (List<Integer> tradeItems : threeRecentItems){
                        if (tradeItems.size() == 1) System.out.println(tradeItems.get(0) + " was traded.");
                        else System.out.println(tradeItems.get(0) + " and " + tradeItems.get(1) + " were traded.");
                    }
                }
            case "2":
                System.out.println("Here are the most frequent trading partners:");
                Set<String> frequentTradingPartners = tm.frequentTradingPartners(username);
                if (frequentTradingPartners.isEmpty()) System.out.println("Empty");
                else {
                    for (String user : frequentTradingPartners) System.out.println(user);
                }
        }
    }

    /**
     * Shows the user what offers have been made to them.
     *
     * @param user the user who is checking their received offers
     */
    public void showOffers(Account user) {
        try {
            List<Integer> tradesReceived = am.getTradesReceived(user);
            int i = 0;
            while (i < tradesReceived.size()) {
                String userInput = getInput();
                System.out.println("Enter 1 to accept, 2 to deny, or 3 to decide later");
                System.out.println(tradesReceived.get(i)); // this only print trade ID (for now)
                switch (userInput) {
                    case "1":
                        System.out.println("Offer accepted!");
                        processAcceptedTrade(tradesReceived.get(i));
                        user.removeTradesReceived(tradesReceived.get(i));
                        break;
                    case "2":
                        System.out.println("Offer denied!");
                        user.removeTradesReceived(tradesReceived.get(i));
                        break;
                    case "3": System.out.println("Don't forget to check back soon!");
                        break;
                }
                i++;
            }
        } catch (TradeNumberException e) {
            System.out.println("There is an error in the system, the trade number should not exist.");
        } catch (AccountNotFoundException e) {
            System.out.println("There is an error in the system, the account should not exist.");
        }
    }

    /**
     * Shows the user what trades they currently have active
     *
     * @param user the user who is checking their active trades
     */
    public void showActiveTrades(Account user) {
        try {
            List<Integer> allTrades = new ArrayList<>(am.getTradesReceived(user));
            for (Integer allTrade : allTrades) {
                System.out.println("Trade ID: " + allTrade);
                System.out.println("Enter 1 to view next active trade, 2 to mark this trade as completed, 3 to cancel this trade");

                String userInput = getInput();


                switch (userInput) {
                    case "1":
                        // if the user enters 1 it'll just print out the next trade
                        break;
                    case "2":
                        System.out.println("Trade marked as completed!");
                        processCompletedTrade(allTrade);
                        user.removeTradesReceived(allTrade);
                        break;
                    case "3":
                        System.out.println("Trade has been cancelled");
                        processCancelledTrade(allTrade);
                        user.removeTradesReceived(allTrade);
                        break;
                }
            }
        //} catch (ItemNotFoundException e) {
            //System.out.println("There is an error in the trade inventory, the trade number should not exist.");
        } catch (TradeNumberException e) {
            System.out.println("There is an error in the item inventory, the item should not exist.");
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows all requests sent by users to have items added to their inventories to an admin. Admins can choose to
     * accept or deny this request.
     */
    public void showItemRequests () {
        System.out.println("Here are the current item requests. Press 1 to accept and 2 to deny: ");
        List<Item> unverifiedItemList = im.getUnverifiedItems();
        int i = 0;
        while (i < unverifiedItemList.size()) {
            try {
                System.out.println(unverifiedItemList.get(i));

                String userInput = getInput();
                if (userInput.equals("1")) {
                    im.verifyItem(unverifiedItemList.get(i).getID());
                    i++;
                } else if (userInput.equals("2")) {
                    im.removeItem(unverifiedItemList.get(i).getID());
                    i++;
                }
            } catch (IOException e) {
                System.out.println("Unable to read file. Please restart the program.");
            } catch (ItemNotFoundException ignored) {
            }
        }
    }

    /**
     * Shows an admin which users have borrowed more than they have lent, letting the admin to choose to freeze their account.
     */
    public void showFreezeUsers(){
        List<String> usernames = am.getUsernames();
        LocalDateTime now = LocalDateTime.now();
        for (String username : usernames){
            if (tm.checkUserShouldFreeze(username, tradeThreshold)) chooseToFreezeUser(username, " has received more than they have sent.");
            if (tm.checkUserWeeklyTrades(username, weeklyThreshold, now.minusDays(7))) chooseToFreezeUser(username, " has traded too much this week.");
            if (tm.checkUserIncompleteTrades(username, incompleteThreshold)) chooseToFreezeUser(username, " has too many incomplete trades.");
        }
        System.out.println("There are no more users that need to be checked.");
    }

    private void chooseToFreezeUser(String username, String reason){
        try {
            System.out.println("The user " + username + reason + " Would you like to freeze their account?");
            System.out.println("Click (1) for yes, (2) for no.");
            String option = getInput();
            switch (option){
                case "1":
                    am.freezeAccount(username);
                    System.out.println("Account " + username + " has been frozen.");
                    break;
                case "2":
                    System.out.println("Account " + username + " has not been frozen.");
                    break;
                default:
                    throw new InvalidOptionException();
            }
        } catch (AccountNotFoundException e) {
            System.out.println("No such account exists.");
        } catch (IOException e) {
            System.out.println("File could not be read from.");
        } catch (InvalidOptionException e) {
            System.out.println("Invalid option detected. Please try again.");
        }
    }

    /**
     * Updates the trade threshold.
     * @param newThreshold the new threshold to replace current one.
     */
    public void updateTradeThreshold(int newThreshold){ tradeThreshold = newThreshold; }

    /**
     * Updates the weekly threshold.
     * @param newThreshold the new threshold to replace current one.
     */
    public void updateWeeklyThreshold(int newThreshold){ weeklyThreshold = newThreshold; }

    /**
     * Updates the incomplete threshold.
     * @param newThreshold the new threshold to replace current one.
     */
    public void updateIncompleteThreshold(int newThreshold){ incompleteThreshold = newThreshold; }

    /**
     * Initializes a trade request meant to be sent by the user.
     *
     * @param user the user instance which is currently logged in
     */
    public void createRequest(Account user) {
        System.out.println("What kind of request you want to make? 1. One way trade 2. Two way trade");
        System.out.println("-----------------");
        int itemId;
        int tradeId;
        try {
            switch (Integer.parseInt(getInput())) {
                case 1:
                    System.out.println("Enter the id of the item you wish to trade:");
                    itemId = Integer.parseInt(getInput());
                    String usernameOfOwner = am.getItemOwner(itemId);
                    System.out.println("Which type of trade would you like to make? 1.Temporary trade " +
                            "2.Permanent trade");
                    switch (Integer.parseInt(getInput())) {
                        case 1:
                            tradeId = tm.newOneWayTrade(false,usernameOfOwner,user.getUsername(),itemId);
                            user.addTradesOffered(tradeId);
                            am.getAccount(usernameOfOwner).addTradesReceived(tradeId);
                            System.out.println("Made a temporary one way trade to "+ usernameOfOwner +" of item No."+
                                    itemId + " successfully!");
                            break;
                        case 2:
                            tradeId = tm.newOneWayTrade(true,usernameOfOwner,user.getUsername(),itemId);
                            user.addTradesOffered(tradeId);
                            am.getAccount(usernameOfOwner).addTradesReceived(tradeId);
                            System.out.println("Made a permanent one way trade to "+usernameOfOwner+" of item No."+
                                    itemId +" successfully!");
                            break;
                        default:
                            throw new InvalidOptionException();
                    }
                    break;
                case 2:
                    System.out.println("Enter the id of the item you wish to trade:");
                    itemId = Integer.parseInt(getInput());
                    usernameOfOwner = am.getItemOwner(itemId);
                    System.out.println("Enter the id of the your own item you wish to trade:");
                    int itemIdOwn = Integer.parseInt(getInput());
                    System.out.println("What kind of trade you want to make? 1.temporary trade 2.Permanent trade");
                    switch (Integer.parseInt(getInput())) {
                        case 1:
                            tradeId = tm.newTwoWayTrade(false,usernameOfOwner,itemId,user.getUsername(),itemIdOwn);
                            user.addTradesOffered(tradeId);
                            am.getAccount(usernameOfOwner).addTradesReceived(tradeId);
                            System.out.println("Made a temporary two way trade with "+usernameOfOwner+" of item No."+itemId+" and No. "+itemIdOwn+"successfully!");
                            break;
                        case 2:
                            tradeId = tm.newTwoWayTrade(true,usernameOfOwner,itemId,user.getUsername(),itemIdOwn);
                            user.addTradesOffered(tradeId);
                            am.getAccount(usernameOfOwner).addTradesReceived(tradeId);
                            System.out.println("Made a permanent two way trade with "+usernameOfOwner+" of item No."+itemId+" and No. "+itemIdOwn+"successfully!");
                            break;
                        default:
                            throw new InvalidOptionException();
                    }
                    break;
                default:
                    throw new InvalidOptionException();
            }
        }catch(InvalidOptionException e){
            System.out.println("Invalid Option detected. Please try again.");
            createRequest(user);
        }catch(AccountNotFoundException e){
            System.out.println("Cannot get the owner of the selected item.");
        }catch (IOException e) {
            System.out.println("Unable to read file. Please restart the program.");
        }
    }

    //Does not update the users wishlist
    //WILL RETURN ALL ITEMS BACK TO THEIR ORIGINAL OWNERS
    private void processCancelledTrade(int tradeNumber) throws TradeNumberException, AccountNotFoundException{
        Trade trade = tm.getTrade(tradeNumber);
        List<String> users = trade.getTraders();
        List<Integer> items = trade.getItemsOriginal();
        int i = 0;
        while(i < users.size()){
            if(!(items.get(i) == null)){
                Account a = am.getAccount(users.get(i));
                a.addInventory(items.get(i));

                //Remove the Trade

                a.removeTradesReceived(tradeNumber);
                a.removeTradesOffered(tradeNumber);
            }

            i++;
        }

        //



        //The below line is purely optional
        trade.setStatus(-1);
    }


    //Does not update the users wishlist
    //WILL CANCEL ALL TRADES THAT CONTAIN ONE OF THE ITEMS
    //WILL REMOVE ALL ITEMS BACK TO THEIR ORIGINAL OWNERS
    private void processAcceptedTrade(int tradeNumber) throws TradeNumberException, AccountNotFoundException {
        Trade trade = tm.getTrade(tradeNumber);
        List<String> users = trade.getTraders();
        List<Integer> items = trade.getItemsOriginal();
        int i = 0;

        while(i < users.size()){

            //Check if you need to do anything to the user

            if(!(items.get(i) == null)){
                Account a = am.getAccount(users.get(i));

                //Cancel Trades Offered by the Original User that contain the item

                List<Integer> tradesOffered = a.getTradesOffered();
                for(Integer x: tradesOffered){
                    Trade tx = tm.getTrade(x);
                    if(tx.getStatus() == 0){
                        if(tx.getItemsOriginal().contains(items.get(i))){
                            tx.setStatus(-1);
                        }
                    }
                }

                //Cancel Trades Received by the Original User that contain the item

                List<Integer> tradesReceived = a.getTradesReceived();
                for(Integer x: tradesReceived){
                    Trade tx = tm.getTrade(x);
                    if(tx.getStatus() == 0){
                        if(tx.getItemsOriginal().contains(items.get(i))) {
                            tx.setStatus(-1);
                        }
                    }
                }

                //Remove the item from the User

                a.removeInventory(items.get(i));
            }

            i++;
        }
        trade.setStatus(1);
    }


    //Does not update the users wishlist
    //WILL GIVE ALL ITEMS TO THEIR NEW OWNERS
    private void processCompletedTrade(int tradeNumber) throws TradeNumberException, AccountNotFoundException{
        Trade trade = tm.getTrade(tradeNumber);
        List<String> users = trade.getTraders();
        List<Integer> items = trade.getItemsFinal();
        int i = 0;
        while(i < users.size()){
            if(!(items.get(i) == null)){
                Account a = am.getAccount(users.get(i));
                a.addInventory(items.get(i));
            }

            i++;
        }
        //The below line is purely optional
        trade.setStatus(2);
    }


}
