abstract class OneWayTrade extends Trade{
    private UserAccount sender;
    private UserAccount receiver;
    private Item item; //changed item from List<Item> to Item


    /** Initializes an instance of OneWayTrade based on the given parameters
     *
     * @param tradeNumber The tradeNumber corresponding to this trade
     * @param sender The trader (UserAccount) that sent the item
     * @param receiver The trader (UserAccount) that received the item
     * @param item The item that was traded from the sender to the receiver
     */
    public OneWayTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){
        super(tradeNumber);
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
    }

    /** Retrieve the item involved with this trade
     *
     * @return The item involved with this trade
     */
    public Item getItem(){
        return item;
    }

    /** Retrieve the trader (UserAccount) that sent the item away in this trade
     *
     * @return The trader (UserAccount) that sent the item away in this trade
     */
    public UserAccount getSender(){
        return sender;
    }


    /** Retrieve the trader (UserAccount) that received an item in this trade
     *
     * @return The trader (UserAccount) that received an item in this trade
     */
    public UserAccount getReceiver(){
        return receiver;
    }
}