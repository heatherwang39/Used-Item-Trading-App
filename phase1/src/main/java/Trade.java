package main.java;

abstract class Trade {
    private int tradeNumber;
    private int status;
    private Meeting[2] meeting;


    public Trade(int tradeNumber){
        this.tradeNumber = tradeNumber;
        status = 0;
    }


    /**
     * Return the trade number of the main.java.Trade object
     *
     * @return the trade number of the main.java.Trade object
     */
    public int getTradeNumber(){
        return tradeNumber;
    }


    /**
     * Return the status of the main.java.Trade Object.
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @return the status of the main.java.Trade Object.
     */
    public int getStatus(){
        return status;
    };


    /**
     * Changes the status of the main.java.Trade object. Iff the change was successfully made, return True.
     *
     * @param status The new status of the main.java.Trade
     * @return A boolean representing whether or not the change was made
     */
    public boolean setStatus(int status){
        if(this.status == status){
            return false;
        }
        this.status = status;
        return true;
    }

    /**
     * Sets the meeting of this main.java.Trade object. Returns nothing.
     * @param m The meeting location of this main.java.Trade object.
     */
    public void setMeeting(OneMeeting m){
        if (meeting[0] == null) {
            meeting[0] = m;
        }
        else{
            meeting[1] = m;
        }
    }
    public void setMeeting(TwoMeeting m){meeting[0] = m[0]; meeting[1] = m[1]}

    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    abstract boolean isPermanent();


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    abstract boolean isOneWay();
}
