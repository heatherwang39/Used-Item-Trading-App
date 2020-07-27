package main.java.interfaces;

/**
 * An Interface for the actions of an account that is able to register
 *
 * @author Fadi Hareth
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
     */
    void register(String username, String password, String email);
}