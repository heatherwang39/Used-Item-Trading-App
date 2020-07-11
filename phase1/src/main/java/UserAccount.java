package main.java;

import java.util.HashMap;

/**
 * Represents an user account in the main.java.Trade system. Users have basic privileges, and can make offers so long as their
 * account isn't frozen.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class UserAccount extends Account {

    private boolean isFrozen;

    /**
     * Class constructor
     * @param username account username
     * @param password account password
     * @param email account email address
     * @param isFrozen is this account frozen?
     */
    public UserAccount(String username, String password, String email, boolean isFrozen){
        super(username, password, email);
        this.isFrozen = isFrozen;
    }

    /**
     * Set account status to frozen
     */
    public void freeze() { isFrozen = true; }

    /**
     * Set account status to not frozen
     */
    public void unfreeze() { isFrozen = false; }

    /**
     * Return false because users do not have admin privileges
     */
    public boolean isAdmin(){
        return false;
    }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public HashMap<String, String> getData(){return new HashMap<>();}

}
