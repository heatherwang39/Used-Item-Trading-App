package main.java.status;

/**
 * Status represents an account Status that can be added by an admin or automatically added. Statuses can provide
 * benefits to an Account, or it can block an Account from certain functionalities.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class Status {

    private final int id;
    private final String username;
    private boolean revokeRequested;

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
     * Get whether the revoke request has been sent.
     *
     * @return Boolean representing the condition
     */
    public boolean revokeRequested() {return revokeRequested; }

    /**
     * Set whether the revoke request has been sent.
     *
     * @param b boolean
     */
    public void setRevokeRequested(boolean b) {revokeRequested = b;}
}
