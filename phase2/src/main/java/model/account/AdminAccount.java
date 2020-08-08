package main.java.model.account;

/**
 * AdminAccount is a LoginAccount that can manage other accounts inventories and frozen status, verify items, and
 * perform other system level tasks.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class AdminAccount extends LoginAccount {

    /**
     * Class constructor.
     *
     * @param username account username
     * @param password account password
     * @param emailAddress account email address
     */
    public AdminAccount(String username, String password, String emailAddress){
        super(username, password, emailAddress);
    }


    /**
     * Gets the account type to determine available menu options.
     *
     * @return account type
     */
    public String getType(){
        return "ADMIN";
    }
}