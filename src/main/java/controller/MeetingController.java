package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.meeting.*;
import main.java.model.trade.MaxNumMeetingsExceededException;
import main.java.model.trade.TradeCancelledException;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.presenter.MeetingPresenter;
import main.java.presenter.TradePresenter;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

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
    private final MeetingStorage meetingStorage;
    private final String username;
    private final MeetingPresenter meetingPresenter;
    private final TradePresenter tradePresenter;

    /**
     * Initializes a new MeetingController for the given username
     *
     * @param storageDepot gateway for loading and saving information
     * @param username username of the user accessing the Request tab
     */
    public MeetingController(StorageDepot storageDepot, String username) throws IOException, ClassNotFoundException{
        storageGateway = storageDepot.getStorageGateway();
        this.username = username;
        tradeStorage = storageDepot.getTradeStorage();
        meetingStorage = storageDepot.getMeetingStorage();
        meetingPresenter = new MeetingPresenter(username);
        tradePresenter = new TradePresenter(storageDepot);
    }


    //Methods related to creating meetings from accepted trade requests

    /**
     * Gets all accepted trades involving user that have not set a meeting yet
     *
     * @return List of hashmaps that contain trade data
     * @throws TradeNumberException invalid trade id found in system
     */
    public List<HashMap<String, List<String>>> getAcceptedTradesUnformatted() throws TradeNumberException {
        return tradeStorage.getTradesData(tradeStorage.getAcceptedTradesWithUser(username));
    }

    /**
     * Gets all formatted accepted trades involving user that have not set a meeting yet
     *
     * @return List of strings that contain formatted trade data
     * @throws TradeNumberException invalid trade id found in system
     * @throws ItemNotFoundException invalid item id found in system
     */
    public List<String> getAcceptedTrades() throws TradeNumberException, ItemNotFoundException {
        return tradePresenter.formatTradeForListView(getAcceptedTradesUnformatted(), "MEETING");
    }

    /**
     * Gets all trades involving user that have a completed meeting but require another meeting to be fully finished
     *
     * @return List of hashmaps that contain trade data
     * @throws TradeNumberException invalid trade id found in system
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getUnfinishedTradesUnformatted() throws TradeNumberException,
            MeetingIDException {
        List<Integer> displayTrades = new ArrayList<>();
        List<Integer> unfinishedTrades = tradeStorage.getUnfinishedTradesWithUser(username);
        for (Integer tradeNumber : unfinishedTrades) {
            List<Integer> meetings = tradeStorage.getMeetings(tradeNumber);
            int meetingID = meetings.get(meetings.size() - 1);
            if (meetingStorage.getMeetingData(meetingID).get("unconfirmed").size() == 0) displayTrades.add(tradeNumber);
        }
        return tradeStorage.getTradesData(displayTrades);
    }

    /**
     * Gets all formatted trades involving user that have a completed meeting but need another meeting to fully finish
     *
     * @return List of strings that contain formatted trade data
     * @throws TradeNumberException invalid trade id found in system
     * @throws ItemNotFoundException invalid item id found in system
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<String> getUnfinishedTrades() throws TradeNumberException, ItemNotFoundException, MeetingIDException {
        return tradePresenter.formatTradeForListView(getUnfinishedTradesUnformatted(), "MEETING");
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
            IOException, TradeCancelledException, MaxNumMeetingsExceededException, WrongMeetingAccountException, MeetingIDException {
        List<String> attendees = tradeStorage.getTraders(tradeNumber);
        int meetingID = meetingStorage.newMeeting(attendees, place, time);
        tradeStorage.addMeeting(tradeNumber, meetingID);
        meetingStorage.acceptMeeting(meetingID, username);
        storageGateway.saveStorageData();
    }


    //Methods related to interacting with suggested meetings

    /**
     * Gets all meeting suggestions the user has not replied to yet
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getSuggestedMeetingsUnformatted() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getSuggestedMeetings(username));
    }

    /**
     * Gets all formatted meeting suggestions the user has not replied to yet
     *
     * @return List of strings that contain formatted meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<String> getSuggestedMeetings() throws MeetingIDException {
        return meetingPresenter.formatMeetingToListView(getSuggestedMeetingsUnformatted());
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
        storageGateway.saveStorageData();
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
        storageGateway.saveStorageData();
    }


    //Methods related to interacting with ongoing meetings

    /**
     * Gets all meetings the user is participating in that are currently ongoing
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getOngoingMeetingsUnformatted() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getOngoingMeetings(username));
    }

    /**
     * Gets all meetings the user is participating in that are currently ongoing formatted
     *
     * @return List of strings that contain formatted meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<String> getOngoingMeetings() throws MeetingIDException{
        return meetingPresenter.formatMeetingToListView(getOngoingMeetingsUnformatted());
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
        storageGateway.saveStorageData();
    }


    //Methods related to interacting with completed meetings

    /**
     * Gets all meetings the user has participated in that are completed. There is nothing to interact with, this is
     * just to view meeting history
     *
     * @return List of hashmaps that contain meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<HashMap<String, List<String>>> getCompletedMeetingsUnformatted() throws MeetingIDException {
        return meetingStorage.getMeetingsData(meetingStorage.getCompletedMeetings(username));
    }

    /**
     * Gets all meetings the user has participated in that are completed. There is nothing to interact with, this is
     * just to view meeting history formatted
     *
     * @return List of strings that contain formatted meeting data
     * @throws MeetingIDException invalid meeting id found in system
     */
    public List<String> getCompletedMeetings() throws MeetingIDException {
        return meetingPresenter.formatMeetingToListView(getCompletedMeetingsUnformatted());
    }
}
