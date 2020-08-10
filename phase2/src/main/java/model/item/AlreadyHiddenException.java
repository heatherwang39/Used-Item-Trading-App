package main.java.model.item;

/**
 * AlreadyHiddenException is thrown when an item being hidden is already hidden
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AlreadyHiddenException extends Exception {

    /**
     * Class Constructor
     */
    public AlreadyHiddenException() {
        super("The item you are trying to hide is already hidden.");
    }

}
