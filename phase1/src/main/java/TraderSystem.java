package main.java;

import java.io.*;

public class TraderSystem {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";

    private final AccountManager am;
    private final TradeManager tm;
    private final ItemManager im;
    private final BufferedReader input;

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


    public Account signIn() throws IOException {
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
        return signIn();

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


    public void addItems() {
        ;
    }

    public void browseListings() {
        ;
    }

    public void addAdmin() {

        System.out.println("Enter the username of the user you would like to promote to Admin status: ");
        // search in existing users, find if they exist
        ;
        // if they exist, promote them to admin
        // else:
        System.out.println("User with corresponding username was not found in the database");
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
        ;
    }

    public void showFreezeUsers(){
        ;
    }

    public void updateTradeThreshold(int newThreshold){
        ;
    }

}
