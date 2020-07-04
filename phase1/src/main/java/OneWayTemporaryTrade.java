package main.java;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings, Serializable {
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;


    /** Initializes an instance of OneWayTemporaryTrade based on the given parameters
     *
     * @param sender The trader (their username) that sent the item
     * @param receiver The trader (their username) that received the item
     * @param item The item (its ID) that was traded from the sender to the receiver
     */
    public OneWayTemporaryTrade(String sender, String receiver, int item){
        super(sender, receiver, item);
        firstMeeting = new TwoPersonMeeting(sender, receiver);
        secondMeeting = new TwoPersonMeeting(sender, receiver);
    }


    /** Return the place of the suggested first Meeting for this Trade. If no first Meeting has been suggested, return
     * null
     *
     * @return The place of the suggested first Meeting
     */
    public String getFirstMeetingPlace(){
        return firstMeeting.getPlace();
    }


    /** Return the place of the suggested second Meeting for this Trade. If no second Meeting has been suggested,
     * return null
     *
     * @return The place of the suggested second Meeting
     */
    public String getSecondMeetingPlace(){
        return secondMeeting.getPlace();
    }


    /** Return the time of the first suggested Meeting for this Trade. If no first Meeting has been suggested, return
     * null
     *
     * @return The time of the suggested first Meeting
     */
    public LocalDateTime getFirstMeetingTime(){
        return firstMeeting.getTime();
    }


    /** Return the second time of the suggested Meeting for this Trade. If no second Meeting has been suggested, return
     * null
     *
     * @return The time of the suggested second Meeting
     */
    public LocalDateTime getSecondMeetingTime(){
        return secondMeeting.getTime();
    }


    /** Set the first Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean setFirstMeeting(String place, LocalDateTime time) throws TimeException{
        boolean value;
        try{ value = firstMeeting.setPlaceTime(place, time);}
        catch(TradeCancelledException e){
            setStatus(-1);
            return false;
        }
        return value;
    }


    /** Set the first Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean setSecondMeeting(String place, LocalDateTime time) throws TimeException{
        boolean value;
        try{ value = secondMeeting.setPlaceTime(place, time);}
        catch(TradeCancelledException e){
            secondMeeting.resetWarnings();
            return false;
        }
        return value;
    }


    /** Suggest a first Meeting for this Trade to be at this place and this time. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean suggestFirstMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {
        boolean value;
        try{value = firstMeeting.suggestPlaceTime(place, time, suggester);}
        catch(TradeCancelledException e){
            setStatus(-1);
            return false;
        }
        return value;
    }


    /** Suggest a second Meeting for this Trade to be at this place and this time. The person suggesting this
     * Meeting automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean suggestSecondMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {

        boolean value;
        try{value = secondMeeting.suggestPlaceTime(place, time, suggester);}
        catch(TradeCancelledException e){
            resetWarnings();
            return false;
        }
        return value;
    }


    /** Attempt to record the fact that acceptor has accepted the suggested first Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested first Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no first meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this first meeting
     */
    public boolean acceptFirstMeeting(String acceptor) throws NoMeetingException, WrongAccountException {
        return firstMeeting.acceptMeeting(acceptor);
    }


    /** Attempt to record the fact that acceptor has accepted the suggested second Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested second Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no second meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this second meeting
     */
    public boolean acceptSecondMeeting(String acceptor) throws NoMeetingException, WrongAccountException {
        return secondMeeting.acceptMeeting(acceptor);
    }


    /** Attempt to record the fact that attendee has confirmed the suggested first Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no first meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this first meeting
     * @throws TimeException Thrown if the first Meeting was confirmed before it was supposed to take place
     */
    public boolean confirmFirstMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException{
        return firstMeeting.confirmMeeting(attendee);
    }


    /** Attempt to record the fact that attendee has confirmed the suggested second Meeting. If this fact is
     * successfully recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no second Meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the second Meeting was confirmed before it was supposed to take place
     */
    public boolean confirmSecondMeeting(String attendee) throws NoMeetingException, WrongAccountException,
            TimeException{
        boolean value;
        value = secondMeeting.confirmMeeting(attendee);
        if(secondMeeting.getConfirmed()){
            setStatus(2);
        }
        return value;
    }

    /**Reset the number of warnings (i.e., the number of times a meeting has been
     * suggested without confirming) back to 0
     *
     */
    public void resetWarnings(){
        firstMeeting.resetWarnings();
        secondMeeting.resetWarnings();
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return false;
    }


    /** Return True iff the first Meeting corresponding to the Trade has been accepted
     *
     * @return whether the first Meeting has been accepted.
     */
    public boolean getFirstMeetingAccepted(){
        return firstMeeting.getAccepted();
    }


    /** Return True iff the second Meeting corresponding to the Trade has been accepted
     *
     * @return whether the second Meeting has been accepted.
     */
    public boolean getSecondMeetingAccepted(){
        return secondMeeting.getAccepted();
    }


    /** Return True iff the first Meeting corresponding to the Trade has been confirmed
     *
     * @return whether the first Meeting has been confirmed.
     */
    public boolean getFirstMeetingConfirmed(){
        return firstMeeting.getConfirmed();
    }


    /** Return True iff the second Meeting for this Trade has been confirmed
     *
     * @return whether the second Meeting has been confirmed.
     */
    public boolean getSecondMeetingConfirmed(){
        return secondMeeting.getConfirmed();
    }
}
