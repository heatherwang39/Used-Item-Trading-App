package main.java.system;

import java.util.List;

/**
 * An interface for the actions of an account
 *
 * @author Fadi Hareth, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface AccountInterface {

    /**
     * Allows the account to log out and view the listings as a guest user
     */
    void logout();

    /**
     * Returns information about the account, including username and email
     * @return list of information about account
     */
    List<String> viewAccountInformation();
}
