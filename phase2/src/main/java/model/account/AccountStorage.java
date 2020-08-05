package main.java.model.account;

import main.java.model.Storage;

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
public class AccountStorage implements Storage {

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
     * Checks if email address is valid
     *
     * @param emailAddress input email
     * @return a boolean representing whether the email is valid
     */
    private boolean isInvalidEmail(String emailAddress) {

        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
                "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(" +
                "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
                "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08" +
                "\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))");
        return !p.matcher(emailAddress).matches();
    }

    /**
     * Checks if email address is used in another account.
     *
     * @param emailAddress input email
     * @return a boolean representing if emailAddress is in use
     */
    private boolean isEmailAddressInUse(String emailAddress) {
        for (LoginAccount account: accounts.values()) {
            if (account.getEmailAddress().equals(emailAddress)) return true;
        }
        return false;
    }

    /**
     * Wraps all checks for a new account being valid.
     *
     * @param username input username
     * @param password input password
     * @param emailAddress input email
     * @throws InvalidEmailAddressException emailAddress doesn't match regex
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailAddressInUseException emailAddress is in use
     */
    private void isValidAccount(String username, String password, String emailAddress) throws
            InvalidEmailAddressException, InvalidLoginException, UsernameInUseException, EmailAddressInUseException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isInvalidEmail(emailAddress)) {throw new InvalidEmailAddressException(); }
        if (isUsernameInUse(username)) {throw new UsernameInUseException(); }
        if (isEmailAddressInUse(username)) {throw new EmailAddressInUseException(); }
    }

    /**
     * Checks if username is used in another account.
     *
     * @param username input username
     * @return a boolean representing if username is in use
     */
    public boolean isUsernameInUse(String username) { return accounts.containsKey(username); }

    /**
     * Creates a UserAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @return Account username
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public String createUser(String username, String password, String emailAddress) throws InvalidLoginException,
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException {
        isValidAccount(username, password, emailAddress);
        UserAccount user = new UserAccount(username, password, emailAddress);
        accounts.put(username, user);
        return username;
    }

    /**
     * Creates an AdminAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @return Account username
     * @throws InvalidLoginException  login doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public String createAdmin(String username, String password, String emailAddress) throws InvalidLoginException,
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException {
        isValidAccount(username, password, emailAddress);
        AdminAccount admin = new AdminAccount(username, password, emailAddress);
        accounts.put(username, admin);
        return username;
    }

    /**
     * Get account by username
     * @param username input username
     * @return account with username
     * @throws AccountNotFoundException account not found under username
     */
    private Account getAccount(String username) throws AccountNotFoundException{
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


    /** Return the type of account
     *
     * @param username The username of the account
     * @return The type of account the given account is
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public String getType(String username) throws AccountNotFoundException{
        return getAccount(username).getType();
    }
}
