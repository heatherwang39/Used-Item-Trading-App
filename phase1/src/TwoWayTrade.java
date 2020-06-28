abstract class TwoWayTrade extends Trade{
    private UserAccount trader1;
    private Item item1;
    private UserAccount trader2;
    private Item item2;


    /** Initializes an instance of TwoWayTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param firstTrader The first trader (UserAccount) involved in this trade
     * @param firstItem The item the first trader traded away
     * @param secondTrader The second trader (UserAccount) involved in this trade
     * @param secondItem The item the second trader traded away
     */
    public TwoWayTrade(int tradeNumber,
                       UserAccount firstTrader, Item firstItem,
                       UserAccount secondTrader, Item secondItem){
        super(tradeNumber);
        trader1 = firstTrader;
        item1 = firstItem;
        trader2 = secondTrader;
        item2 = secondItem;
    };


    /** Retrieve the first trader (UserAccount) involved in this trade
     *
     * @return The first trader (UserAccount) involved in this trade
     */
    public UserAccount getFirstTrader(){
        return trader1;
    }

    /** Retrieve the item the first trader (UserAccount) sent in this trade
     *
     * @return The item the first trader (UserAccount) sent in this trade
     */
    public Item getFirstTraderItem(){
        return item1;
    }


    /** Retrieve the second trader (UserAccount) involved in this trade
     *
     * @return The second trader (UserAccount) involved in this trade
     */
    public UserAccount getSecondTrader(){
        return trader2;
    }


    /** Retrieve the item the second trader (UserAccount) sent in this trade
     *
     * @return The item the second trader (UserAccount) sent in this trade
     */
    public Item getSecondTraderItem(){
        return item2;
    }
}