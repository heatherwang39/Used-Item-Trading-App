package main.java;

import java.io.*;

/**
 *
 */

public class TraderClient {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";


    public void run() throws ClassNotFoundException {
        System.out.println("Welcome to Trader. At anytime you may type 'exit' to quit.\n" +
                "Please choose any of the following by typing the option number.");
        Account currUser;

        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) { //from readWrite lecture
            TraderSystem ts = new TraderSystem(tradesPath, itemsPath, accountsPath);
            System.out.println("1. Sign In\n2.Register");
            String input = keyboard.readLine();
            currUser = ts.login(input);
            System.out.println("Login Successful. Welcome to Trader, " + currUser.getUsername());
            layerTwo(ts, currUser);
            if (currUser.isAdmin()){
                adminOptions(ts);
            }


        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        } catch (AccountNotFoundException e) { //TODO: IMPORTANT needs edit/deleted later!!!!
            e.printStackTrace();
        }


        //TODO: give options to view available/active/requested trade lists or user account info etc. UPDATE: create another presenter class

    }


    public void layerTwo(TraderSystem ts, Account user) throws IOException, InvalidOptionException {
        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("1. View Account Information\n2. Add Items\n3. Browse Listings\n4. My Activity\n" +
                    "5. Offers\n6. Active Trades");

            if (keyboard.readLine().equals("1")) {
                System.out.println("Username: " + user.getUsername() + "\nEmail" + user.getEmail() + "\nInventory: " +
                        user.getInventory() + "\nWishlist" + user.getWishlist());
            } else if (keyboard.readLine().equals("2")) {
                addItems();
            } else if (keyboard.readLine().equals("3")) {
                browseListings();
            } else if (keyboard.readLine().equals("4")) {
                ;
            } else if (keyboard.readLine().equals("5")) {
                ;
            } else if (keyboard.readLine().equals("6")){
                ;
            } else { throw new InvalidOptionException(); }

        }
    }

    public void adminOptions(TraderSystem ts) throws IOException, InvalidOptionException {
        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("7. Admin Options");
            if (keyboard.readLine().equals("7")) {
                System.out.println("1. View Requests\n2. Freeze Accounts\n3. Update Trade Threshold\n4. Add Admins");
                switch (keyboard.readLine()) {
                    case "1":
                        // print an arraylist of all user requests of items to be added to their inventory,either accept or deny
                        ;
                        break;
                    case "2":
                        // get and print out users the system thinks the admin should freeze
                        // this should take the input for each individual user, and accept either a yes or no
                        ;
                        break;
                    case "3":
                        System.out.println("Enter the new trading threshold: ");
                        break;
                    case "4":
                        // add new admins
                        ;
                        break;
                    default:
                        throw new InvalidOptionException();
                }
            }



        }
    }

    public void addItems(){
        ;
    }

    public void browseListings(){
        ;
    }



}
