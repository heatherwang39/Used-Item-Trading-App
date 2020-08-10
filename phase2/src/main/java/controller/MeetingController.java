package main.java.controller;

import main.java.model.item.ItemStorage;
import main.java.model.meeting.*;
import main.java.model.trade.MaxNumMeetingsExceededException;
import main.java.model.trade.TradeCancelledException;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Controller that returns the needed information for GUI client to display for Meeting tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class MeetingController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final ItemStorage itemStorage;
    private final MeetingStorage meetingStorage;
    private final String username;

    /**
     * Initializes a new MeetingController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Request tab
     */
    public MeetingController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.TRADE);
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.ITEM);
        meetingStorage = (MeetingStorage) sf.getStorage(storageGateway, StorageEnum.MEETING);
    }


    //Methods related to creating meetings from accepted trade requests


    /**
     * Gets all accepted trades involving user that have not set a meeting yet
     *
     * @return List of hashmaps that contain trade data
     * @throws TradeNumberException invalid trade id found in system
     */
    public List<HashMap<String, List<String>>> getAcceptedTrades() throws TradeNumberException {
        return tradeStorage.getTradesData(tradeStorage.getAcceptedTradesWithUser(username));
    }

    /**
     * Suggests a meeting for the trade with given tradeNumber
     *
     * @param tradeNumber id of Trade
     * @param place suggested location
     * @param time suggested time
     * @throws TradeNumberException invalid trade id
     * @throws TradeCancelledException trade is cancelled, no longer valid
     * @throws MaxNumMeetingsExceededException too many meeting suggested, trade is now cancelled
     */
    public void suggestMeeting(int tradeNumber, String place, LocalDateTime time) throws TradeNumberException,
            IOException, TradeCancelledException, MaxNumMeetingsExceededException {
        List<String> attendees = tradeStorage.getTraders(tradeNumber);
        tradeStorage.addMeeting(tradeNumber, meetingStorage.newMeeting(attendees, place, time));
        storageGateway.saveStorageData(StorageEnum.MEETING);
    }


    //Methods related to interacting with suggested meetings


    /**
     * Gets all meeting suggestions the user has not replied to yet
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getSuggestedMeetings() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getSuggestedMeetings(username));
    }

    /**
     * Accepts the suggested meeting
     *
     * @param meetingId id of meeting
     * @throws WrongMeetingAccountException user is not a part of the meeting
     * @throws MeetingIDException invalid meeting id
     */
    public void acceptMeeting(int meetingId) throws WrongMeetingAccountException, MeetingIDException, IOException {
        meetingStorage.acceptMeeting(meetingId, username);
        storageGateway.saveStorageData(StorageEnum.MEETING);
    }

    /**
     * Rejects the suggested/ongoing meeting and cancels it
     *
     * @param meetingId id of meeting
     * @throws MeetingAlreadyConfirmedException meeting has already been confirmed, cannot be cancelled
     * @throws MeetingIDException invalid meeting id
     */
    public void rejectMeeting(int meetingId) throws MeetingAlreadyConfirmedException, MeetingIDException, IOException {
        meetingStorage.cancelMeeting(meetingId);
        storageGateway.saveStorageData(StorageEnum.MEETING);
        storageGateway.saveStorageData(StorageEnum.TRADE);
    }


    //Methods related to interacting with ongoing meetings


    /**
     * Gets all meetings the user is participating in that are currently ongoing
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getOngoingMeetings() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getOngoingMeetings(username));
    }

    /**
     * Confirms that the ongoing meeting has been complete and that the items were exchanged
     *
     * @param meetingId id of meeting
     * @throws WrongMeetingAccountException user is not a part of the meeting
     * @throws MeetingIDException invalid meeting id
     * @throws TimeException The meeting isn't suppose to have taken place yet
     */
    public void confirmMeeting(int meetingId) throws WrongMeetingAccountException, MeetingIDException, TimeException,
            IOException {
        meetingStorage.confirmMeeting(meetingId, username);
        storageGateway.saveStorageData(StorageEnum.MEETING);
    }

    //rejectMeeting also used to cancel ongoing meetings


    //Methods related to interacting with completed meetings


    /**
     * Gets all meetings the user has participated in that are completed. There is nothing to interact with, this is
     * just to view meeting history
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getCompletedMeetings() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getCompletedMeetings(username));
    }
}
