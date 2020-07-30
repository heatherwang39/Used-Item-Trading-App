package main.java.systems;

/**
 * An Interface for the actions of an account that is able to log in
 *
 * @author Fadi Hareth, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Loginable {

    /**
     * Checks if username and password are valid, existing alphanumeric + dash/underscore strings
     *
     * @param password the input password
     * @return true if sign in was successful, false if not
     */
    boolean signIn(String password);
}