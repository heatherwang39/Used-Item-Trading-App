package main.java.controller;

/**
 * Exception to be thrown when the user's status is muted and cannot send messages.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class MutedAccountException extends Exception{
    /**
     * Class Constructor
     */
    public MutedAccountException() {super("Cannot send message because of muted account status."); }
}
