package main.java.model.status;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.util.*;

/**
 * StatusStorage is a use case class that manages Account statuses, creation and deletion.
 *
 * @author Robbert Liu, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class StatusStorage implements Storage {

    private Map<String, List<Status>> statuses;
    private final StatusFactory statusFactory;

    private final List<StatusObserver> observers = new ArrayList<>();

    /**
     * Class constructor.
     */
    public StatusStorage() {
        statusFactory = new StatusFactory();
    }

    /**
     * Generates a new structure matching the Storage's internal data
     * @return Empty storage data
     */
    @Override
    public Object getNewStorageData() {
        return new HashMap<String, Status>();
    }

    /**
     * This sets the Storage's data
     * @param statuses statuses data
     */
    @Override
    public void setStorageData(Object statuses) {
        this.statuses = (Map<String, List<Status>>) statuses;
    }


    private List<Status> getAccountStatuses(String username) {
        return (statuses.containsKey(username)) ? new ArrayList<>(statuses.get(username)) : new ArrayList<>();
    }


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
*/
    public void removeStatus(String username, String type) {
        for (Status s: getAccountStatuses(username)) {
            if (s.getType().equals(type)) {
                List<Status> accountStatuses = statuses.get(username);
                accountStatuses.remove(s);
                if (accountStatuses.isEmpty()) {
                    statuses.remove(username);
                }
                notifyStatusRemoved(type, username);
                return;
            }
        }
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
            if(!containsStatus(username,type))
                {statuses.get(username).add(s);}
        } else {
            statuses.put(username, new ArrayList<>(Collections.singletonList(s)));
        }
        notifyStatusAdded(type, username);
    }

    /**
     * Remove the new status of user automatically when a trade is completed
     *
     * @param itemIDs ids of traded items
     * @param newOwner usernames of trades

    public void updateTradeComplete(List<Integer> itemIDs, List<String> newOwner){
        try{
            for(String username:newOwner){
                if(containsStatus(username,"NEW")){
                    removeStatus(username,"NEW");}
            }
        }catch(StatusNotFoundException ignored){}
    }
     */

    /** Add an observer to this subject/observed object
     *
     * @param statusObserver The newly-added observer for this object
     */
    public void attachStatusObserver(StatusObserver statusObserver){
        observers.add(statusObserver);
    }


    /** Remove an observer from this subject/observed object
     *
     * @param statusObserver The recently-removed observer from this object
     */
    public void detachTradeObserver(StatusObserver statusObserver){
        observers.remove(statusObserver);
    }


    /** Notify the observers that a status was added to a given user
     *
     * @param status The status added to the user
     * @param user The user that had a status added
     */
    public void notifyStatusAdded(String status, String user){
        for(StatusObserver x : observers){
            x.updateStatusAdded(status, user);
        }
    }


    /** Notify the observers that a status was removed from a given user
     *
     * @param status The status removed from the user
     * @param user The user that had a status removed
     */
    public void notifyStatusRemoved(String status, String user){
        for(StatusObserver x : observers){
            x.updateStatusRemoved(status, user);
        }
    }
}
