package main.java.Account;

import java.util.concurrent.Semaphore;

/**
 * LoginAccount is an abstract class representing a unique member of the system who can log in and access the system.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
abstract class LoginAccount extends Account {

    private final String password;
    private final String emailAddress;

    /**
     * Class constructor.
     *
     * @param username account username
     * @param password account password
     * @param emailAddress account email address
     */
    public LoginAccount(String username, String password, String emailAddress) {
        super(username);
        this.password = password;
        this.emailAddress = emailAddress;
    }

    /**
     * Returns email address.
     *
     * @return email address
     */
    public String getEmailAddress() { return emailAddress; }

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
