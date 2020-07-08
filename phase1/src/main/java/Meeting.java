package main.java;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface representing a meeting between multiple attendees
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public interface Meeting {

    /** Return the String representation of the location of this Meeting (i.e., Address)
     *
     * @return The String representation of the location of this Meeting
     */
    String getPlace();


    /** Return the time at which this Meeting will take place
     *
     * @return The time at which this Meeting will take place
     */
    LocalDateTime getTime();


    /** Return a List of Attendees for this Meeting
     *
     * @return A list of Attendees for this Meeting
     */
    List<String> getAttendees();
}
