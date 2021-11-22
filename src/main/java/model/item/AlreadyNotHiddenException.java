package main.java.model.item;

/**
 * AlreadyNotHiddenException is thrown when an item being unhidden is already not hidden
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AlreadyNotHiddenException extends Exception {

    /**
     * Class Constructor
     */
    public AlreadyNotHiddenException() {
        super("The item you are trying to unhide is already not hidden.");
    }

}
