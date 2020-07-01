package main.java;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting, Serializable {
    private TwoPersonMeeting meeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of main.java.OneWayPermanentTrade based on the given parameters
     *
     * @param sender The trader (their username) that sent the item
     * @param receiver The trader (their username) that received the item
     * @param item The item (its ID) that was traded from the sender to the receiver
     */
    public OneWayPermanentTrade(String sender, String receiver, int item){
        super(sender, receiver, item);
        warnings = 0;
    }


    /** Return the place of the suggested Meeting for this Trade. If no Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The place of the suggested Meeting
     * @throws NoMeetingException (Thrown if no meeting has been suggested)
     */
    public String getMeetingPlace() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getPlace();
    }


    /** Return the time of the suggested Meeting for this Trade. If no Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The time of the suggested Meeting
     * @throws NoMeetingException (Thrown if no meeting has been suggested)
     */
    public LocalDateTime getMeetingTime() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getTime();
    }


    /** Set the Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean setMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getSender(), getReceiver());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        this.meeting = meeting;
        return true;
    }


    /** Suggest a Meeting for this Trade to be at this place and this time. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean suggestMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getSender()) || suggester.equals(getReceiver())){
            if(!setMeeting(place, time)){
                return false;
            }
            acceptMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    /** Attempt to record the fact that acceptor has accepted the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this meeting
     */
    public boolean acceptMeeting(String acceptor) throws WrongAccountException {
        boolean value;
        if(acceptor.equals(getSender()) || acceptor.equals(getReceiver())){
            value = meeting.acceptMeeting(acceptor);
            if(getMeetingAccepted()){
                resetWarnings();
            }
            return value;
        }
        throw new WrongAccountException();
    }


    /** Attempt to record the fact that attendee has confirmed the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the Meeting was confirmed before it was supposed to take place
     */
    public boolean confirmMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getSender()) || attendee.equals(getReceiver())){
            return meeting.confirmMeeting(attendee);
        }
        throw new WrongAccountException();
    }


    /**Reset the number of warnings (i.e., the number of times a meeting has been
     * suggested without confirming) back to 0
     *
     */
    public void resetWarnings(){
        warnings = 0;
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return true;
    }


    /** Return True iff the Meeting corresponding to the Trade has been accepted
     *
     * @return whether the Meeting has been accepted.
     */
    public boolean getMeetingAccepted(){
        return meeting.getAccepted();
    }


    /** Return True iff the Meeting corresponding to the Trade has been confirmed
     *
     * @return whether the Meeting has been confirmed.
     */
    public boolean getMeetingConfirmed(){
        return meeting.getConfirmed();
    }
}