package main.java.model.item;

/**
 * ItemInTradeException is thrown when an item being unhidden is currently in an ongoing trade
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public class ItemInTradeException extends Exception {

    /**
     * Class Constructor
     */
    public ItemInTradeException() {
        super("The item you are trying to unhide is in an ongoing trade. You cannot unhide yet.");
    }

}
