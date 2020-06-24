import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneWayPermanentTrade extends OneWayTrade implements OneMeeting{
    private Optional<List> meeting;


    public OneWayPermanentTrade(int tradeNumber, UserAccount sender, UserAccount receiver, List<Item> items){
        super(tradeNumber, sender, receiver, items);
    }

    public Optional<List> getMeeting(){
        return meeting;
    };


    public boolean setMeeting(){

    };

}
