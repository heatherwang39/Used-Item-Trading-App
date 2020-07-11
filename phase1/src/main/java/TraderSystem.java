package main.java;

import java.io.*;

public class TraderSystem {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";
    String itemRequestsPath = "phase1/src/main/resources/serializeditemrequests.ser";

    private final AccountManager am;
    private final TradeManager tm;
    private final ItemManager im;
    private final BufferedReader input;

    private Account account;

    public TraderSystem(BufferedReader keyboard) throws IOException, ClassNotFoundException {

        input = keyboard;
        tm = new TradeManager(tradesPath);
        im = new ItemManager(itemsPath);
        am = new AccountManager(accountsPath);

        try {
            am.createAdminAccount("admin", "admin", "admin@trader.org");
            am.createUserAccount("Sarah.alk", "123456", "sarah@trader.org", false);
        } catch (UsernameInUseException | EmailInUseException | InvalidEmailException | InvalidLoginException e){
            //it's okay if this is not the first time the code is getting run
        }
    }


    public Account login() throws IOException {
        try {
            System.out.println("Username: ");
            String username = input.readLine();
            System.out.println("Password: ");
            String pw = input.readLine();

            if (am.tryLogin(username, pw)){
                return am.getAccount(username);
            } else { throw new AccountNotFoundException(); }
        } catch (AccountNotFoundException e){
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
        } catch (EmailInUseException e){
            System.out.println("This email address is already in use. Please try again.");
        } catch (InvalidEmailException e){
            System.out.println("Invalid email. Please try again.");
        } catch (InvalidLoginException e){
            System.out.println("Invalid Username or Password format. Please try again with alphanumerics and special characters only.");
        }
        return am.getAccount(username);
    }

    public void viewInventory(){
        ;
    }


    public void addItems() {
        System.out.println("Enter the name of the item you wish to add to your inventory: ");
        String item = input.readLine();
        addItem(item);
    }

    public void browseListings() {
        System.out.println("Here are all available item listings: ");
        Item<List> itemList = getVerifiedItems();
        for (int i = 0; i < itemList.size(); i++){
            System.out.println(itemList.get(i) + "\n");
        }
        // Here there should be an extension for the user to either do a Trade Request or a Borrow Request for an item
    }

    public void addAdmin() throws IOException{
        //
        System.out.println("Enter the username of the user you would like to promote to admin: ");
        String username = input.readLine();
        try {
            Account user = am.getAccount(username);
            if (user.isAdmin()) System.out.println("User with corresponding username is already an admin");
            else {
                am.removeUserAccount(username);
                am.createAdminAccount(username, user.getPassword(), user.getEmail());
            }
        } catch (AccountNotFoundException e){
            System.out.println("User with corresponding username was not found in the database");
        } catch (InvalidLoginException e) {
            System.out.println("The corresponding username is invalid");
        } catch (InvalidEmailException e) {
            System.out.println("The corresponding email is invalid");
        } catch (EmailInUseException e) {
            System.out.println("The corresponding email is already in use");
        } catch (UsernameInUseException e) {
            System.out.println("The corresponding username is already in use");
        } catch (IOException e) {
            System.out.println("File could not be updated");
        }
    }


    public void showActivity() {
        ;
    }

    public void showOffers(){
        ;
    }

    public void showActiveTrades(){
        ;
    }

    public void showItemRequests(){
        System.out.println("Here are the current item requests. Press 1 to accept and 2 to deny: ");
        List<Item> unverifiedItemList = getUnverifiedItems();
        int i = 0;

        while (i < unverifiedItemList.size()){
            try {
                System.out.println(unverifiedItemList.get(i));

                String input = input.readLine();
                if (input.equals("1")) {
                    verifyItem(unverifiedItemList.get(i));
                    i++;
                } else if (input.equals("2")) {
                    removeItem(unverifiedItemList.get(i));
                    i++;
                }
            } catch(InvalidOptionException e){
                System.out.println("Option not valid");

            }
        }
    }

    public void showFreezeUsers(){
        ;
    }

    public void updateTradeThreshold(int newThreshold){
        ;
    }

}
