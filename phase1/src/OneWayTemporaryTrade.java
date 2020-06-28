import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private List firstMeeting;
    private List<Boolean> firstMeetingAccepted;
    private List<Boolean> firstMeetingConfirmed;
    private List secondMeeting;
    private List<Boolean> secondMeetingAccepted;
    private List<Boolean> secondMeetingConfirmed;
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

        firstMeeting = new ArrayList();

        firstMeetingAccepted = new ArrayList();
        firstMeetingAccepted.add(false);
        firstMeetingAccepted.add(false);

        firstMeetingConfirmed = new ArrayList();
        firstMeetingConfirmed.add(false);
        firstMeetingConfirmed.add(false);

        secondMeeting = new ArrayList();

        secondMeetingAccepted = new ArrayList();
        secondMeetingAccepted.add(false);
        secondMeetingAccepted.add(false);

        secondMeetingConfirmed = new ArrayList();
        secondMeetingConfirmed.add(false);
        secondMeetingConfirmed.add(false);
    }


    /** Return a list representation of the scheduled first meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the first meeting
     */
    public List getFirstMeeting(){
        List meetingCopy = new ArrayList(firstMeeting);
        return meetingCopy;
    }


    /** Return a list representation of the scheduled second meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the second meeting
     */
    public List getSecondMeeting(){
        List meetingCopy = new ArrayList(secondMeeting);
        return meetingCopy;
    }


    public boolean setFirstMeeting(String place, LocalDateTime time){
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        firstMeeting = new ArrayList();
        firstMeeting.add(place);
        firstMeeting.add(time);
        return true;
    }


    public boolean setSecondMeeting(String place, LocalDateTime time){
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        secondMeeting = new ArrayList();
        secondMeeting.add(place);
        secondMeeting.add(time);
        secondMeeting.add(false);
        secondMeeting.add(false);
        return true;
    }










    //TODO: May change this function to raise errors
    public boolean acceptSecondMeeting(UserAccount trader){
        if(secondMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = acceptSecondMeetingSender();
            if(getSecondAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = acceptSecondMeetingReceiver();
            if(getSecondAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean acceptSecondMeetingSender(){
        if(secondMeetingAccepted.get(0)){
            return false;
        }
        secondMeetingAccepted.set(0, true);
        return true;
    }

    private boolean acceptSecondMeetingReceiver(){
        if(secondMeetingAccepted.get(1)){
            return false;
        }
        secondMeetingAccepted.set(1, true);
        return true;
    }

    public boolean getSecondAccepted(){
        if(secondMeeting.isEmpty()){
            return false;
        }
        return secondMeetingAccepted.get(0) && secondMeetingAccepted.get(1);
    }









    //TODO: May change this function to raise errors
    public boolean confirmFirstMeeting(UserAccount trader){
        if(firstMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmFirstMeetingSender();
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmFirstMeetingReceiver();
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmFirstMeetingSender(){
        if(firstMeetingConfirmed.get(0)){
            return false;
        }
        firstMeetingConfirmed.set(0, true);
        return true;
    }

    private boolean confirmFirstMeetingReceiver(){
        if(firstMeetingConfirmed.get(1)){
            return false;
        }
        firstMeetingConfirmed.set(1, true);
        return true;
    }

    public boolean getFirstConfirmed(){
        return firstMeetingConfirmed.get(0) && firstMeetingConfirmed.get(1);
    }









    //TODO: May change this function to raise errors
    public boolean acceptFirstMeeting(UserAccount trader){
        if(firstMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = acceptFirstMeetingSender();
            if(getFirstAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = acceptFirstMeetingReceiver();
            if(getFirstAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean acceptFirstMeetingSender(){
        if(firstMeetingAccepted.get(0)){
            return false;
        }
        firstMeetingAccepted.set(0, true);
        return true;
    }

    private boolean acceptFirstMeetingReceiver(){
        if(firstMeetingAccepted.get(1)){
            return false;
        }
        firstMeetingAccepted.set(1, true);
        return true;
    }

    public boolean getFirstAccepted(){
        if(firstMeeting.isEmpty()){
            return false;
        }
        return firstMeetingAccepted.get(0) && firstMeetingAccepted.get(1);
    }








    //TODO: May change this function to raise errors
    public boolean confirmSecondMeeting(UserAccount trader){
        if(secondMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmSecondMeetingSender();
            if (getSecondConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmSecondMeetingReceiver();
            if (getSecondConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmSecondMeetingSender(){
        if(secondMeetingConfirmed.get(0)){
            return false;
        }
        secondMeetingConfirmed.set(0, true);
        return true;
    }

    private boolean confirmSecondMeetingReceiver(){
        if(secondMeetingConfirmed.get(1)){
            return false;
        }
        secondMeetingConfirmed.set(1, true);
        return true;
    }

    public boolean getSecondConfirmed(){
        if(secondMeeting.isEmpty()){
            return false;
        }
        return secondMeetingConfirmed.get(0) && secondMeetingConfirmed.get(1);
    }

}
