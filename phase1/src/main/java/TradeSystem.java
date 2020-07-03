package main.java;

import java.io.IOException;

/**
 *
 */

public class TradeSystem {

    String tradesPath = "./resources/serializedtrades.ser";
    String itemsPath = "./resources/serializeditems.ser"; //TODO: check what the paths are for each file
    String accountsPath = "./resources/accounts.csv";

    private final TradeManager tm;
    private final ItemSystem im;
    private final AccountManager am;

    public TradeSystem() throws IOException, ClassNotFoundException {
        tm = new TradeManager(tradesPath);
        im = new ItemSystem(itemsPath);
        am = new AccountManager(accountsPath);
    }

    public void run() throws IOException, InvalidEmailException, InvalidLoginException, AccountNotFoundException,
            InvalidOptionException {

        am.createAdminAccount("admin", "admin", "trader@admin.org");

        Account currentUser;

        System.out.println("Welcome to Phase1 Project. Please choose any of the following by typing the option number.");
        System.out.println("1. Sign In    2.Register");
        String input = System.in.toString();
        if(!input.equals("1") && !input.equals("2")) {
            System.out.println("Invalid Option detected. Please try again."); //throw exception?
            throw new InvalidOptionException();
        }
        currentUser = login(am, input);

        //TODO: give options to view available/active/requested trade lists or user account info etc.

        }

    public Account login(AccountManager am, String input) throws AccountNotFoundException, InvalidEmailException,
            InvalidLoginException, IOException {
        if (input.equals("1")){
            return signIn(am);
        }
        if (input.equals("2")) {
            return register(am);
        }
        //return currentUser; //TODO: ?? i'm too tired to fix this return error rn
    }

    public Account signIn(AccountManager am) throws AccountNotFoundException {
        System.out.println("Username: ");
        String username = System.in.toString();
        System.out.println("Password: ");
        String pw = System.in.toString();
        Account currentUser;
        if (am.tryLogin(username, pw)){
            currentUser = am.getAccount(username);
        }else{
            System.out.println("Wrong Username or Password. Please try again.");
            currentUser = signIn(am);
        }
        return currentUser;
    }

    public Account register(AccountManager am) throws InvalidLoginException, InvalidEmailException, IOException,
            AccountNotFoundException {
        System.out.println("Enter Username: ");
        String username = System.in.toString();
        System.out.println("Enter Password: ");
        String pw = System.in.toString();
        System.out.println("Enter email: ");
        String email = System.in.toString();
        am.createUserAccount(username,pw, email, false);
        return am.getAccount(username);
    }


}
