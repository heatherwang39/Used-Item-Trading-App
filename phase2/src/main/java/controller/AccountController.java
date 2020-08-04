package main.java.controller;

/**
 * A Controller for Accounts
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface AccountController {

    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    String getAccountType();
}