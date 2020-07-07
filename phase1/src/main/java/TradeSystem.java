package main.java;

import java.io.IOException;

/**
 *
 */

public class TradeSystem {

    String tradesPath = "src/main/resources/serializedtrades.ser";
    String itemsPath = "src/main/resources/serializeditems.ser";
    String accountsPath = "src/main/resources/serializedaccounts.ser";

    private TradeManager tm = new TradeManager(tradesPath);
    private ItemManager im = new ItemManager(itemsPath);
    //private AccountManager am = new AccountManager(accountsPath);

    public TradeSystem() throws IOException, ClassNotFoundException {
    }


    public void run() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Phase1 Project. At anytime you may type 'exit' to quit.");
        Account currUser;
        System.out.println("Please choose any of the following by typing the option number.");
        try {
            AccountSystem as = new AccountSystem(accountsPath);
            System.out.println("1. Sign In    2.Register");
            String input = System.in.toString();
            currUser = as.login(input);
        } catch (InvalidOptionException e) {
            System.out.println("Invalid Option detected. Please try again.");
        } catch (InvalidLoginException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }


        //TODO: give options to view available/active/requested trade lists or user account info etc. UPDATE: create another presenter class

        }



}
