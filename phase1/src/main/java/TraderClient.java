package main.java;

import java.io.*;

public class TraderClient {

    private TraderSystem ts;
    private BufferedReader keyboard;
    private Account currUser;

    public void run() {
        System.out.println("Welcome to Trader. At anytime you may type 'exit' to quit.\n" +
                "Please choose any of the following by typing the option number.");

        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) { //from readWrite lecture
            this.keyboard = keyboard;
            ts = new TraderSystem(keyboard);
            currUser = login();
            System.out.println("Login Successful. Welcome to Trader, " + currUser.getUsername());
            layerTwo();
        } catch (IOException e) {
            System.out.println("Files could not be read from.");
        } catch (ClassNotFoundException e) {
            System.out.println("Files are corrupt/improper.");
        }
    }

    /**
     * returns an Account associated with the input info of the user after signing in to an existing
     * the input options:
     *       1 represents option to Sign In to an existing account
     *       2 represents option to Register a new account
     * sets currUser to the logged in User Account
     * @throws IOException file could no tbe written to after adding an account
     */
    public Account login() throws IOException {
        System.out.println("1. Sign In\n2. Register");
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

    /** Prints out the layer two UI.
     */
    public void layerTwoMenu() {
        System.out.println("1. View Account Information\n2. Add Items\n3. Browse Listings\n4. Create Request\n" +
                "5. My Activity\n6. Offers\n7. Active Trades\n8. Sign out");
        if (currUser.isAdmin()) {
            System.out.println("9. Admin Options");
        }
    }

    /** Detects user input from the second layer, and calls the corresponding method from TraderSystem.
     *
     * currUser user: The instance of the user which is currently logged in.
     * @throws IOException if the user enters an invalid option
     */
    public void layerTwo() throws IOException {
        layerTwoMenu();
        String option = keyboard.readLine();
        try {
            switch (option) {
                case "1":
                    accountInformation();
                    break;
                case "2":
                    ts.addItem(currUser);
                    break;
                case "3":
                    ts.browseListings();
                    break;
                case "4":
                    ts.createRequest(currUser);
                    break;
                case "5":
                    ts.showActivity(currUser);
                    break;
                case "6":
                    ts.showOffers(currUser);
                    break;
                case "7":
                    ts.showActiveTrades(currUser);
                    break;
                case "8":
                    signOut();
                case "9":
                    if (currUser.isAdmin()) { adminOptions(); }
                    else { throw new InvalidOptionException(); }
                    break;
                case "0":
                    layerTwoMenu();
                    break;
                default:
                    throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid option detected. Please try again, or type 0 to print menu options again.");
            layerTwo();
        }

    }


    /** A collection of features exclusive to admin users. Admins choose which feature they wish to access by
     * entering the corresponding numeric option.
     *
     * currUser admin: The instance of the admin user which is currently logged in.
     * @throws IOException if the user inputs an invalid option
     */
        private void adminOptions() throws IOException {
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
            adminOptions();
        }
    }

    /**
     * Prints out information about account
     * @throws IOException
     */
    public void accountInformation() throws IOException {
        Account account = currUser;
        System.out.println("Username: " + account.getUsername());
        System.out.println("Email Address: " + account.getEmail());
        System.out.println("-----------------");
        try {
            ts.viewInventory(account);
            System.out.println("-----------------");
            ts.viewWishlist(account);
        } catch (AccountNotFoundException e) {
            System.out.println("Your account is missing/deleted from the system. Please restart this program.");
        }
    }

    public void signOut() throws IOException {
        System.out.println("Please confirm Sign Out. \n1. Confirm \n2. Decline");
        try {
            switch (keyboard.readLine()) {
                case "1":
                    String prevUsername = currUser.getUsername();
                    currUser = null;
                    System.out.println("Signed Out Successfully. See you soon, " + prevUsername);
                case "2":
                    layerTwo();
                default:
                    throw new InvalidOptionException();
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
            signOut();
        }
    }

}
