package main.java.model.message;
/**
 * Exception to be thrown when the recipient list is empty.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public class EmptyRecipientListException extends Exception{
    /**
     * Class Constructor
     */
    public EmptyRecipientListException() {super("Cannot send message because of empty recipient list."); }

}
