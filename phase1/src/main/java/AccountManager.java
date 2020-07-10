package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Use case class for managing login info, account creation, and getting and mutating accounts.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class AccountManager {

    Map<String, Account> accounts;
    String path;

    /**
     * Class constructor
     * @param path Path of serialized accounts
     * @throws IOException if file can't be read
     * @throws ClassNotFoundException if serialized class doesn't exist
     */
    public AccountManager(String path) throws IOException, ClassNotFoundException {
        accounts = new HashMap<>();
        this.path = path;
        File file = new File(path);
        if (file.exists()) {
            try {readFromFile(path);} catch(EOFException e) {}
        } else {
            file.createNewFile();
        }
    }

    /**
     * Deserialize accounts data
     * @param path Path of serialized accounts
     * @throws IOException if file can't be read
     * @throws ClassNotFoundException if serialized class doesn't exist
     */
    private void readFromFile(String path) throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        accounts = (Map<String, Account>) input.readObject();
        input.close();
    }

    /**
     * Class constructor
     * @param path Path of serialized accounts
     * @throws IOException if file can't be written
     */
    private void saveToFile(String path) throws IOException {
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(accounts);
        output.close();
    }

    /**
     * Checks if username and password are valid alphanumeric + dash/underscore strings
     * @param username input username
     * @param password input password
     * @return a boolean representing whether the combination is valid
     */
    private boolean isInvalidLogin(String username, String password) {
        // Regex from: https://stackoverflow.com/questions/34916716
        Pattern p = Pattern.compile("^[a-zA-Z0-9-_]$");
        return !p.matcher(username).matches() || !p.matcher(password).matches();
    }

    /**
     * Checks if email address is valid
     * @param email input email
     * @return a boolean representing whether the email is valid
     */
    private boolean isInvalidEmail(String email) {

        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
                "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(" +
                "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
                "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08" +
                "\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))");
        return !p.matcher(email).matches();
    }

    /**
     * Checks if username is used in another account
     * @param username input username
     * @return a boolean representing if username is in use
     */
    public boolean isUsernameInUse(String username) { return accounts.containsKey(username); }

    /**
     * Checks if email is used in another account
     * @param email input email
     * @return a boolean representing if email is in use
     */
    private boolean isEmailInUse(String email) {
        for (Account account: accounts.values()) {
            if (account.getEmail().equals(email)) return true;
        }
        return false;
    }

    /**
     * Wraps all checks for a new account being valid
     * @param username input username
     * @param password input password
     * @param email input email
     * @throws InvalidEmailException email doesn't match regex
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailInUseException email is in use
     */
    private void isValidAccount(String username, String password, String email) throws InvalidEmailException,
            InvalidLoginException, UsernameInUseException, EmailInUseException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isInvalidEmail(email)) {throw new InvalidEmailException(); }
        if (isUsernameInUse(username)) {throw new UsernameInUseException(); }
        if (isEmailInUse(username)) {throw new EmailInUseException(); }
    }

    /**
     * Creates a user account
     * @param username input username
     * @param password input password
     * @param email input email
     * @throws InvalidEmailException email doesn't match regex
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailInUseException email is in use
     */
    public void createUserAccount(String username, String password, String email, boolean isFrozen) throws IOException,
            InvalidLoginException, InvalidEmailException, UsernameInUseException, EmailInUseException {
        isValidAccount(username, password, email);
        UserAccount user = new UserAccount(username, password, email, isFrozen);
        accounts.put(username, user);
        saveToFile(path);
    }

    /**
     * Creates an admin account
     * @param username input username
     * @param password input password
     * @param email input email
     * @throws InvalidEmailException email doesn't match regex
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailInUseException email is in use
     */
    public void createAdminAccount(String username, String password, String email) throws IOException,
            EmailInUseException, InvalidEmailException, InvalidLoginException, UsernameInUseException {
        isValidAccount(username, password, email);
        AdminAccount user = new AdminAccount(username, password, email);
        accounts.put(username, user);
        saveToFile(path);
    }

    /**
     * Sets a user account to be be frozen
     * @param username input username
     * @throws AccountNotFoundException account not found under username
     * @throws ClassCastException account is an admin account
     */
    public void freezeAccount(String username) throws AccountNotFoundException, ClassCastException{
        ((UserAccount)getAccount(username)).freeze();
    }

    /**
     * Sets a user account to be be unfrozen
     * @param username input username
     * @throws AccountNotFoundException account not found under username
     * @throws ClassCastException account is an admin account
     */
    public void unfreezeAccount(String username) throws AccountNotFoundException, ClassCastException{
        ((UserAccount)getAccount(username)).unfreeze();
    }

    /**
     * Get account by username
     * @param username input username
     * @return account with username
     * @throws AccountNotFoundException account not found under username
     */
    public Account getAccount(String username) throws AccountNotFoundException{
        if (accounts.containsKey(username)){
            return accounts.get(username);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Get all accounts
     * @return copy of accounts List
     */
    public List<Account> getAccounts() { return new ArrayList<>(accounts.values()); }

    /**
     * If login matches an account in system
     * @return boolean
     */
    public boolean tryLogin(String username, String password){
        return accounts.containsKey(username) && accounts.get(username).isPassword(password);
    }

    /**
     * Add item ID to account inventory
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addInventory(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).addInventory(itemID);
        saveToFile(path);
    }

    /**
     * Add item IDs to account inventory
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addInventory(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.addInventory(id);}
        saveToFile(path);
    }

    /**
     * Add item ID to account wishlist
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addWishlist(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).addWishlist(itemID);
        saveToFile(path);
    }

    /**
     * Add item IDs to account wishlist
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addWishlist(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.addWishlist(id);}
        saveToFile(path);
    }

    /**
     * Remove item ID from account inventory
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeInventory(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).removeInventory(itemID);
        saveToFile(path);
    }

    /**
     * Remove item IDs from account inventory
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeInventory(String username, List<Integer> itemIDs) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.removeInventory(id);}
        saveToFile(path);
    }

    /**
     * Remove item ID from account wishlist
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeWishlist(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).removeWishlist(itemID);
        saveToFile(path);
    }

    /**
     * Remove item IDs from account wishlist
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeWishlist(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id : itemIDs) {
            account.removeWishlist(id);
        }
        saveToFile(path);
    }

    /**
     * Add trade ID to trades offered
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addTradesOffered(String username, int tradeNumber) throws AccountNotFoundException, IOException {
        getAccount(username).addTradesOffered(tradeNumber);
        saveToFile(path);
    }

    /**
     * Add trade IDs to trades offered
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addTradesOffered(String username, List<Integer> tradeNumbers) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int number: tradeNumbers) { account.addTradesOffered(number);}
        saveToFile(path);
    }

    /**
     * Add trade ID to trades received
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addTradesReceived(String username, int tradeNumber) throws AccountNotFoundException, IOException {
        getAccount(username).addTradesReceived(tradeNumber);
        saveToFile(path);
    }

    /**
     * Add trade IDs to trades received
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void addTradesReceived(String username, List<Integer> tradeNumbers) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int number: tradeNumbers) { account.addTradesReceived(number);}
        saveToFile(path);
    }

    /**
     * Remove trade ID from trades offered
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeTradesOffered(String username, int tradeNumber) throws AccountNotFoundException, IOException {
        getAccount(username).removeTradesOffered(tradeNumber);
        saveToFile(path);
    }

    /**
     * Remove trade IDs from trades offered
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeTradesOffered(String username, List<Integer> tradeNumbers) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int number: tradeNumbers) { account.removeTradesOffered(number);}
        saveToFile(path);
    }

    /**
     * Remove trade ID from trades received
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeTradesReceived(String username, int tradeNumber) throws AccountNotFoundException, IOException {
        getAccount(username).removeTradesReceived(tradeNumber);
        saveToFile(path);
    }

    /**
     * Remove trade IDs from trades received
     * @throws AccountNotFoundException account not found under username
     * @throws IOException if file can't be saved to
     */
    public void removeTradesReceived(String username, List<Integer> tradeNumbers) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int number : tradeNumbers) {
            account.removeTradesReceived(number);
        }
        saveToFile(path);
    }
}
