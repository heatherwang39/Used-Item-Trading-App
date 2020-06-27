import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting{
    private List meeting;
    private List meetingAccepted;
    private List meetingConfirmed;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of OneWayPermanentTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param sender The trader (UserAccount) that sent the item
     * @param receiver The trader (UserAccount) that received the item
     * @param item The item that was traded from the sender to the receiver
     */
    public OneWayPermanentTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){

        super(tradeNumber, sender, receiver, item);

        meeting = new ArrayList();

        meetingAccepted = new ArrayList();
        meetingAccepted.add(false);
        meetingAccepted.add(false);

        meetingConfirmed = new ArrayList();
        meetingConfirmed.add(false);
        meetingConfirmed.add(false);
    }


    /** Return a list representation of the scheduled meeting.
     *
     * Iff a meeting has been suggested, return a list with the String representation of the location
     * of the meeting at index 0 and the time (LocalDateTime) of the meeting at the index 1 of the list.
     *
     * Iff no meeting has been suggested, return an empty list.
     *
     * @return A list representation of the meeting
     */
    public List getMeeting(){
        ArrayList meetingCopy = new ArrayList(meeting);
        return meetingCopy;
    }


    public boolean setMeeting(String place, LocalDateTime time){
        if(time.compareTo(time.now()) < 0){
            //TODO: Raise time error?
            return false;
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        meeting = new ArrayList();
        meeting.add(place);
        meeting.add(time);
        return true;
    }


    //TODO: May change this function to raise errors
    public boolean acceptMeeting(UserAccount trader){
        if(meeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = acceptMeetingSender();
            if(getAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = acceptMeetingReceiver();
            if(getAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean acceptMeetingSender(){
        if(meetingAccepted[0]){
            return false;
        }
        meetingAccepted[0] = true;
        return true;
    }

    private boolean acceptMeetingReceiver(){
        if(meetingAccepted[1]){
            return false;
        }
        meetingAccepted[1] = true;
        return true;
    }

    public boolean getAccepted(){
        if(meeting.isEmpty()){
            return false;
        }
        return meetingAccepted[0] && meetingAccepted[1];
    }


    public boolean confirmMeeting(UserAccount trader){
        if(meeting[1].compareTo(meeting[1].now()) < 0){
            //TODO: Raise time error?
            return false;
        }
        if(meeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmMeetingSender();
            if (getConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmMeetingReceiver();
            if (getConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmMeetingSender(){
        if(meetingConfirmed[0]){
            return false;
        }
        meetingConfirmed[0] = true;
        return true;
    }

    private boolean confirmMeetingReceiver(){
        if(meetingConfirmed[1]){
            return false;
        }
        meetingConfirmed[1] = true;
        return true;
    }

    public boolean getConfirmed(){
        if(meeting.isEmpty()){
            return false;
        }
        return meetingConfirmed[0] && meetingConfirmed[1];
    }
}