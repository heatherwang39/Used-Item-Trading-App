package main.java;

import java.time.LocalDateTime;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting {
    private TwoPersonMeeting meeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayPermanentTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayPermanentTrade(String firstTrader, int firstItem,
                                String secondTrader, int secondItem){

        super(firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    /** Return the Meeting suggested with this Trade. If no meeting has been suggested,
     * a NoMeetingException will be thrown
     *
     * @return The Meeting associated with this Trade
     * @throws NoMeetingException No Meeting has been suggested
     */
    public TwoPersonMeeting getMeeting() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting;
    }


    /** Suggest a Meeting for this Trade. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this Trade.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    public boolean setMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
        if(!meeting.getAttendees().contains(getFirstTrader())){
            throw new WrongAccountException();
        }
        if(!meeting.getAttendees().contains(getSecondTrader())){
            throw new WrongAccountException();
        }
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
}
