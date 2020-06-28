package main.java;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting{
    private TwoPersonMeeting meeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of main.java.OneWayPermanentTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param sender The trader (main.java.UserAccount) that sent the item
     * @param receiver The trader (main.java.UserAccount) that received the item
     * @param item The item that was traded from the sender to the receiver
     */
    public OneWayPermanentTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){
        super(tradeNumber, sender, receiver, item);
        warnings = 0;
    }


    public TwoPersonMeeting getMeeting(){
        return meeting;
    }


    public boolean setMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
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
        this.meeting = meeting;
        return true;
    }
}