package main.java;

import java.io.*;

public class TraderClient {

    private TraderSystem ts;
    private BufferedReader keyboard;

    public void run() {
        System.out.println("Welcome to Trader. At anytime you may type 'exit' to quit.\n" +
                "Please choose any of the following by typing the option number.");
        Account currUser;

        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) { //from readWrite lecture
            this.keyboard = keyboard;
            ts = new TraderSystem(keyboard);
            System.out.println("1. Sign In\n2. Register");
            currUser = login();
            System.out.println("Login Successful. Welcome to Trader, " + currUser.getUsername());
            layerTwoMenu(currUser);
            layerTwo(currUser);
        } catch (IOException e) {
            System.out.println("Files could not be read from.");
        } catch (ClassNotFoundException e) {
            System.out.println("Files are corrupt/improper.");
        }
    }

    /**
     * returns an Account associated with the input info of the user after signing in to an existing
     * the input options:
     *      1 represents option to Sign In to an existing account
     *      2 represents option to Register a new account
     * @return  returns the logged in User Account
     * @throws IOException file could no tbe written to after adding an account
     */
    public Account login() throws IOException {
        String i = keyboard.readLine();
        try {
            if (i.equals("1")) {
                return ts.signIn();
            }
            if (i.equals("2")) {
                return ts.register();
            } else {
                throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
            return login();
        }
    }

    public void layerTwoMenu(Account user){
        System.out.println("1. View Account Information\n2. Add Items\n3. Browse Listings\n4. Create Request\n" +
                "5. My Activity\n6. Offers\n7. Active Trades\n8. Sign out");
        if (user.isAdmin()){ System.out.println("9. Admin Options"); }
    }

    public void layerTwo(Account user) throws IOException { //TODO: give options to view available/active/requested trade lists or user account info etc.
        String option = keyboard.readLine();
        try {
            switch (option) {
                case "1":
                    ts.accountInformation(user);
                    break;
                case "2":
                    ts.addItem(user);
                    break;
                case "3":
                    ts.browseListings();
                    break;
                case "4":
                    ts.createRequest(user);
                    break;
                case "5":
                    ts.showActivity();
                    break;
                case "6":
                    ts.showOffers(user);
                    break;
                case "7":
                    ts.showActiveTrades(user);
                    break;
                case "8":
                    signOut();
                case "9":
                    if (user.isAdmin()){ adminOptions(user); }
                    else { throw new InvalidOptionException(); }
                    break;
                case "0":
                    layerTwoMenu(user);
                    break;
                default:
                    throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid option detected. Please try again, or type 0 to print menu options.");
        } finally {
            layerTwo(user);
        }

    }

    private void adminOptions(Account admin) throws IOException {
        try {
            System.out.println("1. View Requests\n2. Freeze Accounts\n3. Update Trade Threshold\n4. Add new Admins");
            switch (keyboard.readLine()) {
                case "1":
                    // print an arraylist of all user requests of items to be added to their inventory,either accept or deny
                    ts.showItemRequests();
                    break;
                case "2":
                    // get and print out users the system thinks the admin should freeze
                    // this should take the input for each individual user, and accept either a yes or no
                    ts.showFreezeUsers();
                    break;
                case "3":
                    System.out.println("Which threshold do you want to update? 1, Trade\n2. Weekly\n3. Incomplete");
                    switch (keyboard.readLine()) {
                        case "1":
                            System.out.println("Enter the new trading threshold: ");
                            ts.updateTradeThreshold(Integer.parseInt(keyboard.readLine()));
                        case "2":
                            System.out.println("Enter the new weekly threshold: ");
                            ts.updateWeeklyThreshold(Integer.parseInt(keyboard.readLine()));
                        case "3":
                            System.out.println("Enter the new incomplete threshold: ");
                            ts.updateIncompleteThreshold(Integer.parseInt(keyboard.readLine()));
                    }
                    break;
                case "4":
                    ts.addAdmin();
                    break;
                default:
                    throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
            adminOptions(admin);
        }
    }

    public void signOut(){
        //TODO: implement this later.
    }

}
