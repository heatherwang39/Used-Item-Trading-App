package main.java.status;

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
     * @param id       status id
     * @param username status' account's username
     */
    public GildedStatus(int id, String username) {
        super(id, username);
        setType("GILDED");
    }
}
