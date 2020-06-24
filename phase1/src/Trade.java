import java.util.ArrayList;
import java.util.List;

abstract class Trade {
    private int tradeNumber;
    private int status;


    public Trade(int tradeNumber){
        this.tradeNumber = tradeNumber;
        status = 0;
    }


    /**
     * Return the trade number of the Trade object
     *
     * @return the trade number of the Trade object
     */
    public int getTradeNumber(){
        return tradeNumber;
    }


    /**
     * Return the status of the Trade Object.
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @return the status of the Trade Object.
     */
    public int getStatus(){
        return status;
    };


    /**
     * Changes the status of the Trade object. Iff the change was successfully made, return True.
     *
     * @param status The new status of the Trade
     * @return A boolean representing whether or not the change was made
     */
    public boolean setStatus(int status){
        if(this.status == status){
            return false;
        }
        this.status = status;
        return true;
    };


    public abstract List<Item> getItems();
    public abstract List<Account> getAccounts();
}
