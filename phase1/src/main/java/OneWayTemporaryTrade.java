package main.java;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of OneWayTemporaryTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param sender The trader (UserAccount) that sent the item
     * @param receiver The trader (UserAccount) that received the item
     * @param item The item that was traded from the sender to the receiver
     */
    public OneWayTemporaryTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){
        super(tradeNumber, sender, receiver, item);
        warnings = 0;
    }


    public TwoPersonMeeting getFirstMeeting(){
        return firstMeeting;
    }


    public TwoPersonMeeting getSecondMeeting(){
        return secondMeeting;
    }


    public boolean setFirstMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
        if(!meeting.getAttendees().contains(getSender())){
            throw new WrongAccountException();
        }
        if(!meeting.getAttendees().contains(getReceiver())){
            throw new WrongAccountException();
        }
        if(meeting.getTime().compareTo(meeting.getTime().now()) < 0){
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


    public boolean setSecondMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
        if(!meeting.getAttendees().contains(getSender())){
            throw new WrongAccountException();
        }
        if(!meeting.getAttendees().contains(getReceiver())){
            throw new WrongAccountException();
        }
        if(meeting.getTime().compareTo(meeting.getTime().now()) < 0){
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
}
