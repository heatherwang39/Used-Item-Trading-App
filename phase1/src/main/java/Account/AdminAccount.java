package main.java.account;

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
     */
    public AdminAccount(String username, String password){
        super(username, password);
    }


    @Override
    String type() {
        return "admin";
    }
}