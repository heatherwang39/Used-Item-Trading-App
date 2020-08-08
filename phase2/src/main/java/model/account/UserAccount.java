package main.java.model.account;

/**
 * UserAccount is a LoginAccount that possesses an inventory and wishlist, and can trade with others and be frozen.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class UserAccount extends LoginAccount {
    private int borrowThreshold = 1;
    private int incompleteThreshold = 3;
    private int weeklyThreshold = 3;

    /**
     * Class constructor.
     *
     * @param username account's username
     * @param password account's password
     * @param emailAddress account email address
     */
    public UserAccount(String username, String password, String emailAddress) {
        super(username, password, emailAddress);
    }


    /**
     * Gets the account type to determine available menu options.
     *
     * @return account type
     */
    public String getType(){
        return "USER";
    }


    //Threshold Methods Below


    public int getBorrowThreshold(){
        return borrowThreshold;
    }

    public int getIncompleteThreshold(){
        return incompleteThreshold;
    }

    public int getWeeklyThreshold(){
        return weeklyThreshold;
    }

    public void setBorrowThreshold(int borrowThreshold){
        this.borrowThreshold = borrowThreshold;
    }

    public void setIncompleteThreshold(int incompleteThreshold){
        this.incompleteThreshold = incompleteThreshold;
    }

    /**
     *
     * @param weeklyThreshold
     */
    public void setWeeklyThreshold(int weeklyThreshold){
        this.weeklyThreshold = weeklyThreshold;
    }
}