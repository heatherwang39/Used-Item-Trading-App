package main.java;

import java.io.*;

/**
 *
 */

public class TraderClient {

    String tradesPath = "phase1/src/main/resources/serializedtrades.ser";
    String itemsPath = "phase1/src/main/resources/serializeditems.ser";
    String accountsPath = "phase1/src/main/resources/serializedaccounts.ser";

    private TradeManager tm = new TradeManager(tradesPath);
    private ItemManager im = new ItemManager(itemsPath);

    public TraderClient() throws IOException, ClassNotFoundException {
    }


    public void run() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Phase1 Project. At anytime you may type 'exit' to quit.");
        Account currUser;
        System.out.println("Please choose any of the following by typing the option number.");
        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) { //from readWrite lecture
            TraderSystem as = new TraderSystem(accountsPath);
            System.out.println("1. Sign In    2.Register");
            String input = keyboard.readLine();
            currUser = as.login(input);
            System.out.println("Login Successful. Welcome to Trader, " + currUser.getUsername());
            System.out.println("1. View Account information    2. View trade listings    3. View my trade activity");
            if (currUser.isAdmin()){
                System.out.println("4. Show messages    5. Edit Customer Account information");
            }
        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        } catch (AccountNotFoundException e) { //needs edit/deleted later
            e.printStackTrace();
        }


        //TODO: give options to view available/active/requested trade lists or user account info etc. UPDATE: create another presenter class

        }



}
