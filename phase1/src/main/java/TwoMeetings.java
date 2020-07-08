package main.java;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface for transactions, events, etc. that involve two meetings
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public interface TwoMeetings {


    /** Return the place of the suggested first Meeting for this object. If no first Meeting has been suggested, return
     * null
     *
     * @return The place of the suggested first Meeting
     */
    String getFirstMeetingPlace();


    /** Return the time of the first suggested Meeting for this object. If no first Meeting has been suggested, return
     * null
     *
     * @return The time of the suggested first Meeting
     */
    LocalDateTime getFirstMeetingTime();


    /** Return True iff the first Meeting corresponding to the object has been accepted
     *
     * @return whether the first Meeting has been accepted.
     */
    boolean getFirstMeetingAccepted();


    /** Return True iff the first Meeting corresponding to the object has been confirmed
     *
     * @return whether the first Meeting has been confirmed.
     */
    boolean getFirstMeetingConfirmed();


    /** Set the first Meeting for this object to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean setFirstMeeting(String place, LocalDateTime time) throws TimeException;


    /** Suggest a first Meeting for this object to be at this place and this time. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean suggestFirstMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    /** Attempt to record the fact that acceptor has accepted the suggested first Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested first Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no first meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this first meeting
     */
    boolean acceptFirstMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    /** Attempt to record the fact that attendee has confirmed the suggested first Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no first meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this first meeting
     * @throws TimeException Thrown if the first Meeting was confirmed before it was supposed to take place
     */
    boolean confirmFirstMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;





    /** Return the place of the suggested second Meeting for this object. If no second Meeting has been suggested,
     * return null
     *
     * @return The place of the suggested second Meeting
     */
    String getSecondMeetingPlace();


    /** Return the second time of the suggested Meeting for this object. If no second Meeting has been suggested, return
     * null
     *
     * @return The time of the suggested second Meeting
     */
    LocalDateTime getSecondMeetingTime();


    /** Return True iff the second Meeting corresponding to the object has been accepted
     *
     * @return whether the second Meeting has been accepted.
     */
    boolean getSecondMeetingAccepted();


    /** Return True iff the second Meeting corresponding to the object has been confirmed
     *
     * @return whether the second Meeting has been confirmed.
     */
    boolean getSecondMeetingConfirmed();


    /** Set the first Meeting for this object to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean setSecondMeeting(String place, LocalDateTime time) throws TimeException;


    /** Suggest a second Meeting for this object to be at this place and this time. The person suggesting this
     * Meeting automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    boolean suggestSecondMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    /** Attempt to record the fact that acceptor has accepted the suggested second Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested second Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no second meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this second meeting
     */
    boolean acceptSecondMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    /** Attempt to record the fact that attendee has confirmed the suggested second Meeting. If this fact is
     * successfully recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no second Meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the second Meeting was confirmed before it was supposed to take place
     */
    boolean confirmSecondMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;
}