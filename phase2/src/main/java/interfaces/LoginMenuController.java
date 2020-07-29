package main.java.interfaces;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to log in
 *
 * @author Fadi Hareth, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface LoginMenuController {

    /**
     * Checks if username and password are valid, existing alphanumeric + dash/underscore strings
     *
     * @param username the input username
     * @param password the input password
     * @return The controller of the account that you've logged in to
     */
    AccountController signIn(String username, String password);


    /**
     * Registers a new user
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     */
    void register(String username, String password, String email);
}