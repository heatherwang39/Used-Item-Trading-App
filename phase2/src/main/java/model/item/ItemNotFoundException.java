package main.java.model.item;

/**
 * Exception to be thrown when an item cannot be found with the given itemId.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class ItemNotFoundException extends Exception{

    /**
     * Class Constructor
     */
    public ItemNotFoundException() { super("An item was not found with the input ID."); }

}
