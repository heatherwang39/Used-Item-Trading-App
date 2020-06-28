package main.java;

import java.time.LocalDateTime;

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


    /** Return the suggested first Meeting with this Trade. If the first meeting has
     * not yet been suggested, a NoMeetingException will be thrown
     *
     * @return The first Meeting associated with this Trade
     * @throws NoMeetingException The first meeting has not yet been suggested
     */
    public TwoPersonMeeting getFirstMeeting() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting;
    }


    /** Return the suggested second Meeting with this Trade. If no meeting has been suggested,
     * a NoMeetingException will be thrown
     *
     * @return The second Meeting associated with this Trade
     * @throws NoMeetingException The second meeting has not yet been suggested
     */
    public TwoPersonMeeting getSecondMeeting() throws NoMeetingException{
        if(secondMeeting == null){
            throw new NoMeetingException();
        }
        return secondMeeting;
    }


    /** Suggest a first Meeting for this Trade. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this Trade.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    public boolean setFirstMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
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
        firstMeeting = meeting;
        return true;
    }


    /** Suggest a second Meeting for this Trade. Return True iff this suggestion has been
     * successfully recorded. Throw an exception is this suggestion is inappropriate for this Trade.
     *
     * @param meeting The suggested Meeting
     * @return True iff the suggestion has been successfully recorded
     * @throws WrongAccountException The suggested Meeting does not have the right Attendees
     * @throws TimeException The suggested Meeting is at an inappropriate time
     */
    public boolean setSecondMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException{
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
        secondMeeting = meeting;
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
        return false;
    }
}
