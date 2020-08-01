package main.java.status;

/**
 * AwayStatus represents an Account that has requested to lock their ability to trade, to receive trades, and their
 * ability to add items.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class AwayStatus extends Status {
    /**
     * Class constructor.
     *
     * @param username status' account's username
     */
    public AwayStatus(String username) {
        super(username);
        setType("AWAY");
    }
}
