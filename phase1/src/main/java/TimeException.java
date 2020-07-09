package main.java;

/**
 * Exception to be thrown when attempting to set/suggest/confirm a meeting at an inappropriate
 * time (i.e., suggesting a meeting to happen in the past, or confirming a meeting before it
 * has even happened)
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class TimeException extends Exception {

    /**
     * Class Constructor
     */
    public TimeException() {
        super();
    }

}