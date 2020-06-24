import java.util.ArrayList;
import java.util.List;

abstract class OneWayTrade extends Trade{
    private UserAccount sender;
    private UserAccount receiver;
    private List<Item> items;

    public OneWayTrade(int tradeNumber, UserAccount sender, UserAccount receiver, List<Item> items){
        super(tradeNumber);
        this.sender = sender;
        this.receiver = receiver;
        this.items = items;
    };

    public List<Item> getItems(){
        return items;
    };

    public List<Account> getAccounts(){
        List accounts = new ArrayList();
        accounts.add(sender);
        accounts.add(receiver);
        return accounts;
    };

    public UserAccount getSender(){
        return sender;
    }

    public UserAccount getReceiver(){
        return receiver;
    }
}