package main.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting {
    private List meeting;
    private List meetingAccepted;
    private List meetingConfirmed;
    private int warnings;
    private int max_warnings = 6;


    public TwoWayPermanentTrade(int tradeNumber, UserAccount trader1, Item items1,
                                UserAccount trader2, Item items2){

        super(tradeNumber, trader1, items1, trader2, items2);

        meeting = new ArrayList();

        meetingAccepted = new ArrayList();
        meetingAccepted.add(false);
        meetingAccepted.add(false);

        meetingConfirmed = new ArrayList();
        meetingConfirmed.add(false);
        meetingConfirmed.add(false);
    }


    public Optional<List> getMeeting(){
        if(meeting.isEmpty()){
            return null;
        }
        List meetingCopy = new ArrayList(meeting);
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
        if(getFirstTrader().equals(trader)){
            boolean confirmation = acceptMeetingSecondTrader();
            if(getAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getSecondTrader().equals(trader)){
            boolean confirmation = acceptMeetingSecondTrader();
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

    private boolean acceptMeetingFirstTrader(){
        if(meetingAccepted[0]){
            return false;
        }
        meetingAccepted[0] = true;
        return true;
    }

    private boolean acceptMeetingSecondTrader(){
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
        if(meeting[1].compareTo(meeting[1].now() < 0){
            //TODO: Raise time error?
            return false;
        }
        if(meeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getFirstTrader().equals(trader)){
            boolean confirmation = confirmMeetingFirstTrader();
            if (getConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        if(getSecondTrader().equals(trader)){
            boolean confirmation = confirmMeetingSecondTrader();
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

    private boolean confirmMeetingFirstTrader(){
        if(meetingConfirmed[0]){
            return false;
        }
        meetingConfirmed[0] = true;
        return true;
    }

    private boolean confirmMeetingSecondTrader(){
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
