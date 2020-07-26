package main.java.Account;

/**
 * AdminAccount is a LoginAccount that can manage other accounts inventories and frozen status, verify items, and
 * perform other system level tasks.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
class AdminAccount extends LoginAccount {

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


    @Override
    String type() {
        return "admin";
    }
}