package main.java.systems;

import java.util.List;
import java.util.Map;

/**
 * An Interface for the actions of an account that has access to moderator privileges
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface ModeratorInterface {

    /**
     * Shows which users have violated a trade threshold, allowing the user with moderator privileges to decide
     * whether to freeze their account or not
     */
    List<List<String>> showFreezeUsers(int borrowThreshold, int incompleteThreshold, int weeklyThreshold);

    /**
     * Shows items awaiting approval, allowing the user with moderator privileges to decide whether to approve the
     * items or not
     */
    Map<Integer, String> showItemRequests();
}