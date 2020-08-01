package main.java.status;

/**
 * NewStatus represents an Account that has yet to make a Trade.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class NewStatus extends Status {
    /**
     * Class constructor.
     *
     * @param username status' account's username
     */
    public NewStatus(String username) {
        super(username);
        setType("NEW");
    }
}
