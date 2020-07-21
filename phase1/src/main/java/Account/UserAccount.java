package main.java.Account;

/**
 * UserAccount is a LoginAccount that possesses an inventory and wishlist, and can trade with others and be frozen.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
class UserAccount extends LoginAccount {

    /**
     * Class constructor.
     *
     * @param username account's username
     * @param password account's password
     */
    public UserAccount(String username, String password) { super(username, password); }

    @Override
    String type() { return "user"; }
}