package main.java;

public class TwoWayTemporaryTrade extends TwoWayTrade implements TwoMeetings {
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayTemporaryTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param firstTrader The first trader (UserAccount) involved in this trade
     * @param firstItem The item the first trader traded away
     * @param secondTrader The second trader (UserAccount) involved in this trade
     * @param secondItem The item the second trader traded away
     */
    public TwoWayTemporaryTrade(int tradeNumber, UserAccount firstTrader, Item firstItem,
                                     UserAccount secondTrader, Item secondItem) {

        super(tradeNumber, firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    public TwoPersonMeeting getFirstMeeting(){
        return firstMeeting;
    }


    public TwoPersonMeeting getSecondMeeting(){
        return secondMeeting;
    }


    public boolean setFirstMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
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
        firstMeeting = meeting;
        return true;
    }


    public boolean setSecondMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
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
        secondMeeting = meeting;
        return true;
    }
}
