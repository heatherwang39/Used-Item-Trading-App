import java.util.ArrayList;
import java.util.List;

abstract class TwoWayTrade extends Trade{
    private UserAccount trader1;
    private List<Item> items1;
    private UserAccount trader2;
    private List<Item> items2;

    public TwoWayTrade(int tradeNumber, UserAccount trader1, List<Item> items1,
                       UserAccount trader2, List<Item> items2){
        super(tradeNumber);
        this.trader1 = trader1;
        this.items1 = items1;
        this.trader2 = trader2;
        this.items2 = items2;
    };


    public List<Item> getItems(){
        List items = new ArrayList();
        items.addAll(items1);
        items.addAll(items2);
        return items;
    };

    
    public List<Account> getAccounts(){
        List accounts = new ArrayList();
        accounts.add(trader1);
        accounts.add(trader2);
        return accounts;
    };


    public List firstTraderItems(){
        List information = new ArrayList();
        information.add(trader1);
        information.add(items1);
        return information;
    }


    public List secondTraderItems(){
        List information = new ArrayList();
        information.add(trader2);
        information.add(items2);
        return information;
    }
}