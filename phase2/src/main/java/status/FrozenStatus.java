package main.java.status;

/**
 * FrozenStatus represents an Account that has been blocked from borrowing too many items without lending one, or by an
 * Admin's discretion
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class FrozenStatus extends Status {
    /**
     * Class constructor.
     *
     * @param id       status id
     * @param username status' account's username
     */
    public FrozenStatus(int id, String username) {
        super(id, username);
        setType("FROZEN");
    }
}
