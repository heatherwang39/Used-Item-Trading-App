import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private List firstMeeting;
    private List firstMeetingAccepted;
    private List firstMeetingConfirmed;
    private List secondMeeting;
    private List secondMeetingAccepted;
    private List secondMeetingConfirmed;
    private int warnings;
    private int max_warnings = 6;


    public OneWayTemporaryTrade(int tradeNumber, UserAccount sender, UserAccount receiver, List<Item> items){
        super(tradeNumber, sender, receiver, items);
        firstMeeting = new ArrayList();
        secondMeeting = new ArrayList();
    }


    public Optional<List> getFirstMeeting(){
        if(firstMeeting.isEmpty()){
            return null;
        }
        List meetingCopy = new ArrayList(firstMeeting);
        return meetingCopy;
    }


    public Optional<List> getSecondMeeting(){
        if(secondMeeting.isEmpty()){
            return null;
        }
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
    private boolean confirmFirstMeeting(UserAccount trader){
        if(firstMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmFirstMeetingSender();
            if(getFirstConfirmed()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmFirstMeetingReceiver();
            if(getFirstConfirmed()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmFirstMeetingSender(){
        if(firstMeetingConfirmed[0]){
            return false;
        }
        firstMeetingConfirmed[0] = true;
        return true;
    }

    private boolean confirmFirstMeetingReceiver(){
        if(firstMeetingConfirmed[1]){
            return false;
        }
        firstMeetingConfirmed[1] = true;
        return true;
    }

    public boolean getFirstConfirmed(){
        return firstMeeting[0] && firstMeeting[1];
    }








    //TODO: May change this function to raise errors
    private boolean confirmSecondMeeting(UserAccount trader){
        if(secondMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmSecondMeetingSender();
            if(getSecondConfirmed()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmSecondMeetingReceiver();
            if(getSecondConfirmed()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmSecondMeetingSender(){
        if(secondMeeting[2]){
            return false;
        }
        secondMeeting[2] = true;
        return true;
    }

    private boolean confirmSecondMeetingReceiver(){
        if(secondMeeting[0]){
            return false;
        }
        return true;
    }

    public boolean getSecondConfirmed(){
        if(secondMeeting.isEmpty()){
            return false;
        }
        return secondMeeting[0] && secondMeeting[1];
    }

}
