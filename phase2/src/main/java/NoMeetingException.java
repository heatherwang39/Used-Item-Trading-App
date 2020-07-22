package main.java;

/**
 * Exception to be thrown when attempting to accept a meeting when a meeting place
 * or a time haven't even be suggested yet. Also thrown when trying to confirm a meeting when
 * a meeting place/time haven't been accepted yet.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class NoMeetingException extends Exception {

    /**
     * Class Constructor
     */
    public NoMeetingException() {
        super();
    }

}