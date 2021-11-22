package main.java.model.message;
/**
 * Exception to be thrown when the content is empty.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public class EmptyContentException extends Exception{
    /**
     * Class Constructor
     */
    public EmptyContentException() {super("Cannot send message because of empty content."); }

}
