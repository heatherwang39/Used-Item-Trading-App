package main.java.presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for formatting Account freeze data into an easy to output GUI format
 * Useful for Freeze tabs
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class FreezePresenter {

    /**
     * Formats a list of lists representing data for why a user should be frozen for GUI Lists to display
     * Format of freeze data: "Username, Reasons: BORROW, INCOMPLETE, WEEKLY"
     * Example: "Fadi, Reasons: INCOMPLETE"
     *
     * @param usersToFreeze List of lists which each contain the username of user and reasons to freeze them
     * @return List of formatted Strings of each freeze data
     */
    public List<String> formatUserFreeze(List<List<String>> usersToFreeze) {
        List<String> formatFreeze = new ArrayList<>();
        for (List<String> freezeData : usersToFreeze) {
            StringBuilder freezeInfo = new StringBuilder(freezeData.get(0));
            freezeInfo.append(", Reasons: ");
            for (String reason: freezeData.subList(1, freezeData.size())) {
                freezeInfo.append(reason);
                freezeInfo.append(", ");
            }
            freezeInfo.deleteCharAt(freezeInfo.length() - 1);
            formatFreeze.add(freezeInfo.toString());
        }
        return formatFreeze;
    }
}
