package main.java.systems;

import java.util.List;

/**
 * An Interface for the actions of an admin account
 *
 * @author Fadi Hareth, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface AdminInterface {

    /**
     * Registers a new Administrative Account
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @param email the email of the new account
     */
    String registerAdmin(String username, String password, String email);
}