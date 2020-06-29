package main.java;

/**
 *
 */
abstract class TwoWayTrade extends Trade{
    private String trader1;
    private int item1;
    private String trader2;
    private int item2;


    /** Initializes an instance of TwoWayTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTrade(String firstTrader, int firstItem,
                       String secondTrader, int secondItem){
        trader1 = firstTrader;
        item1 = firstItem;
        trader2 = secondTrader;
        item2 = secondItem;
    };


    /** Retrieve the first trader (their username) involved in this trade
     *
     * @return The first trader (their username) involved in this trade
     */
    public String getFirstTrader(){
        return trader1;
    }

    /** Retrieve the item the first trader (their username) sent in this trade
     *
     * @return The item the first trader (their username) sent in this trade
     */
    public int getFirstTraderItem(){
        return item1;
    }


    /** Retrieve the second trader (their username) involved in this trade
     *
     * @return The second trader (their username) involved in this trade
     */
    public String getSecondTrader(){
        return trader2;
    }


    /** Retrieve the item the second trader (their username) sent in this trade
     *
     * @return The item the second trader (their username) sent in this trade
     */
    public int getSecondTraderItem(){
        return item2;
    }


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    public boolean isOneWay(){
        return false;
    }
}