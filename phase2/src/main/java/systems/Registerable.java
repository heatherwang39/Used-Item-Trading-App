package main.java.systems;

/**
 * An Interface for the actions of an account that is able to register
 *
 * @author Fadi Hareth, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Registerable {

    /**
     * Registers a new user
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     * @return String informing whether the register was a success or if there was a problem
     */
    String register(String username, String password, String email);
}