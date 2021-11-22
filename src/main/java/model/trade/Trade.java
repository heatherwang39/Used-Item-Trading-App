package main.java.model.trade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract class in the Trade system representing a trade in the program. All trades
 * store a trade number, a status, meeting IDs and associated items/traders for each trade.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public abstract class Trade implements Serializable {
    private final int tradeNumber;
    private int status;

    private final TradeAlgorithm tradeAlgorithm;

    private final List<String> traders;
    private final List<Integer> items;

    private final List<Boolean> accepted;

    private int warnings;
    private int maxWarnings;

    private List<Integer> meetings;
    private int numberOfMeetings;

    private LocalDateTime completionTime;

    //Basic Trade Methods begin here


    /** Initialize a new instance of Trade. The default status of the trade will be set to 0,
     * and the Trade will be assigned the tradeNumber that's given
     *
     * @param tradeNumber The unique TradeNumber associated with this trade
     * @param tradeAlgorithm The algorithm that determines how the trade functions
     * @param traders The traders involved in this trade, with the person who made the request stored at the index 0.
     * @param items The items involved in this trade in the order of their owners (in traders)
     */
    public Trade(int tradeNumber, TradeAlgorithm tradeAlgorithm, List<String> traders, List<Integer> items){
        this.tradeNumber = tradeNumber;
        this.tradeAlgorithm = tradeAlgorithm;
        this.traders = traders;
        this.accepted = new ArrayList<>(Collections.nCopies(traders.size(), false));
        this.items = items;
        meetings = new ArrayList<>();
        status = 0;
        maxWarnings = 6;
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
     * 1 represents that the trade has been accepted but no items have been exchanged yet
     * 2 represents that the trade is ongoing (items have been exchanged)
     * 3 represents that the trade has been completed
     *
     * @return the status of the Trade Object.
     */
    public int getStatus(){
        return status;
    }


    /**
     * Changes the status of the Trade object. Iff the change was successfully made, return True.
     *
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade has been accepted but no items have been exchanged yet
     * 2 represents that the trade is ongoing (items have been exchanged)
     * 3 represents that the trade has been completed
     *
     * @param status The new status of the Trade
     * @return A boolean representing whether or not the change was made
     */
    public boolean setStatus(int status){
        if(this.status == status){
            return false;
        }
        this.status = status;
        if(status == 3){
            setCompletionTime(LocalDateTime.now());
        }
        return true;
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    abstract boolean isPermanent();


    /** Returns whether or not the Trade is one-way (i.e., a two person trade where only one trader gives the other
     * trader an items. Iff the Trade is one-way, return true.)
     *
     * @return whether the Trade is one-way
     */
    public boolean isOneWay(){
        return traders.size() == 2 && items.contains(null);
    }


    /** Return the username of the trader who made the trade request
     *
     * @return the trader who made the trade request
     */
    public String getTradeInitializer(){
        return traders.get(0);
    }


    /** Return the name of the algorithm that determines how items are exchanged.
     *
     * @return The trade's algorithm's name.
     */
    public String getAlgorithmName(){
        return tradeAlgorithm.getTradeAlgorithmName();
    }


    //Methods regarding warnings below


    /** Increases the number of warnings by 1. If the number of warnings reaches the maximum number of warnings,
     * the trade is automatically cancelled
     */
    public void warn(){
        warnings++;
        if(warnings >= maxWarnings){
            setStatus(0);
        }
    }


    /**
     * Return the number of warnings received
     *
     * @return number of warnings
     */
    public int getWarnings() { return warnings; }


    /**
     * Return the maximum number of warnings allowed
     *
     * @return maximum number of warnings
     */
    public int getMaxWarnings() { return maxWarnings; }


    /** Resets the Warnings for meetings to 0.
     */
    public void resetWarnings(){
        warnings = 0;
    }


    /** Sets the Maximum Number of Warnings for this Trade
     *
     * @param maxWarnings What the new Maximum Number of Warnings for this Trade will be
     */
    public void setMaxWarnings(int maxWarnings){
        this.maxWarnings = maxWarnings;
    }

    //Meetings Methods Below

    /** Set the meetings for this trade.
     *
     * Precondition: meetings.size <= getTotalNumMeetings()
     * Precondition: meetings contains no duplicate MeetingIDs
     *
     * @param meetings What the meetings for this trade will be set to.
     */
    public void setMeetings(List<Integer> meetings) {
        this.meetings = meetings;
    }


    /** Add a meetings to this trade.
     *
     * Precondition: meetings.size <= getTotalNumMeetings()
     * Precondition: The meeting to be added isn't already in the list of meetings
     *
     * @param meetingID The meeting(ID) that you're adding to this trade
     */
    public void addMeeting(int meetingID){
        meetings.add(meetingID);
        warn();
    }


    /** Remove the last meeting that has been suggested in this trade
     *
     */
    public void removeLastMeeting(){
        meetings.remove(meetings.size() - 1);
    }


    /** Remove the given meeting
     *
     * Precondition: The meeting is part of this Trade
     *
     * @param meetingID The meeting(ID) that you're removing from this trade
     */
    public void removeMeeting(int meetingID){
        meetings.remove(meetings.lastIndexOf(meetingID));
    }


    /** Return a list consisting of all the IDs of the suggested meetings
     *
     * @return A list consisting of all the IDs of the suggested meetings
     */
    public List<Integer> getMeetings(){
        return new ArrayList<>(meetings);
    }


    /** Get the total number of meetings that should occur in this trade
     *
     * @return The total number of meetings that should occur in this trade
     */
    public int getTotalNumMeetings(){
        return numberOfMeetings;
    }


    /** Set the total number of meetings that should occur in this trade to numMeetings
     *
     * @param numMeetings The total number of meetings that should occur for this trade
     */
    public void setNumMeetings(int numMeetings){
        if(numMeetings >= 0){
            numberOfMeetings = numMeetings;
        }
    }

    //Methods about Traders and Items Exchanged Below


    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @return Usernames of Traders involved in this trade
     */
    public List<String> getTraders(){
        return new ArrayList<>(traders);
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and was originally owned by the Trader at the given
     * index in getTraders(). Iff the specified Trader has no item that he/she is involving in trade and originally
     * owned, store null at the particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsOriginal(){
        return new ArrayList<>(items);
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be held by the Trader at the given
     * index in getTraders() after the first transaction. Iff the specified Trader has no item that he/she is involving
     * in trade and originally owned, store null at the particular index.
     *
     * @return A list of item IDs involved in this trade based on the owners after the first transaction
     */
    public List<Integer> getItemsExchanged(){
        return tradeAlgorithm.getExchangedItems(new ArrayList<>(items));
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be own by the Trader at the given
     * index in getTraders() at the end of the trade. Iff the specified Trader will not receive an item that at the end
     * of the trade, store null at that particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    abstract List<Integer> getItemsFinal();


    //Methods regarding who has trade acceptance


    /** Return the usernames of the Traders that haven't accepted this trade yet
     *
     * @return the traders that haven't accepted the trade
     */
    public List<String> getUnacceptedTraders(){
        List<String> unaccepted = new ArrayList<>();
        for (int i = 0; i < traders.size(); i++) if(!accepted.get(i)) unaccepted.add(traders.get(i));
        return unaccepted;
    }


    /** Record the fact that the trader has accepted the trade. If all traders have accepted the trade, automatically
     * update the trade's status
     *
     * @param trader The trader accepting the trade
     */
    public void acceptTrade(String trader){
        int i = traders.indexOf(trader);
        if(!accepted.get(i)){
            accepted.set(i, true);
        }
        if(checkAccepted()){
            if(numberOfMeetings == 0){setStatus(3);}
            else {setStatus(1);}
        }
    }


    /** Return True if the Trade is Accepted by all Traders
     *
     * @return Whether the Trade is Accepted by all Traders
     */
    public boolean checkAccepted(){
        for(Boolean a: accepted){
            if(!a){
                return false;
            }
        }
        return true;
    }


    //Confirmation Time Methods

    /** Set the Completion time of this Trade to be the following
     *
     * @param completionTime The Completion time for this Trade
     */
    public void setCompletionTime(LocalDateTime completionTime){
        this.completionTime = completionTime;
    }

    /** Get the Completion time of this Trade
     *
     * @return the Completion time of this Trade
     */
    public LocalDateTime getCompletionTime(){
        return completionTime;
    }
}