package main.java.model.status;

/**
 * GildedStatus represents an Account that has confirmed 20 or more Trades.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class GildedStatus extends Status {
    /**
     * Class constructor.
     *
     * @param username status' account's username
     */
    public GildedStatus(String username) {
        super(username);
        setType("GILDED");
    }
}
