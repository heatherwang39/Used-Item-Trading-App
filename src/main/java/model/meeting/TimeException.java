package main.java.model.meeting;

/**
 * Exception to be thrown when attempting to confirm a meeting at an inappropriate time
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */

public class TimeException extends Exception {

    /**
     * Class Constructor
     */
    public TimeException() {
        super("Error: The meeting isn't suppose to have happened yet!");
    }

}