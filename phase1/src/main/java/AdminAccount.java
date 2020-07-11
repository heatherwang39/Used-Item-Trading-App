package main.java;

import java.util.HashMap;

/**
 * Represents an admin account in the main.java.Trade system. Admins are users with additional privileges, meaning they can
 * manage other accounts' trading threshold, inventory, and frozen status.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
class AdminAccount extends Account {

    /**
     * Class constructor
     * @param username account username
     * @param password account password
     * @param email account email address
     */
    public AdminAccount(String username, String password, String email){
        super(username, password, email);
    }

    /**
     * Return true because admins have admin privileges.
     */
    public boolean isAdmin(){
        return true;
    }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public HashMap<String, String> getData(){return new HashMap<>();}

}