import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {


    /** Return a list representation of the scheduled meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the meeting
     */
    List getMeeting();


    boolean setMeeting(String place, LocalDateTime time);

    boolean confirmMeeting(UserAccount trader);

    /** Return True iff the meeting is confirmed
     *
     * @return Whether or not the meeting has been confirmed
     */
    boolean getConfirmed();


    boolean acceptMeeting(UserAccount trader);

    /** Return True iff the meeting has been accepted
     *
     * @return Whether or not the meeting has been accepted
     */
    boolean getAccepted();
}