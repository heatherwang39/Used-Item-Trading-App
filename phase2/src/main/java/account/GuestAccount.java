package main.java.account;

/**
 * GuestAccount is an Account representing a non-unique member of the system who has limited actions and cannot save any
 * data.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
class GuestAccount extends Account {

    /**
     * Class constructor.
     */
    public GuestAccount() {
        super("guest account");
    }

    @Override
    String type() {
        return "guest";
    }
}
