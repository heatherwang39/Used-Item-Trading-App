import java.util.List;
import java.util.Optional;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting {
    private Optional<List> meeting;
    private int warnings;


    public TwoWayPermanentTrade(int tradeNumber, UserAccount trader1, List<Item> items1,
                                UserAccount trader2, List<Item> items2){
        super(tradeNumber, trader1, items1, trader2, items2);
    }

    //TODO: Finish Implementing the Meeting system

}
