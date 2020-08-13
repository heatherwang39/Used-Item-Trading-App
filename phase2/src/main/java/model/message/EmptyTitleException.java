package main.java.model.message;

/**
 * Exception to be thrown when the title is empty.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public class EmptyTitleException extends Exception{
    /**
     * Class Constructor
     */
    public EmptyTitleException() {super("Cannot send message because of empty title."); }

}