package main.java;

abstract class TwoWayTrade extends Trade{
    private UserAccount trader1;
    private Item item1;
    private UserAccount trader2;
    private Item item2;


    /** Initializes an instance of main.java.TwoWayTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param firstTrader The first trader (main.java.UserAccount) involved in this trade
     * @param item1 The item the first trader traded away
     * @param secondTrader The second trader (main.java.UserAccount) involved in this trade
     * @param item2 The item the second trader traded away
     */
    public TwoWayTrade(int tradeNumber,
                       UserAccount firstTrader, Item item1,
                       UserAccount secondTrader, Item item2){
        super(tradeNumber);
        this.trader1 = firstTrader;
        this.item1 = item1;
        this.trader2 = secondTrader;
        this.item2 = item2;
    };


    /** Retrieve the first trader (main.java.UserAccount) involved in this trade
     *
     * @return The first trader (main.java.UserAccount) involved in this trade
     */
    public UserAccount getFirstTrader(){
        return trader1;
    }

    /** Retrieve the item the first trader (main.java.UserAccount) sent in this trade
     *
     * @return The item the first trader (main.java.UserAccount) sent in this trade
     */
    public Item getFirstTraderItem(){
        return item1;
    }


    /** Retrieve the second trader (main.java.UserAccount) involved in this trade
     *
     * @return The second trader (main.java.UserAccount) involved in this trade
     */
    public UserAccount getSecondTrader(){
        return trader2;
    }


    /** Retrieve the item the second trader (main.java.UserAccount) sent in this trade
     *
     * @return The item the second trader (main.java.UserAccount) sent in this trade
     */
    public Item getSecondTraderItem(){
        return item2;
    }
}