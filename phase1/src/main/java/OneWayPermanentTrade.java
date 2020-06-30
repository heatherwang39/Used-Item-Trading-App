package main.java;

import java.sql.Time;
import java.time.LocalDateTime;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting{
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


    public String getMeetingPlace() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getPlace();
    }


    public LocalDateTime getMeetingTime() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getTime();
    }


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


    public boolean acceptMeeting(String acceptor) throws WrongAccountException {
        if(acceptor.equals(getSender()) || acceptor.equals(getReceiver())){
            meeting.acceptMeeting(acceptor);
        }
        throw new WrongAccountException();
    }

    public boolean confirmMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getSender()) || attendee.equals(getReceiver())){
            meeting.acceptMeeting(attendee);
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




    public boolean getMeetingAccepted(){
        return meeting.getAccepted();
    }


    public boolean getMeetingConfirmed(){
        return meeting.getConfirmed();
    }
}