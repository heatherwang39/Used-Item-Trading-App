package main.java;

import java.time.LocalDateTime;
import java.util.List;

public interface TwoMeetings {


    /** Return a list representation of the scheduled first meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the first meeting
     */
    List getFirstMeeting();

    boolean setFirstMeeting(String place, LocalDateTime time);

    boolean confirmFirstMeeting(UserAccount trader);

    boolean acceptFirstMeeting(UserAccount trader);


    /** Return a list representation of the scheduled second meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the second meeting
     */
    List getSecondMeeting();

    boolean setSecondMeeting(String place, LocalDateTime time);

    boolean confirmSecondMeeting(UserAccount trader);

    boolean acceptSecondMeeting(UserAccount trader);
}