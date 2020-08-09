package main.java.model.account;

import main.java.model.Storage;
import main.java.model.status.StatusObserver;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AccountStorage implements Storage, StatusObserver {

    private Map<String, LoginAccount> accounts;

    @Override
    public Object getNewStorageData() {
        Map<String, LoginAccount> map = new HashMap<>();
        map.put("admin", new AdminAccount("admin", "admin", "(none)"));
        return map;
    }

    @Override
    public void setStorageData(Object accounts) {
        this.accounts = (Map<String, LoginAccount>) accounts;
    }

    /**
     * Checks if username violate requirements.
     *
     * @param username input username
     * @return a boolean representing whether the combination is valid
     */
    protected boolean isInvalidUsername(String username) {
        // Regex from: https://stackoverflow.com/questions/1221985/
        Pattern p = Pattern.compile("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");
        return !p.matcher(username).matches();
    }

    /**
     * Checks if username violate requirements.
     *
     * @param username input username
     * @return a boolean representing whether the combination is valid
     */
    protected boolean isInvalidPassword(String username) {
        // Regex from: https://stackoverflow.com/questions/19605150
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        return !p.matcher(username).matches();
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
     * @throws InvalidUsernameException  username doesn't match regex
     * @throws InvalidPasswordException  password doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailAddressInUseException emailAddress is in use
     */
    private void isValidAccount(String username, String password, String emailAddress) throws
            InvalidEmailAddressException, UsernameInUseException, EmailAddressInUseException, InvalidUsernameException, InvalidPasswordException {
        if (isInvalidUsername(username)){ throw new InvalidUsernameException(); }
        if (isInvalidPassword(password)){ throw new InvalidPasswordException(); }
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
     * @throws InvalidUsernameException  username doesn't match regex
     * @throws InvalidPasswordException  password doesn't match regex
     * @throws UsernameInUseException username is in use
     */
    public void createUser(String username, String password, String emailAddress) throws
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException, InvalidPasswordException, InvalidUsernameException {
        isValidAccount(username, password, emailAddress);
        UserAccount user = new UserAccount(username, password, emailAddress);
        accounts.put(username, user);
    }

    /**
     * Creates an AdminAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @param emailAddress input email
     * @throws InvalidUsernameException  username doesn't match regex
     * @throws InvalidPasswordException  password doesn't match regex
     * @throws InvalidEmailAddressException email doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailAddressInUseException email address is in use
     */
    public void createAdmin(String username, String password, String emailAddress) throws
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException, InvalidPasswordException, InvalidUsernameException {
        isValidAccount(username, password, emailAddress);
        AdminAccount admin = new AdminAccount(username, password, emailAddress);
        accounts.put(username, admin);
    }

    /**
     * Get account by username
     * @param username input username
     * @return account with username
     * @throws AccountNotFoundException account not found under username
     */
    private LoginAccount getAccount(String username) throws AccountNotFoundException{
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
    private List<Account> getAccounts() { return new ArrayList<>(accounts.values()); }

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

    /**
     * Return all the usernames of users who are not admins
     *
     * @return The list of usernames of users
     */
    public List<String> getUsers() {
        List<String> allUsers = new ArrayList<>();
        List<Account> allAccounts = getAccounts();
        for (Account account : allAccounts) {
            if (!account.getType().equals("ADMIN")){
                allUsers.add(account.getUsername());
            }
        }
        return allUsers;
    }

    /** Return all the usernames of admins
     *
     *
     * @return The list of usernames of admins
     */
    public List<String> getAdmins(){
        List<String> allAdmins = new ArrayList<>();
        List<Account> allAccounts = getAccounts();
        for(Account account:allAccounts){
            if(account.getType().equals("ADMIN")){
                allAdmins.add(account.getUsername());
            }
        }
        return allAdmins;
    }

    public String getPassword(String username) throws AccountNotFoundException {
        LoginAccount acc = getAccount(username);
        return acc.getPassword();
    }

    public String getEmail(String username) throws AccountNotFoundException {
        LoginAccount acc = getAccount(username);
        return acc.getEmailAddress();
    }


    //Threshold Methods Below

    /** Set the BorrowThreshold of the given account to be the following.
     *
     * @param username The username of the account you'd like to modify
     * @param threshold What you want to set the threshold to
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setBorrowThreshold(String username, int threshold) throws NegativeThresholdException, WrongAccountTypeException,
            AccountNotFoundException{
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            ((UserAccount) a).setBorrowThreshold(threshold);
        }
        throw new WrongAccountTypeException();
    }

    /** Set the IncompleteThreshold of the given account to be the following.
     *
     * @param username The username of the account you'd like to modify
     * @param threshold What you want to set the threshold to
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setIncompleteThreshold(String username, int threshold) throws NegativeThresholdException, WrongAccountTypeException,
            AccountNotFoundException{
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            ((UserAccount) a).setIncompleteThreshold(threshold);
        }
        throw new WrongAccountTypeException();
    }

    /** Set the WeeklyThreshold of the given account to be the following.
     *
     * @param username The username of the account you'd like to modify
     * @param threshold What you want to set the threshold to
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setWeeklyThreshold(String username, int threshold) throws NegativeThresholdException, WrongAccountTypeException,
            AccountNotFoundException{
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            ((UserAccount) a).setWeeklyThreshold(threshold);
        }
        throw new WrongAccountTypeException();
    }

    /** Return the BorrowThreshold of the given account
     *
     * @param username The username of the account you're interested in
     * @return The Threshold associated with this account.
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public int getBorrowThreshold(String username) throws WrongAccountTypeException, AccountNotFoundException{
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            return ((UserAccount) a).getBorrowThreshold();
        }
        throw new WrongAccountTypeException();
    }

    /** Return the IncompleteThreshold of the given account
     *
     * @param username The username of the account you're interested in
     * @return The Threshold associated with this account.
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public int getIncompleteThreshold(String username) throws WrongAccountTypeException, AccountNotFoundException{
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            return ((UserAccount) a).getIncompleteThreshold();
        }
        throw new WrongAccountTypeException();
    }

    /** Return the WeeklyThreshold of the given account
     *
     * @param username The username of the account you're interested in
     * @return The Threshold associated with this account.
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public int getWeeklyThreshold(String username) throws WrongAccountTypeException, AccountNotFoundException{
        Account a = getAccount(username);
        if(a.getType().equals("USER")){
            return ((UserAccount) a).getWeeklyThreshold();
        }
        throw new WrongAccountTypeException();
    }

    /** Update the fact that a Status was added to a certain user
     *
     * @param status The status added to the user
     * @param user The user that had a status added
     */
    public void updateStatusAdded(String status, String user) {
        if (status.equals("GILDED")) {
            try {
                UserAccount userAccount = (UserAccount) getAccount(user);
                userAccount.setBorrowThreshold(userAccount.getBorrowThreshold() * 2);
                userAccount.setWeeklyThreshold(userAccount.getWeeklyThreshold() * 2);
                userAccount.setIncompleteThreshold(userAccount.getIncompleteThreshold() * 2);
            } catch (AccountNotFoundException ignored) {}
        }
    }


    /** Update the fact that a Status was removed from a certain user
     *
     * @param status The status removed from the user
     * @param user The user that had a status removed
     */
    public void updateStatusRemoved(String status, String user) {
        if (status.equals("GILDED")) {
            try {
                UserAccount userAccount = (UserAccount) getAccount(user);
                userAccount.setBorrowThreshold(userAccount.getBorrowThreshold()/2);
                userAccount.setWeeklyThreshold(userAccount.getWeeklyThreshold()/2);
                userAccount.setIncompleteThreshold(userAccount.getIncompleteThreshold()/2);
            } catch (AccountNotFoundException ignored) {}
        }
    }
}
