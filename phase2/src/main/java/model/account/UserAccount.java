package main.java.model.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAccount is a LoginAccount that possesses an inventory and wishlist, and can trade with others and be frozen.
 *
 * @author Robbert Liu, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class UserAccount extends LoginAccount {
    private int borrowThreshold = 1;
    private int incompleteThreshold = 3;
    private int weeklyThreshold = 3;
    private int gildedThreshold = 10;
    private int numberOfBorrowedItems;
    private int numberOfCompletedTrades;
    private int numberOfWeeklyTrades;
    private int numberOfIncompleteTrades;
    private List<String> freezeReasons = new ArrayList<>();
    private final List<StatusEnum> statuses = new ArrayList<>();
    private LocalDateTime weeklyTimer;


    /**
     * Class constructor.
     *
     * @param username account's username
     * @param password account's password
     * @param emailAddress account email address
     */
    public UserAccount(String username, String password, String emailAddress) {
        super(username, password, emailAddress);
        statuses.add(StatusEnum.NEW);
    }


    /**
     * Gets the account type to determine available menu options.
     *
     * @return account type
     */
    public String getType(){
        return "USER";
    }

    /**
     * Get the account statuses.
     *
     * @return list of all statuses
     */
    public List<StatusEnum> getStatuses(){ return statuses;}

    /**
     * Add a new status to the account.
     *
     * @param status the status need to be added
     */
    public void addStatus(StatusEnum status){
        statuses.add(status);
    }

    /**
     * Remove a status from the account.
     *
     * @param status the status need to be added
     */
    public void removeStatus(StatusEnum status){
        statuses.remove(status);
    }

    /**
     * Get the number of completed trades of the account.
     *
     * @return the number of completed trades of the account
     */
    public int getNumberOfCompletedTrades(){return numberOfCompletedTrades;}

    /**
     * Set the number of completed trades of the account.
     *
     * @param numberOfCompletedTrades  the number of completed trades of the account
     */
    public void setNumberOfCompletedTrades(int numberOfCompletedTrades){
        this.numberOfCompletedTrades = numberOfCompletedTrades;
    }

    /**
     * Get all freeze reasons of the account.
     * If the account is not frozen, return null.
     *
     * @return all freeze reasons of the account.
     */
    public List<String> getFreezeReasons() {
        return freezeReasons;
    }

    /**
     * Set freeze reasons to the account.
     *
     * @param freezeReasons the freeze reasons of the account.
     */
    public void setFreezeReasons(List<String> freezeReasons) {
        this.freezeReasons = freezeReasons;
    }

    /**
     * Get the number of completed trades in a week of the account.
     *
     * @return the number of completed trades in a week of the account
     */
    public int getNumberOfWeeklyTrades() {
        return numberOfWeeklyTrades;
    }

    /**
     * Set the number of completed trades in a week of the account.
     *
     * @param numberOfWeeklyTrades  the number of completed trades in a week of the account
     */
    public void setNumberOfWeeklyTrades(int numberOfWeeklyTrades) {
        this.numberOfWeeklyTrades = numberOfWeeklyTrades;
    }

    /**
     * Get the number of incomplete trades of the account.
     *
     * @return the number of incomplete trades of the account
     */
    public int getNumberOfIncompleteTrades() {
        return numberOfIncompleteTrades;
    }

    /**
     * Set the number of incomplete trades of the account.
     *
     * @param numberOfIncompleteTrades  the number of incomplete trades of the account
     */
    public void setNumberOfIncompleteTrades(int numberOfIncompleteTrades) {
        this.numberOfIncompleteTrades = numberOfIncompleteTrades;
    }

    /**
     * Get the number of borrowed items of the account.
     *
     * @return the number of borrowed items of the account
     */
    public int getNumberOfBorrowedItems() {
        return numberOfBorrowedItems;
    }

    /**
     * Set the number of borrowed items of the account.
     *
     * @param numberOfBorrowedItems  the number of borrowed items of the account
     */
    public void setNumberOfBorrowedItems(int numberOfBorrowedItems) {
        this.numberOfBorrowedItems = numberOfBorrowedItems;
    }

    /**
     * Get the gilded threshold of the account.
     *
     * @return the gilded threshold of the account
     */
    public int getGildedThreshold() {
        return gildedThreshold;
    }

    /**
     * Set the gilded threshold of the account.
     *
     * @param gildedThreshold  the gilded threshold of the account
     */
    public void setGildedThreshold(int gildedThreshold) {
        this.gildedThreshold = gildedThreshold;
    }

    /** Return the BorrowThreshold of the Account
     *
     * @return the BorrowThreshold
     */
    public int getBorrowThreshold(){
        return borrowThreshold;
    }

    /** Return the IncompleteThreshold for this given Account
     *
     * @return the IncompleteThreshold
     */
    public int getIncompleteThreshold(){
        return incompleteThreshold;
    }

    /** Return the WeeklyThreshold for this given Account
     *
     * @return the WeeklyThreshold
     */
    public int getWeeklyThreshold(){
        return weeklyThreshold;
    }

    /** Set the BorrowThreshold to be the given threshold.
     *
     * Precondition: The threshold is a non-negative integer.
     *
     * @param borrowThreshold What you want to change the threshold to.
     */
    public void setBorrowThreshold(int borrowThreshold){
        this.borrowThreshold = borrowThreshold;
    }

    /** Set the IncompleteThreshold to be the given threshold.
     *
     * Precondition: The threshold is a non-negative integer.
     *
     * @param incompleteThreshold What you want to change the threshold to.
     */
    public void setIncompleteThreshold(int incompleteThreshold){
        this.incompleteThreshold = incompleteThreshold;
    }

    /** Set the WeeklyThreshold to be the given threshold.
     *
     * Precondition: The threshold is a non-negative integer.
     *
     * @param weeklyThreshold What you want to change the threshold to.
     */
    public void setWeeklyThreshold(int weeklyThreshold){
        this.weeklyThreshold = weeklyThreshold;
    }

    /**
     * Get the weekly timer of the account.
     * It's the time of the earliest completed trades that happened within a week.
     * If the new completed trades happens more than one week after the weekly timer, the weekly timer need to be reset
     *
     * @return the weekly timer of the account
     */
    public LocalDateTime getWeeklyTimer() {
        return weeklyTimer;
    }

    /**
     * Set the weekly timer of the account.
     * It's the time of the earliest completed trades that happened within a week.
     * If the new completed trades happens more than one week after the weekly timer, the weekly timer need to be reset
     *
     * @param  weeklyTimer the weekly timer of the account
     */
    public void setWeeklyTimer(LocalDateTime weeklyTimer) {
        this.weeklyTimer = weeklyTimer;
    }

}