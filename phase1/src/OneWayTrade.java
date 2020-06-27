import java.util.ArrayList;
import java.util.List;

abstract class OneWayTrade extends Trade{
    private UserAccount sender;
    private UserAccount receiver;
    private Item item; //changed item from List<Item> to Item

    public OneWayTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){
        super(tradeNumber);
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

    public List<Account> getAccounts(){
        List<Account> accounts = new ArrayList();
        accounts.add(sender);
        accounts.add(receiver);
        return accounts;
    }

    public UserAccount getSender(){
        return sender;
    }

    public UserAccount getReceiver(){
        return receiver;
    }
}