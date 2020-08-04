package main.java.model.status;

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
     */
    public MutedStatus() {
        setType("MUTED");
    }
}
