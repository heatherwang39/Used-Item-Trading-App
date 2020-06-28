package main.java;

import java.time.LocalDateTime;
import java.util.List;

public interface TwoMeetings {


    /** Return the suggested first Meeting with this object. If the first meeting has
     * not yet been suggested, a NoMeetingException will be thrown
     *
     * @return The first Meeting associated with this object
     * @throws NoMeetingException The first meeting has not yet been suggested
     */
    Meeting getFirstMeeting() throws NoMeetingException;


    /** Suggest a first Meeting for this object. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this object.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    boolean setFirstMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;


    /** Return the suggested second Meeting with this object. If no meeting has been suggested,
     * a NoMeetingException will be thrown
     *
     * @return The second Meeting associated with this object
     * @throws NoMeetingException The second meeting has not yet been suggested
     */
    Meeting getSecondMeeting() throws NoMeetingException;


    /** Suggest a second Meeting for this object. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this object.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    boolean setSecondMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;
}