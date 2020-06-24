import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private Optional<List> firstMeeting;
    private Optional<List> secondMeeting;
    private int warnings;



    public OneWayTemporaryTrade(int tradeNumber, UserAccount sender, UserAccount receiver, List<Item> items){
        super(tradeNumber, sender, receiver, items);
    }


    //TODO: Finish Implementing the Meeting system

}
