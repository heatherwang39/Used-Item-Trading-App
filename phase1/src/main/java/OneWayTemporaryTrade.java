package main.java;

import java.time.LocalDateTime;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of OneWayTemporaryTrade based on the given parameters
     *
     * @param sender The trader (their username) that sent the item
     * @param receiver The trader (their username) that received the item
     * @param item The item (its ID) that was traded from the sender to the receiver
     */
    public OneWayTemporaryTrade(String sender, String receiver, int item){
        super(sender, receiver, item);
        warnings = 0;
    }


    public String getFirstMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getPlace();
    }


    public String getSecondMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return secondMeeting.getPlace();
    }


    public LocalDateTime getFirstMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getTime();
    }


    public LocalDateTime getSecondMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return secondMeeting.getTime();
    }


    public boolean setFirstMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getSender(), getReceiver());
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


    public boolean setSecondMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getSender(), getReceiver());
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


    public boolean suggestFirstMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getSender()) || suggester.equals(getReceiver())){
            if(!setFirstMeeting(place, time)){
                return false;
            }
            acceptFirstMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    public boolean suggestSecondMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getSender()) || suggester.equals(getReceiver())){
            if(!setSecondMeeting(place, time)){
                return false;
            }
            acceptSecondMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    public boolean acceptFirstMeeting(String acceptor) throws WrongAccountException {
        boolean value;
        if(acceptor.equals(getSender()) || acceptor.equals(getReceiver())){
            value = firstMeeting.acceptMeeting(acceptor);
            if(getFirstMeetingAccepted()){
                resetWarnings();
            }
            return value;
        }
        throw new WrongAccountException();
    }


    public boolean acceptSecondMeeting(String acceptor) throws WrongAccountException {
        boolean value;
        if(acceptor.equals(getSender()) || acceptor.equals(getReceiver())){
            value = secondMeeting.acceptMeeting(acceptor);
            if(getSecondMeetingAccepted()){
                resetWarnings();
            }
            return value;
        }
        throw new WrongAccountException();
    }


    public boolean confirmFirstMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getSender()) || attendee.equals(getReceiver())){
            return firstMeeting.confirmMeeting(attendee);
        }
        throw new WrongAccountException();
    }


    public boolean confirmSecondMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getSender()) || attendee.equals(getReceiver())){
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


    public boolean getFirstMeetingAccepted(){
        return firstMeeting.getAccepted();
    }


    public boolean getSecondMeetingAccepted(){
        return secondMeeting.getAccepted();
    }


    public boolean getFirstMeetingConfirmed(){
        return firstMeeting.getConfirmed();
    }


    public boolean getSecondMeetingConfirmed(){
        return secondMeeting.getConfirmed();
    }
}
