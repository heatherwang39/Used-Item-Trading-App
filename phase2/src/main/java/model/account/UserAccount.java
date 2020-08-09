package main.java.model.account;

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
    private List<StatusEnum> statuses = new ArrayList<>();

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

    public List<StatusEnum> getStatuses(){ return statuses;}

    public void addStatus(StatusEnum status){
        statuses.add(status);
    }

    public void removeStatus(StatusEnum status){
        statuses.remove(status);
    }

    //Threshold Methods Below

    /** Return the BorrowThreshold for this given Account
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
}