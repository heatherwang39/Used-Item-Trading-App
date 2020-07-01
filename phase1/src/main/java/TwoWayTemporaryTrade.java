package main.java;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TwoWayTemporaryTrade extends TwoWayTrade implements TwoMeetings, Serializable {
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayTemporaryTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTemporaryTrade(String firstTrader, int firstItem,
                                String secondTrader, int secondItem) {

        super(firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    /** Return the place of the suggested first Meeting for this Trade. If no first Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The place of the suggested first Meeting
     * @throws NoMeetingException (Thrown if no first meeting has been suggested)
     */
    public String getFirstMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getPlace();
    }


    /** Return the place of the suggested second Meeting for this Trade. If no second Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The place of the suggested second Meeting
     * @throws NoMeetingException (Thrown if no second meeting has been suggested)
     */
    public String getSecondMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return secondMeeting.getPlace();
    }


    /** Return the time of the first suggested Meeting for this Trade. If no first Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The time of the suggested first Meeting
     * @throws NoMeetingException (Thrown if no first meeting has been suggested)
     */
    public LocalDateTime getFirstMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getTime();
    }


    /** Return the second time of the suggested Meeting for this Trade. If no second Meeting has been suggested, throw
     * a NoMeetingException
     *
     * @return The time of the suggested second Meeting
     * @throws NoMeetingException (Thrown if no second meeting has been suggested)
     */
    public LocalDateTime getSecondMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
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
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getFirstTrader(), getSecondTrader());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        firstMeeting = meeting;
        return true;
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
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getFirstTrader(), getSecondTrader());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        secondMeeting = meeting;
        return true;
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

        if(suggester.equals(getFirstTrader()) || suggester.equals(getSecondTrader())){
            if(!setFirstMeeting(place, time)){
                return false;
            }
            acceptFirstMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
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

        if(suggester.equals(getFirstTrader()) || suggester.equals(getSecondTrader())){
            if(!setSecondMeeting(place, time)){
                return false;
            }
            acceptSecondMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    /** Attempt to record the fact that acceptor has accepted the suggested first Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested first Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no first meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this first meeting
     */
    public boolean acceptFirstMeeting(String acceptor) throws WrongAccountException {
        boolean value;
        if(acceptor.equals(getFirstTrader()) || acceptor.equals(getSecondTrader())){
            value = firstMeeting.acceptMeeting(acceptor);
            if(getFirstMeetingAccepted()){
                resetWarnings();
            }
            return value;
        }
        throw new WrongAccountException();
    }


    /** Attempt to record the fact that acceptor has accepted the suggested second Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested second Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no second meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this second meeting
     */
    public boolean acceptSecondMeeting(String acceptor) throws WrongAccountException {
        boolean value;
        if(acceptor.equals(getFirstTrader()) || acceptor.equals(getSecondTrader())){
            value = secondMeeting.acceptMeeting(acceptor);
            if(getSecondMeetingAccepted()){
                resetWarnings();
            }
            return value;
        }
        throw new WrongAccountException();
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
    public boolean confirmFirstMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getFirstTrader()) || attendee.equals(getSecondTrader())){
            return firstMeeting.confirmMeeting(attendee);
        }
        throw new WrongAccountException();
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
    public boolean confirmSecondMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getFirstTrader()) || attendee.equals(getSecondTrader())){
            return secondMeeting.confirmMeeting(attendee);
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
