import java.util.ArrayList;
import java.util.List;

public class TradeManager {
    private static int numTrades = 0;
    private List<Trade> trades;

    //TODO: Create a public method that creates new trades of the required type, using the helper methods below

    private void newOWPTrade(UserAccount sender, UserAccount receiver, List<Item> items){
        Trade t = new OneWayPermanentTrade(numTrades + 1, sender, receiver, items);
        numTrades++;
        trades.add(t);
    }

    private void newOWTTrade(UserAccount sender, UserAccount receiver, List<Item> items){
        Trade t = new OneWayTemporaryTrade(numTrades + 1, sender, receiver, items);
        numTrades++;
        trades.add(t);
    }

    private void newTWPTrade(UserAccount trader1, List<Item> items1, UserAccount trader2, List<Item> items2){
        Trade t = new TwoWayPermanentTrade(numTrades + 1, trader1, items1, trader2, items2);
        numTrades++;
        trades.add(t);
    }

    private void newTWTTrade(UserAccount trader1, List<Item> items1, UserAccount trader2, List<Item> items2){
        Trade t = new TwoWayTemporaryTrade(numTrades + 1, trader1, items1, trader2, items2);
        numTrades++;
        trades.add(t);
    }



    public Trade getTrade(int tradeNumber){
        for(Trade t:trades){
            if(t.getTradeNumber() == tradeNumber){
                return t;
            }
        }
        //TODO: Create an Error if no Trade has the given TradeNumber
    }

}
