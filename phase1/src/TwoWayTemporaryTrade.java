import java.util.List;
import java.util.Optional;

public class TwoWayTemporaryTrade extends TwoWayTrade implements TwoMeetings {
    private Optional<List> firstMeeting;
    private Optional<List> secondMeeting;
    private int warnings;


    public TwoWayTemporaryTrade(int tradeNumber, UserAccount trader1, List<Item> items1,
                                     UserAccount trader2, List<Item> items2){
        super(tradeNumber, trader1, items1, trader2, items2);
    }

    //TODO: Finish Implementing the Meeting system

}
