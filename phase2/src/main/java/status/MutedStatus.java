package main.java.status;

/**
 * MutedStatus represents an Account that has been disallowed from sending Messages
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class MutedStatus extends Status {
    /**
     * Class constructor.
     *
     * @param username status' account's username
     */
    public MutedStatus(String username) {
        super(username);
        setType("MUTED");
    }
}
