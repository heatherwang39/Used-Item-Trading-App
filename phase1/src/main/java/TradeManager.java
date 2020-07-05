package main.java;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class TradeManager {
    private List<Trade> trades;

    /**
     * Creates a new TradeManager.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public TradeManager(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }
    }
    /** Initializes a new OneWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * OneWayTrade.
     *
     * @param permanent Whether or not the Trade is to be permanent
     * @param sender The username of the Sender of the Trade
     * @param receiver The username of the Receiver of the Trade
     * @param item The ID of the item involved in the Trade
     * @return The tradeNumber of the newly initialized Trade.
     */
    public int newOneWayTrade(Boolean permanent, String sender, String receiver, int item){
        int t;
        if(permanent){t = newOWPTrade(sender, receiver, item);}
        else{t = newOWTTrade(sender, receiver, item);}
        return t;
    }

    private int newOWPTrade(String sender, String receiver, int item){
        Trade t = new OneWayPermanentTrade(sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newOWTTrade(String sender, String receiver, int item){
        Trade t = new OneWayTemporaryTrade(sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }


    /** Initializes a new TwoWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * TwoWayTrade
     *
     * @param permanent Whether or not the Trade is to be permanent
     * @param firstTrader The username of the first trader.
     * @param firstItem The ID of the item the first trader sent in this trade
     * @param secondTrader The username of the second trader.
     * @param secondItem The ID of the item the second trader sent in this trade
     * @return The tradeNumber of the newly initialized Trade.
     */
    public int newTwoWayTrade(Boolean permanent, String firstTrader, int firstItem,
                               String secondTrader, int secondItem){
        int t;
        if(permanent){t = newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{t = newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
        return t;
    }

    private int newTWPTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayPermanentTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newTWTTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayTemporaryTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }

    /**
     * Reads the trades from file at filepath, populating trades.
     *
     * @param path the file to read the trades from
     * @throws IOException
     */
    public void readFromFile(String path) throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        trades = (List<Trade>) input.readObject();
        input.close();
    }

    /**
     * Writes the trades to the file at filepath.
     *
     * @param filePath the file to write the trades to
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(trades);
        Trade.setTotalNumTrades(trades.size()); //TODO: Consider alternative way to update total num of trades
        output.close();
    }

    /** Returns the Trade that corresponds to the given tradeNumber
     *
     * @param tradeNumber The tradeNumber of the Trade that is to be returned
     * @return The Trade that corresponds to the given tradeNumber
     * @throws TradeNumberException If no Trade with the given tradeNumber can be found
     */
    public Trade getTrade(int tradeNumber) throws TradeNumberException{
        for(Trade t:trades){
            if(t.getTradeNumber() == tradeNumber){
                return t;
            }
        }
        throw new TradeNumberException();
    }
}