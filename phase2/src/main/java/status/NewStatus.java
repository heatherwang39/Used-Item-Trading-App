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
     * @param id       status id
     * @param username status' account's username
     */
    public NewStatus(int id, String username) {
        super(id, username);
        setType("NEW");
    }
}
