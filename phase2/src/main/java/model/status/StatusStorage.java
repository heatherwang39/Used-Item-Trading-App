package main.java.model.status;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.util.*;

/**
 * StatusStorage is a use case class that manages Account statuses, creation and deletion.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StatusStorage implements Storage, TradeObserver {

    private final Map<String, List<Status>> statuses;
    private StatusFactory statusFactory;

    /**
     * Class constructor.
     *
     * @param statuses Map containing lists of Statuses referenced by usernames
     */
    public StatusStorage(Object statuses) {
        if (statuses == null) {
            this.statuses = new HashMap<>();
        } else {
            this.statuses = (Map<String, List<Status>>) statuses;
        }
        statusFactory = new StatusFactory();
    }

    /**
     * Get all active Account statuses under a username
     *
     * @param username Account username
     * @return Account statuses
     */
    private List<Status> getAccountStatuses(String username) {
        return (statuses.containsKey(username)) ? new ArrayList<>(statuses.get(username)) : new ArrayList<>();
    }

    /**
     * Maps a list of Statuses to a list of strings
     *
     * @param statuses Status list
     * @return string list
     */
    private List<String> getStatusStrings(List<Status> statuses){
        List<String> strings = new ArrayList<>();
        for (Status s: statuses) {
            strings.add(s.getType());
        }
        return strings;
    }

    /**
     * Get all active Account statuses as strings under a username
     *
     * @param username Account username
     * @return Account status strings
     */
    public List<String> getAccountStatusStrings(String username) {
        return getStatusStrings(getAccountStatuses(username));
    }

    /**
     * Get whether Account has a certain status
     *
     * @param username Account username
     * @param type Status type
     * @return boolean representing existence of status
     */
    public boolean containsStatus(String username, String type) {
        for (Status s: getAccountStatuses(username)) {
            if (s.getType().equals(type)) {
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
     */
    public List<String> getAccountsWithStatus(String type) {
        List<String> accountsWithStatus = new ArrayList<>();
        for (Map.Entry<String, List<Status>> entry : statuses.entrySet()) {
            if (containsStatus(entry.getKey(), type)) accountsWithStatus.add(entry.getKey());
        }
        return accountsWithStatus;
    }

    /**
     * Remove Status under Account username
     *
     * @param username Account username
     * @param type Status type
     * @throws StatusNotFoundException when no Status could be found
     */
    public void removeStatus(String username, String type) throws StatusNotFoundException {

        for (Status s: getAccountStatuses(username)) {
            if (s.getType().equals(type)) {
                List<Status> accountStatuses = statuses.get(username);
                accountStatuses.remove(s);
                if (accountStatuses.isEmpty()) {
                    statuses.remove(username);
                }
                return;
            }
        }
        throw new StatusNotFoundException();
    }

    /**
     * Create a Status under Account username
     *
     * @param username Account username
     * @param type Status type
     * @throws InvalidStatusTypeException when Status type is invalid
     */
    public void createStatus(String username, String type) throws InvalidStatusTypeException {
        Status s = statusFactory.getStatus(type);
        if (s == null) throw new InvalidStatusTypeException();
        if (statuses.containsKey(username)) {
            statuses.get(username).add(s);
        } else {
            statuses.put(username, new ArrayList<>(Collections.singletonList(s)));
        }
    }


    //Observer pattern
    /**
     * Remove the new status of user automatically when a trade is completed
     *
     * @param itemIDs ids of traded items
     * @param newOwner usernames of trades
     * @throws StatusNotFoundException when no Status can be found
     */
    @Override
    public void updateTradeComplete(List<Integer> itemIDs, List<String> newOwner) throws StatusNotFoundException {
        for(String username:newOwner){
            if(containsStatus(username,"NEW")){
                removeStatus(username,"NEW");
            }
        }
    }
}
