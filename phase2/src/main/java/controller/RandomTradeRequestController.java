package main.java.controller;


import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.WrongAccountTypeException;
import main.java.model.item.ItemStorage;
import main.java.model.trade.*;
import main.java.system.StorageDepot;
import main.java.system.StorageEnum;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller that returns the needed information for GUI client to display for RandomTradeRequest tab
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class RandomTradeRequestController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final ItemStorage itemStorage;
    private final AccountStorage accountStorage;

    private final String username;
    private List<String> traders;
    private List<Integer> items;

    private final int REQUIRED_NUM_TRADERS = 4;

    /**
     * Initializes a new RandomTradeRequestController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     * @param username username of the user accessing the Request tab
     */
    public RandomTradeRequestController(StorageDepot storageDepot, String username) throws IOException, ClassNotFoundException {
        storageGateway = storageDepot.getStorageGateway();
        this.username = username;
        tradeStorage = storageDepot.getTradeStorage();
        itemStorage = storageDepot.getItemStorage();
        accountStorage = storageDepot.getAccountStorage();

        traders = new ArrayList<>();
        items = new ArrayList<>();
    }

    /** Add a Trader to this Trade
     *
     * @param trader The trader you want to add to this Trade
     * @throws UserAlreadyAddedException Thrown if the Trader is already added
     * @throws NoValidItemsFromGivenUserException Thrown if the Trader doesn't have the right items to participate
     * @throws AccountNotFoundException Thrown if the Trader can't be found
     * @throws WrongAccountTypeException Thrown if the Trader is of the right Account type
     */
    public void addTrader(String trader) throws UserAlreadyAddedException, NoValidItemsFromGivenUserException,
            AccountNotFoundException, WrongAccountTypeException {

        if(!accountStorage.getType(trader).equals("USER")){throw new WrongAccountTypeException();}
        else if(traders.contains(trader) | trader.equals(username)){throw new UserAlreadyAddedException();}

        List<Integer> possibleItems = itemStorage.getUnhiddenInventory(trader);
        if(possibleItems.isEmpty()){throw new NoValidItemsFromGivenUserException();}
        else{
            traders.add(trader);

            items.add(possibleItems.get(new Random().nextInt(possibleItems.size())));
            //The above line is from: https://stackoverflow.com/questions/12487592/randomly-select-an-item-from-a-list
        }
    }

    /** Create a Random Trade Offer Request
     *
     * @param permanent Whether this Random Trade is Permanent or Not
     * @throws ItemAlreadyInActiveTradeException Thrown if one of the randomly selected items is invalid
     * @throws NoSuchTradeAlgorithmException Thrown if the Trade Algorithm hasn't been implemented yet
     * @throws TradeNumberException Thrown if there was trouble initializing the Trade
     * @throws WrongTradeAccountException Thrown if the Trader is of the Wrong Account Type
     * @throws TradeCancelledException Thrown if the Trade was cancelled in the process of its construction
     * @throws IOException Thrown if there's an error reading/writing from the file
     * @throws TooFewTradersException Thrown if not enough traders are involved
     * @throws NoValidItemsFromGivenUserException Thrown if the given user doesn't have any valid items to contribute
     */
    public void createRequest(boolean permanent) throws ItemAlreadyInActiveTradeException,
            NoSuchTradeAlgorithmException, TradeNumberException, WrongTradeAccountException,
            TradeCancelledException, IOException, TooFewTradersException, NoValidItemsFromGivenUserException {

        if(traders.size() < REQUIRED_NUM_TRADERS){throw new TooFewTradersException();}
        List<Integer> possibleItems = itemStorage.getUnhiddenInventory(username);
        if(possibleItems.isEmpty()){throw new NoValidItemsFromGivenUserException();}

        traders.add(username);
        items.add(possibleItems.get(new Random().nextInt(possibleItems.size())));

        int tradeNumber = tradeStorage.newTrade(permanent, TradeAlgorithmName.RANDOM, traders, items);
        tradeStorage.acceptTrade(tradeNumber, username);
        storageGateway.saveStorageData();

        traders = new ArrayList<>();
        items = new ArrayList<>();
    }
}