package main.java;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {

    /** Return the Meeting suggested with this object. If no meeting has been suggested,
     * a NoMeetingException will be thrown
     *
     * @return The Meeting associated with this object
     * @throws NoMeetingException No Meeting has been suggested
     */
    Meeting getMeeting() throws NoMeetingException;

    /** Suggest a Meeting for this object. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this object.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    boolean setMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;
}