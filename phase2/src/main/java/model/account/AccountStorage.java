package main.java.model.account;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * AccountStorage is a use case class that manages logging, signing up, and many general Account related tasks.
 *
 * @author Robbert Liu, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class AccountStorage implements Storage, TradeObserver {

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

    public void setAllBorrowThresholds(int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException {
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        for(String user: accounts.keySet()){
            if (getType(user).equals("USER")){
                setBorrowThreshold(user, threshold);
            }
        }
    }

    public void setAllIncompleteThresholds(int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException {
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        for(String user: accounts.keySet()){
            if (getType(user).equals("USER")){
                setIncompleteThreshold(user, threshold);
            }
        }
    }

    public void setAllWeeklyThresholds(int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException {
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        for(String user: accounts.keySet()){
            if (getType(user).equals("USER")){
                setWeeklyThreshold(user, threshold);
            }
        }
    }


    /** Return all current thresholds of the given account
     *
     * @param username The username of the account you're interested in
     * @return A HashMap with key is the threshold's name and value is threshold's number
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public HashMap<String, Integer> getCurrentThresholds(String username) throws WrongAccountTypeException, AccountNotFoundException{
        Account account = getAccount(username);
        HashMap <String, Integer> thresholdData = new HashMap<>();
        if(account.getType().equals("USER")){
            int borrowThreshold = ((UserAccount)account).getBorrowThreshold();
            int incompleteThreshold = ((UserAccount)account).getIncompleteThreshold();
            int weeklyThreshold = ((UserAccount)account).getWeeklyThreshold();
            int gildedThreshold = ((UserAccount)account).getGildedThreshold();
            thresholdData.put("borrowThreshold",borrowThreshold);
            thresholdData.put("incompleteThreshold",incompleteThreshold);
            thresholdData.put("weeklyThreshold",weeklyThreshold);
            thresholdData.put("gildedThreshold",gildedThreshold);
            return thresholdData;
        }
        throw new WrongAccountTypeException();
    }


    /**
     * Get all active Account statuses as strings under a username
     *
     * @param username Account username
     * @return Account status strings
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public List<String> getAccountStatuses(String username) throws AccountNotFoundException, WrongAccountTypeException {
        Account user = getAccount(username);
        if(user.getType().equals("USER")){
            List<StatusEnum> statuses = ((UserAccount) user).getStatuses();
            List<String> statusesNames = new ArrayList<>();
            for(StatusEnum status:statuses){statusesNames.add(status.toString());}
            return statusesNames;
        }
        throw new WrongAccountTypeException();
    }

    /**
     * Get whether Account has a certain status
     *
     * @param username Account username
     * @param type Status type
     * @return boolean representing existence of status
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public boolean containsStatus(String username, String type) throws AccountNotFoundException, WrongAccountTypeException {
        List<String> statuses = getAccountStatuses(username);
        for (String status: statuses) {
            if (status.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all accounts with type status
     *
     * @param type Status type
     * @return list of usernames
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public List<String> getAccountsWithStatus(String type) throws AccountNotFoundException, WrongAccountTypeException {
        List<String> accountsWithStatus = new ArrayList<>();
        for(String username:accounts.keySet()){
            if(getType(username).equals("USER")){
                if(containsStatus(username,type)){
                    accountsWithStatus.add(username);
                }
            }
        }
        return accountsWithStatus;
    }

    /**
     * Remove Status under Account username
     * If it's to remove the gilded status, cut the threshold to half of the current value
     *
     * @param username Account username
     * @param type Status type
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws StatusNotFoundException when no Status with given type could be found
     */
    public void removeStatus(String username, String type) throws WrongAccountTypeException, AccountNotFoundException, StatusNotFoundException {
        if(containsStatus(username,type)) {
            UserAccount user = (UserAccount)getAccount(username);
            StatusEnum typeEnum = StatusEnum.valueOf(type.toUpperCase());
            user.removeStatus(typeEnum);
            if(type.toUpperCase().equals("GILDED")){
                user.setBorrowThreshold(user.getBorrowThreshold()/2);
                user.setWeeklyThreshold(user.getWeeklyThreshold()/2);
                user.setIncompleteThreshold(user.getIncompleteThreshold()/2);
            }
        } else {
            throw new StatusNotFoundException();
        }
    }

    /**
     * Add Status under Account username
     * If it's to add the gilded status, multiply the the current value of threshold by 2
     *
     * @param username Account username
     * @param type Status type
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void createStatus(String username, String type) throws AccountNotFoundException, WrongAccountTypeException {
        if(getType(username).equals("USER")){
            if(!containsStatus(username,type)){
                UserAccount user = (UserAccount)getAccount(username);
                StatusEnum typeEnum = StatusEnum.valueOf(type.toUpperCase());
                user.addStatus(typeEnum);
                if(type.toUpperCase().equals("GILDED")){
                    user.setBorrowThreshold(user.getBorrowThreshold() * 2);
                    user.setWeeklyThreshold(user.getWeeklyThreshold() * 2);
                    user.setIncompleteThreshold(user.getIncompleteThreshold() * 2);
                }
            }
        }

    }

    //automatically freeze account below
    /**
     * Checks if a user with given username should be frozen based on if they violate any of the trade thresholds
     * There are three thresholds that are checked:
     * (1) Borrow Threshold: if a user has borrowed more than they have lent out
     * (2) Incomplete Threshold: if a user has too many incomplete trades
     * (3) Weekly Threshold: if a user has traded too much in one week
     * If a user violates any of these thresholds, a string representing the violation is added into a list, and a list
     * of reasons is returned.
     * If the list is empty, the user should not be frozen.
     *
     * @param username username of the user
     * @return a list of reasons why that user should be frozen. Empty if there are no reasons
     */

    private List<String> checkUserShouldFreeze(String username) throws AccountNotFoundException {
        List<String> reasonsToFreeze = new ArrayList<>();
        UserAccount user = (UserAccount) getAccount(username);
        int borrowThreshold = user.getBorrowThreshold();
        int incompleteThreshold = user.getIncompleteThreshold();
        int weeklyThreshold = user.getWeeklyThreshold();
        int numberOfBorrowedItems = user.getNumberOfBorrowedItems();
        int numberOfIncompleteTrades= user.getNumberOfIncompleteTrades();
        int numberOfWeeklyTrades = user.getNumberOfWeeklyTrades();

        if (numberOfBorrowedItems > borrowThreshold) reasonsToFreeze.add("BORROW");
        if (numberOfIncompleteTrades > incompleteThreshold) reasonsToFreeze.add("INCOMPLETE");
        if (numberOfWeeklyTrades > weeklyThreshold) reasonsToFreeze.add("WEEKLY");
        return reasonsToFreeze;
    }


    /**
     * Checks which users in usernames should be frozen based on if they violate any of the trade thresholds
     * There are three thresholds that are checked:
     * (1) Borrow Threshold: if a user has borrowed more than they have lent out
     * (2) Incomplete Threshold: if a user has too many incomplete trades
     * (3) Weekly Threshold: if a user has traded too much in one week
     * If a user violates any of these thresholds, a string representing the violation is added into a list, and a list
     * of reasons is returned.
     * If the list is empty, the user should not be frozen.
     *
     * @param traders list of usernames of traders involved
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */


    private void freezeUsers(List<String> traders) throws AccountNotFoundException, WrongAccountTypeException {
        for(String username:traders){
            if(!containsStatus(username,"FROZEN")) {
                UserAccount user = (UserAccount) getAccount(username);
                List<String> userFreezeReasons = checkUserShouldFreeze(username);
                user.setFreezeReasons(userFreezeReasons);
                if (userFreezeReasons.size() > 1) {
                    user.addStatus(StatusEnum.FROZEN);
                }
            }
        }
    }

    public List<String> showFreezeReasons(String username) throws AccountNotFoundException {
        UserAccount user = (UserAccount) getAccount(username);
        return user.getFreezeReasons();
    }

    //automatically gild User below
    /** Set the WeeklyThreshold of the given account to be the following.
     *
     * @param username The username of the account you'd like to modify
     * @param threshold What you want to set the threshold to
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setGildedThreshold(String username, int threshold) throws NegativeThresholdException, WrongAccountTypeException,
            AccountNotFoundException{
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        Account user = getAccount(username);
        if(user.getType().equals("USER")){
            ((UserAccount) user).setGildedThreshold(threshold);
        }
        throw new WrongAccountTypeException();
    }

    /** Set the GildedThreshold for all users.
     *
     * @param threshold What you want to set the threshold to
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setAllGildedThresholds(int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException {
        if(threshold < 0){
            throw new NegativeThresholdException();
        }
        for(String user: accounts.keySet()){
            if (getType(user).equals("USER")){
                setGildedThreshold(user, threshold);
            }
        }
    }

    private void gildUsers(List<String> traders) throws AccountNotFoundException, WrongAccountTypeException {
        for(String username:traders) {
            if(!containsStatus(username,"GILDED")){
                UserAccount user = (UserAccount) getAccount(username);
                if(user.getNumberOfCompletedTrades()>user.getGildedThreshold()){
                    user.addStatus(StatusEnum.GILDED);
                }
            }
        }
    }




    /**
     * Record the fact that a Trade's status has changed
     *
     * @param exchangeData A HashMap representing the Exchange Data of the Trade
     * @param newStatus    The new Status of the Trade
     */
    @Override
    public void updateTradeChange(HashMap<String, HashMap<String, Integer>> exchangeData, int newStatus){
        try {
            List<String> traders = (List<String>) exchangeData.keySet();
            if (newStatus == 3) {
                for (String username : traders) {
                    UserAccount user = (UserAccount) getAccount(username);
                    int numberOfCompletedTrades = user.getNumberOfCompletedTrades() + 1;
                    user.setNumberOfCompletedTrades(numberOfCompletedTrades);
                    updateNumberOfWeeklyTrades(user);
                    int numberOfWeeklyTrades = user.getNumberOfWeeklyTrades() + 1;
                    user.setNumberOfWeeklyTrades(numberOfWeeklyTrades);
                    int numberOfIncompleteTrades = user.getNumberOfIncompleteTrades() - 1;
                    user.setNumberOfIncompleteTrades(numberOfIncompleteTrades);
                    if (containsStatus(username, "NEW")) {
                        user.removeStatus(StatusEnum.NEW);
                    }
                }
                updateNumberOfBorrowedItems(exchangeData);
                gildUsers(traders);
                freezeUsers(traders);
            }

            if (newStatus == 0) {
                for (String username : traders) {
                    UserAccount user = (UserAccount) getAccount(username);
                    int numberOfIncompleteTrades = user.getNumberOfIncompleteTrades() + 1;
                    user.setNumberOfIncompleteTrades(numberOfIncompleteTrades);
                }
            }

            if (newStatus == -1) {
                for (String username : traders) {
                    UserAccount user = (UserAccount) getAccount(username);
                    int numberOfIncompleteTrades = user.getNumberOfIncompleteTrades() - 1;
                    user.setNumberOfIncompleteTrades(numberOfIncompleteTrades);
                }
            }
        }
        catch(AccountNotFoundException | WrongAccountTypeException ignored){}
    }

    private void updateNumberOfBorrowedItems(HashMap<String, HashMap<String, Integer>> exchangeData) throws AccountNotFoundException {
        for(String username : exchangeData.keySet()) {
            Integer itemSent = exchangeData.get(username).get("SENT");
            Integer itemReceived = exchangeData.get(username).get("RECEIVED");
            UserAccount user = (UserAccount) getAccount(username);
            if(itemSent!=null){
                int numberOfBorrowedItems = user.getNumberOfBorrowedItems()-1;
                user.setNumberOfBorrowedItems(numberOfBorrowedItems);
            }
            if(itemReceived!=null){
                int numberOfBorrowedItems = user.getNumberOfBorrowedItems()+1;
                user.setNumberOfBorrowedItems(numberOfBorrowedItems);
            }
        }
    }

    private void updateNumberOfWeeklyTrades(UserAccount user){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastCheckPoint = user.getWeeklyTimer();
        if((lastCheckPoint == null ) || (currentTime.isAfter(lastCheckPoint.plusDays(7)))){
            user.setNumberOfWeeklyTrades(0);
            user.setWeeklyTimer(currentTime);
        }
    }
}
