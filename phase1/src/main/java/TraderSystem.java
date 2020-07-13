package main.java;

import java.io.*;
import java.util.*;

public class TraderSystem {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";

    private final AccountStorage am;
    private final TradeStorage tm;
    private final ItemStorage im;
    private final BufferedReader input;
    private int tradeThreshold; //TODO: have configuration file set this

    private Account account;

    public TraderSystem(BufferedReader keyboard) throws IOException, ClassNotFoundException {

        input = keyboard;
        tm = new TradeStorage(tradesPath);
        im = new ItemStorage(itemsPath);
        am = new AccountStorage(accountsPath);

        try {
            am.createAdminAccount("admin", "admin", "admin@trader.org");
            am.createUserAccount("Sarah.alk", "123456", "sarah@trader.org", false);
        } catch (UsernameInUseException | EmailInUseException | InvalidEmailException | InvalidLoginException e) {
            //it's okay if this is not the first time the code is getting run
        }
    }


    public Account login() throws IOException {
        try {
            System.out.println("Username: ");
            String username = input.readLine();
            System.out.println("Password: ");
            String pw = input.readLine();

            if (am.tryLogin(username, pw)) {
                return am.getAccount(username);
            } else {
                throw new AccountNotFoundException();
            }
        } catch (AccountNotFoundException e) {
            System.out.println("Invalid Username or Password. Please try again.");
        }
        return login();

    }

    public Account register() throws IOException, AccountNotFoundException { //this doesn't work properly yet need to fix some stuff

        System.out.println("Enter email: "); //TODO make this method have less duplicate lines for errors
        String email = input.readLine();
        System.out.println("Enter Username: ");
        String username = input.readLine();
        System.out.println("Enter Password: ");
        String pw = input.readLine();

        try {
            if (!am.isUsernameInUse(username)) {
                am.createUserAccount(username, pw, email, false);
            } else {
                throw new UsernameInUseException();
            }

        } catch (UsernameInUseException e) {
            e.printStackTrace(); // TODO: check if I can just override .toString in the exception, instead of  writing the following print lines
            System.out.println("This Username is already in use. Please choose another Username.");
            System.out.println("Enter Username: ");
            username = System.in.toString();
        } catch (EmailInUseException e) {
            System.out.println("This email address is already in use. Please try again.");
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email. Please try again.");
        } catch (InvalidLoginException e) {
            System.out.println("Invalid Username or Password format. Please try again with alphanumerics and special characters only.");
        }
        return am.getAccount(username);
    }

    public void viewInventory() throws IOException {
        EntityDisplay ed = new EntityDisplay("Your Inventory");
        List<Integer> contraband = new ArrayList<>();
        try {
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
                        "been removed from your account.");
            }
            ed.display();
        } catch (AccountNotFoundException e) {
            System.out.println("Your account is missing/deleted from the system. Please restart this program.");
        }
    }


    public void addItem() throws IOException {
        System.out.println("Enter the name of the item:");
        String name = input.readLine();
        System.out.println("Enter a short description:");
        String description = input.readLine();
        System.out.println("Enter the type of item (Book, Clothing, misc.):");
        switch (input.readLine().toLowerCase()) {
            case "book":
                System.out.println("Enter the name of the author:");
                String author = input.readLine();
                try {
                    im.newBook(name, description, author);
                } catch (IOException e) {
                    System.out.println("Unable to read file. Please restart the program.");
                }
                System.out.println("Item was successfully added!");
            case "clothing":
                System.out.println("Enter the name of the brand:");
                String brand = input.readLine();
                try {
                    im.newClothing(name, description, brand);
                } catch (IOException e) {
                    System.out.println("Unable to read file. Please restart the program.");
                }
                System.out.println("Item was successfully added!");
            default:
                System.out.println("Type not recognize/is misc.");
                try {
                    im.newMiscItem(name, description);
                } catch (IOException e) {
                    System.out.println("Unable to read file. Please restart the program.");
                }
                System.out.println("Item was successfully added!");
        }
    }

    public void browseListings(Account user) {
        System.out.println("Here are all available item listings: ");
        List<Item> itemList = im.getVerifiedItems();
        int itemId;
        String usernameOfOwner;
        for (Item item : itemList) {
            System.out.println(item.getName() + ", id:" + item.getID() + "\n");
        }
        System.out.println("What kind of request you want to make? 1.one way trade 2.two way trade");
        try{
            switch (Integer.parseInt(input.readLine())) {
                case 1:
                    System.out.println("Enter the id of the item you wish to trade:");
                    itemId = Integer.parseInt(input.readLine());
                    usernameOfOwner = am.getItemOwner(itemId);
                    System.out.println("What kind of trade you want to make? 1.temporary trade 2.Permanent trade");
                    switch (Integer.parseInt(input.readLine())) {
                        case 1:
                            tm.newOneWayTrade(false,usernameOfOwner,user.getUsername(),itemId);
                            break;
                        case 2:
                            tm.newOneWayTrade(true,usernameOfOwner,user.getUsername(),itemId);
                            break;
                        default:
                            throw new InvalidOptionException();
                    }
                    break;
                case 2:
                    System.out.println("Enter the id of the item you wish to trade:");
                    itemId = Integer.parseInt(input.readLine());
                    usernameOfOwner = am.getItemOwner(itemId);
                    System.out.println("Enter the id of the your own item you wish to trade:");
                    int itemIdOwn = Integer.parseInt(input.readLine());
                    System.out.println("What kind of trade you want to make? 1.temporary trade 2.Permanent trade");
                    switch (Integer.parseInt(input.readLine())) {
                        case 1:
                            tm.newTwoWayTrade(false,usernameOfOwner,itemId,user.getUsername(),itemIdOwn);
                            break;
                        case 2:
                            tm.newTwoWayTrade(true,usernameOfOwner,itemId,user.getUsername(),itemIdOwn);
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
            browseListings(user);
        }catch(AccountNotFoundException e){
            System.out.println("Cannot get the owner of the selected item.");
        }catch (IOException e) {
            System.out.println("Unable to read file. Please restart the program.");
        }
    }



    public void addAdmin() throws IOException {
        System.out.println("Enter the username of the user you would like to promote to admin: ");
        String username = input.readLine();
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

    public void showActivity() {
        ; // save to implement this until phase 2 (potentially)
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
                String userInput = input.readLine();
                System.out.println("Enter 1 to accept, 2 to deny, or 3 to decide later");
                System.out.println(tradesReceived.get(i)); // this only print trade ID (for now)
                if (userInput.equals("1")) {
                    System.out.println("Offer accepted!");
                    processAcceptedTrade(tradesReceived.get(i));
                    user.removeTradesReceived(tradesReceived.get(i));
                } else if (userInput.equals("2")) {
                    System.out.println("Offer denied!");
                    user.removeTradesReceived(tradesReceived.get(i));
                } else if (userInput.equals("3")) {
                    System.out.println("Don't forget to check back soon!");
                }
                i++;
            }
        } catch (TradeNumberException e) {
            System.out.println("There is an error in the trade inventory, the trade number should not exist.");
        } catch (ItemNotFoundException e) {
            System.out.println("There is an error in the item inventory, the item should not exist.");
        } catch (AccountNotFoundException e) {
            System.out.println("There is an error in the item inventory, the account should not exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the user what trades they currently have active
     *
     * @param user the user who is checking their active trades
     */
    public void showActiveTrades(Account user) {
        try {
            StringBuilder sb = new StringBuilder("Here are your active trades");
            List<Integer> allTrades = new ArrayList<>(am.getTradesReceived(user));
            for (int i = 0; i < allTrades.size(); i++){
                System.out.println("Trade ID: " + allTrades.get(i));
                System.out.println("Enter 1 to view next active trade, 2 to mark this trade as completed, 3 to cancel this trade");
                String userInput = input.readLine();
                if (userInput.equals("1")){
                    ; // if the user enters 1 itll just print out the next trade
                } else if (userInput.equals("2")){
                    System.out.println("Trade marked as completed!");
                    processCompletedTrade(allTrades.get(i));
                    user.removeTradesReceieved(allTrades.get(i));
                } else if (userInput.equals("3")){
                    System.out.println("Trade has been cancelled");
                    processCancelledTrade(allTrades.get(i));
                    user.removeTradesReceived(allTrades.get(i));
                }
            }
        } catch (ItemNotFoundException e) {
            System.out.println("There is an error in the trade inventory, the trade number should not exist.");
        } catch (TradeNumberException e) {
            System.out.println("There is an error in the item inventory, the item should not exist.");
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showItemRequests () {
        System.out.println("Here are the current item requests. Press 1 to accept and 2 to deny: ");
        List<Item> unverifiedItemList = im.getUnverifiedItems();
        int i = 0;
        while (i < unverifiedItemList.size()) {
            try {
                System.out.println(unverifiedItemList.get(i));

                String userInput = input.readLine();
                if (userInput.equals("1")) {
                    im.verifyItem(unverifiedItemList.get(i).getID());
                    i++;
                } else if (userInput.equals("2")) {
                    im.removeItem(unverifiedItemList.get(i).getID());
                    i++;
                }
            } catch (IOException e) {
                System.out.println("Unable to read file. Please restart the program.");
            } catch (ItemNotFoundException e) {
                System.out.println("Unable to read file. Please restart the program.");
            }
        }
    }

    /**
     * Shows an admin which users have borrowed more than they have lent, letting the admin to choose to freeze their account.
     */
    public void showFreezeUsers(){
        List<String> usernames = am.getUsernames();
        for (String username : usernames){
            if (tm.checkUserShouldFreeze(username, tradeThreshold)) chooseToFreezeUser(username);
        }
        System.out.println("There are no more users that need to be checked.");
    }

    private void chooseToFreezeUser(String username){
        try {
            System.out.println("The user " + username + " has received more than they have sent. Would you like to freeze their account?");
            System.out.println("Click (1) for yes, (2) for no.");
            String option = input.readLine();
            switch (option){
                case "1":
                    am.freezeAccount(username);
                    System.out.println("Account " + username + " has been frozen.");
                case "2":
                    System.out.println("Account " + username + " has not been frozen.");
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
            }

            i++;
        }
        //The below line is purely optional
        trade.setStatus(-1);
    }


    //Does not update the users wishlist
    //WILL CANCEL ALL TRADES THAT CONTAIN ONE OF THE ITEMS
    //WILL REMOVE ALL ITEMS BACK TO THEIR ORIGINAL OWNERS
    private void processAcceptedTrade(int tradeNumber) throws TradeNumberException, AccountNotFoundException,
            ItemNotFoundException{
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
                    if(tx.getStatus().equals("")){
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
