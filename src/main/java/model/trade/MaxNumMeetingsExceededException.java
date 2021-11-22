package main.java.model.trade;

/**
 * Exception to be thrown when trying to set a meeting for a trade when the maximum number of meetings
 * for said trade has already been reached
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class MaxNumMeetingsExceededException extends Exception{


    /**
     * Class Constructor
     */
    public MaxNumMeetingsExceededException() {
        super("Error: If this change is successfully made, the maximum number of meetings will be exceeded");
    }
}
