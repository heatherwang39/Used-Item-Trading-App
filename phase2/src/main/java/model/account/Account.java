package main.java.model.account;

import java.io.Serializable;

/**
 * Account is an abstract class representing a member with access to the system.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public abstract class Account implements Serializable {

    private final String username;
    private String type;

    /**
     * Class constructor
     *
     * @param username account username
     */
    public Account(String username){
        this.username = username;
        type = "ACCOUNT";
    }

    /**
     * Gets the account's username.
     *
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets the account type to determine available menu options.
     *
     * @return account type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the Account type.
     *
     * @param type Account type
     */
    protected void setType(String type) {
        this.type = type;
    }
}
