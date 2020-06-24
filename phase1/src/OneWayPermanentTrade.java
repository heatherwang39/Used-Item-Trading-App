import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting{
    private Optional<List> meeting;
    private int warnings;


    public OneWayPermanentTrade(int tradeNumber, UserAccount sender, UserAccount receiver, List<Item> items){
        super(tradeNumber, sender, receiver, items);
    }

    public Optional<List> getMeeting(){
        return meeting;
    };


    public boolean setMeeting(List ){
        warnings += 1;
        if(warnings >= 6){
            setStatus(-1);
            return false;
        }
        meet
        return true;


        //TODO: Finish Implementing this method
    };

    //TODO: Finish Implementing the Meeting system

}
