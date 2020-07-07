package main.java;

import java.io.IOException;

/**
 *
 */

public class TradeSystem {

    String tradesPath = "./resources/serializedtrades.ser";
    String itemsPath = "./resources/serializeditems.ser"; //TODO: check what the paths are for each file
    String accountsPath = "./resources/serializedaccounts.ser";

    private TradeManager tm = new TradeManager(tradesPath);
    private ItemSystem im = new ItemSystem(itemsPath);
    private AccountManager am = new AccountManager(accountsPath);

    public TradeSystem() throws IOException, ClassNotFoundException {
    }


    /**
     *
     * @throws IOException
     * @throws InvalidEmailException
     * @throws InvalidLoginException
     * @throws AccountNotFoundException
     * @throws InvalidOptionException
     */
    public void run() throws IOException, InvalidEmailException, InvalidLoginException, AccountNotFoundException,
            InvalidOptionException {

        try {
            am.createAdminAccount("admin", "admin", "admin@trader.org");
            am.createUserAccount("Sarah.alk", "123456", "sarah@trader.org", false);
        } catch (UsernameInUseException | EmailInUseException e){
            //it's okay if this is not the first time the code is getting run
        }

        Account currentUser;
        System.out.println("Welcome to Phase1 Project. Please choose any of the following by typing the option number.");
        System.out.println("1. Sign In    2.Register");
        String input = System.in.toString();

        try{
            if(!input.equals("1") && !input.equals("2")) {
                throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
                System.out.println("Invalid Option detected. Please try again.");
            }

        currentUser = login(am, input);

        //TODO: give options to view available/active/requested trade lists or user account info etc. UPDATE: create another presenter class

        }

    /**
     * returns a User Account associated with the input info of the user after signing in to an existing
     * @param am the Account Manager class
     * @param input the input options:
     *              1 represents option to Sign In to an existing account
     *              2 represents option to Register a new account
     * @return  returns the logged in User Account
     * @throws AccountNotFoundException
     * @throws InvalidLoginException
     * @throws IOException
     */
    public Account login(AccountManager am, String input) throws AccountNotFoundException,
            InvalidLoginException, IOException, InvalidOptionException {
        if (input.equals("1")){
            return signIn(am);
        }
        if (input.equals("2")) {
            return register(am);
        }else{
            throw new InvalidOptionException();
        }
    }

    public Account signIn(AccountManager am) {
        try {
            System.out.println("Username: ");
            String username = System.in.toString();
            System.out.println("Password: ");
            String pw = System.in.toString();

            if (am.tryLogin(username, pw)){
                return am.getAccount(username);
            }
        } catch (AccountNotFoundException e){
                System.out.println("Invalid Username or Password. Please try again.");
                //return signIn(am);
            }
        return signIn(am);

    }

    public Account register(AccountManager am) throws InvalidLoginException, IOException, AccountNotFoundException {

        System.out.println("Enter email: ");
        String email = System.in.toString();
        System.out.println("Enter Username: ");
        String username = System.in.toString();
        System.out.println("Enter Password: ");
        String pw = System.in.toString();

        try {
            am.createUserAccount(username,pw, email, false);

        } catch (UsernameInUseException e) {
            e.printStackTrace(); // TODO: check if I can just override .toString in the exception, instead of  writing the following print lines
            System.out.println("This Username is already in use. Please try again.");
            System.out.println("Enter Username: ");
            username = System.in.toString();
            return am.getAccount(username);
        } catch (EmailInUseException e){
            System.out.println("This email address is already in use. Please try again.");
        } catch (InvalidEmailException e){
            System.out.println("Invalid email. Please try again.");
        }

        return am.getAccount(username);
    }


}
