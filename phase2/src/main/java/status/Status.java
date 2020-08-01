package main.java.status;

import java.io.Serializable;

/**
 * Status represents an account Status that can be added by an admin or automatically added. Statuses can provide
 * benefits to an Account, or it can block an Account from certain functionalities.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class Status implements Serializable {

    private final int id;
    private final String username;
    private String type;

    /**
     * Class constructor.
     *
     * @param id status id
     * @param username status' account's username
     */
    public Status(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Get status id.
     *
     * @return id
     */
    public int getId() { return id; }

    /**
     * Get status' account's username.
     *
     * @return username
     */
    public String getUsername() { return username; }

    /**
     * Get status' type.
     *
     * @return type
     */
    public String getType() { return type; }

    /**
     * Set status' type.
     *
     * @param type status type
     */
    public void setType(String type) { this.type = type; }
}
