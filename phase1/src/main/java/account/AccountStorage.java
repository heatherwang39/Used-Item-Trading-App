package main.java.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * AccountStorage is a use case class that manages logging, signing up, and many general Account related tasks.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class AccountStorage {

    Map<String, LoginAccount> accounts;

    /**
     * Class constructor.
     *
     * @param accounts Map containing LoginAccounts referenced by usernames
     */
    public AccountStorage(Map<String, LoginAccount> accounts) { this.accounts = accounts; }

    /**
     * Checks if username and password violate proper string format (are valid alphanumeric + dash/underscore strings).
     *
     * @param username input username
     * @param password input password
     * @return a boolean representing whether the combination is valid
     */
    protected boolean isInvalidLogin(String username, String password) {
        // Regex from: https://stackoverflow.com/questions/34916716
        Pattern p = Pattern.compile("^[a-zA-Z0-9-_.!@#$%^&*()]*$");
        return !p.matcher(username).matches() || !p.matcher(password).matches();
    }

    /**
     * Checks if username is used in another account.
     *
     * @param username input username
     * @return a boolean representing if username is in use
     */
    private boolean isUsernameInUse(String username) { return accounts.containsKey(username); }

    /**
     * Creates a GuestAccount.
     * 
     * @param username input username
     * @param password input password
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public Account createGuest(String username, String password) throws InvalidLoginException, UsernameInUseException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isUsernameInUse(username)) {throw new UsernameInUseException(); }
        return new GuestAccount();
    }

    /**
     * Creates a UserAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public Account createUser(String username, String password) throws InvalidLoginException,
            UsernameInUseException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isUsernameInUse(username)) {throw new UsernameInUseException(); }
        UserAccount user = new UserAccount(username, password);
        accounts.put(username, user);
        return user;
    }

    /**
     * Creates an AdminAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public Account createAdmin(String username, String password) throws InvalidLoginException, UsernameInUseException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isUsernameInUse(username)) {throw new UsernameInUseException(); }
        AdminAccount admin = new AdminAccount(username, password);
        accounts.put(username, admin);
        return admin;
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
     * Get all account usernames
     * @return copy of account usernames List
     */
    public List<String> getUsernames() {return new ArrayList<>(accounts.keySet()); }

    /**
     * If login matches an account in system
     * @return boolean
     */
    public boolean tryLogin(String username, String password){
        return accounts.containsKey(username) && accounts.get(username).isPassword(password);
    }
}
