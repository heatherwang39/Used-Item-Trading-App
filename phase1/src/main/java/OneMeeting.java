package main.java;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {


    /** Return the place of the suggested Meeting for this object. If no Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The place of the suggested Meeting
     * @throws NoMeetingException (Thrown if no meeting has been suggested)
     */
    String getMeetingPlace() throws NoMeetingException;


    /** Return the time of the suggested Meeting for this object. If no Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The time of the suggested Meeting
     * @throws NoMeetingException (Thrown if no meeting has been suggested)
     */
    LocalDateTime getMeetingTime() throws NoMeetingException;


    /** Return True iff the Meeting corresponding to the object has been accepted
     *
     * @return whether the Meeting has been accepted.
     */
    boolean getMeetingAccepted();


    /** Return True iff the Meeting corresponding to the object has been confirmed
     *
     * @return whether the Meeting has been confirmed.
     */
    boolean getMeetingConfirmed();


    /** Set the Meeting for this object to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean setMeeting(String place, LocalDateTime time) throws TimeException;


    /** Suggest a Meeting for this object to be at this place and this time. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean suggestMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    /** Attempt to record the fact that acceptor has accepted the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this meeting
     */
    boolean acceptMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    /** Attempt to record the fact that attendee has confirmed the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param attendee
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the Meeting was confirmed before it was supposed to take place
     */
    boolean confirmMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;
}