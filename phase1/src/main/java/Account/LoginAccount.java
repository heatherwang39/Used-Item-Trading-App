package main.java.Account;

/**
 * LoginAccount is an abstract class representing a unique member of the system who can log in and access the system.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
abstract class LoginAccount extends Account {

    private final String password;

    /**
     * Class constructor.
     *
     * @param username account username
     * @param password account password
     */
    public LoginAccount(String username, String password) {
        super(username);
        this.password = password;
    }

    /**
     * Verifies the password attempt.
     *
     * @param password input password
     * @return boolean representing if input password is correct
     */
    public boolean isPassword(String password){
        return password.equals(this.password);
    }

}
