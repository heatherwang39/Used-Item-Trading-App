package main.java;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting {
    private TwoPersonMeeting meeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayPermanentTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param firstTrader The first trader (UserAccount) involved in this trade
     * @param firstItem The item the first trader traded away
     * @param secondTrader The second trader (UserAccount) involved in this trade
     * @param secondItem The item the second trader traded away
     */
    public TwoWayPermanentTrade(int tradeNumber, UserAccount firstTrader, Item firstItem,
                                UserAccount secondTrader, Item secondItem){

        super(tradeNumber, firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    public TwoPersonMeeting getMeeting(){
        return meeting;
    }


    public boolean setMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
        if(!meeting.getAttendees().contains(getFirstTrader())){
            throw new WrongAccountException();
        }
        if(!meeting.getAttendees().contains(getSecondTrader())){
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
