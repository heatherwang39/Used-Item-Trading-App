package main.java.model.item;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.util.*;

/**
 * Use case class for storing items, creating new concrete items like book or clothing, verifying items
 * and get all the verified items in the program.
 * @author Heather Wang, Robbert Liu, Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */


public class ItemStorage implements Storage, TradeObserver {

    private Map<Integer, Item> items;

    /**
     * Generates a new structure matching the Storage's internal data
     * @return Empty storage data
     */
    @Override
    public Object getNewStorageData() {
        return new HashMap<Integer, Item>();
    }

    /**
     * This sets the Storage's data
     * @param items items data
     */
    @Override
    public void setStorageData(Object items) {
        this.items = (Map<Integer, Item>) items;
    }

    private Item getItem(int itemID) throws ItemNotFoundException {
        Item item = items.get(itemID);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        return item;
    }

    private int generateNextID() {
        return items.size();
    }


    /**
     * Creates a new item and adds it to the system
     *
     * @param owner username of who added the item
     * @param name name of the item
     * @param description description of the item
     * @param tags any user-added search tags of the item
     */
    public void newItem(String owner, String name, String description, List<String> tags) {
        int id = generateNextID();
        Item item = new Item(id, owner, name, description, tags);
        items.put(generateNextID(), item);
    }

    /**
     * Removes an instance of Item from the overall list of Items.
     *
     * @param itemId The id of the Item object that needs to be removed
     * @throws ItemNotFoundException item not in system
     */
    public void removeItem(int itemId) throws ItemNotFoundException {
        if (items.containsKey(itemId)) {
            items.remove(itemId);
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Adds username to wishlist of Item with given itemId
     *
     * @param username Account username
     * @param itemId The id of the Item object that needs to add a user to its wishlist
     * @throws ItemNotFoundException item not in system
     */
    public void addWishList(String username, int itemId) throws ItemNotFoundException {
        if (items.containsKey(itemId)) {
            items.get(itemId).addWishlist(username);
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Removes username from wishlist of Item with given itemId
     *
     * @param username Account username
     * @param itemId The id of the Item object that needs to remove a user from its wishlist
     * @throws ItemNotFoundException item not in system
     */
    public void removeWishList(String username, int itemId) throws ItemNotFoundException, NotInWishlistException {
        if (items.containsKey(itemId)) {
            items.get(itemId).removeWishlist(username);
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Verify an Item.
     *
     * @param itemId The id of the Item object that needs to be verified.
     *
     * @throws ItemNotFoundException item not in system
     */
    public void verifyItem(int itemId) throws ItemNotFoundException {
        if (items.containsKey(itemId)) {
            items.get(itemId).verify();
        } else {
            throw new ItemNotFoundException();
        }
    }

    private List<Item> getUnverifiedInventory(String username) {
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (!item.isVerified() && item.getOwner().equals(username)) {
                items.add(item);
            }
        }
        return items;
    }

    private List<Item> getVerifiedInventory(List<String> searchTerms, List<String> tags) {
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            for (String term: searchTerms) {
                if ((item.getName().contains(term) || item.getDescription().contains(term))
                        && !Collections.disjoint(tags, item.getTags())) {
                    items.add(item);
                    break;
                }
            }
        }
        return items;
    }

    /**
     * Get all the data of verified Items in the overall list of Items
     *
     * @return all data of verified Items
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getVerifiedItemsData() throws ItemNotFoundException {
        List<HashMap<String, String>> itemData = new ArrayList<>();
        // From here:
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified()) {
                itemData.add(getData(item.getID()));
            }
        }
        return itemData;
    }

    /**
     * Get all unverified Items in the overall list of items.
     *
     * @return all unverified Items
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getUnverifiedItemsData() throws ItemNotFoundException {
        List<HashMap<String, String>> itemData = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (!item.isVerified()) {
                itemData.add(getData(item.getID()));
            }
        }
        return itemData;
    }

    /**
     * Get all an Account's verified items by username.
     *
     * @param username Account username
     * @return verified inventory
     */
    public List<Item> getVerifiedInventory(String username) {
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified() && item.getOwner().equals(username)) {
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Get an Account's wishlist by username.
     *
     * @param username Account username
     * @return wishlist
     */
    public List<Item> getWishlist(String username) {
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified() && item.getWishlist().contains(username)) {
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Get an Account's inventory by username.
     *
     * @param username Account username
     * @return inventory
     */
    public List<String> getInventoryString(String username){
        List<String> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified() && item.getOwner().equals(username)) {
                items.add(item.getName());
            }
        }
        return items;
    }

    /**
     * Get an account's formatted wishlist string
     *
     * @param username Account username
     * @return formatted wishlist
     */
    public List<String> getWishlistString(String username){
        List<String> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified() && item.getWishlist().contains(username)) {
                items.add(item.getName());
            }
        }
        return items;
    }

    /**
     * Suggest items to lend to a given user
     *
     * @param currentUser current Account username who needs suggestion
     * @param givenUser the given Account username who may want to borrow
     * @throws ItemNotFoundException item not in system
     * @return data of suggested items
     */
    public List<HashMap<String, String>> suggestItems(String currentUser, String givenUser) throws ItemNotFoundException {
        List<Item> inventory = getVerifiedInventory(currentUser);
        List<Item> wishlist = getWishlist(givenUser);
        List<Item> suggestedItems = new ArrayList<>(inventory);
        List<HashMap<String, String>> suggestItemsData = new ArrayList<>();
        suggestedItems.retainAll(wishlist);
        for(Item i:suggestedItems){
            suggestItemsData.add(getData(i.getID()));
        }
        return suggestItemsData;
        // this gets the intersection of currentUser's inventory and givenUser's wishlist
    }


    /**
     * Returns a list with each element being the Item data of an Item that is in the given user's wishlist
     *
     * @param username Account username
     * @return data of items in user's wishlist
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getWishlistData(String username) throws ItemNotFoundException {
        List<HashMap<String, String>> wishlistData = new ArrayList<>();
        List<Item> wishlist = getWishlist(username);
        for(Item i : wishlist){
            wishlistData.add(getData(i.getID()));
        }
        return wishlistData;
    }

    /**
     * Returns a list with each element being the Item data of a verified Item that is in the given user's inventory
     *
     * @param username Account username
     * @return data of verified items in user's inventory
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(String username) throws ItemNotFoundException {
        List<HashMap<String, String>> verifiedInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(username);
        for(Item i : verifiedInventory){
            verifiedInventoryData.add(getData(i.getID()));
        }
        return verifiedInventoryData;
    }

    /**
     * Returns a list with each element being the Item data of a verified Item that matches the search filter
     *
     * @param searchTerms keywords searched by the user
     * @param tags enabled item tags
     * @return data of verified items that match the search filter
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(List<String> searchTerms, List<String> tags) throws ItemNotFoundException {
        List<HashMap<String, String>> verifiedInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(searchTerms,tags);
        for(Item i : verifiedInventory){
            verifiedInventoryData.add(getData(i.getID()));
        }
        return verifiedInventoryData;
    }

    /**
     * Creates a HashMap containing the Item's data
     * @param itemID The id of the Item
     * @return Data in the form of {Label: Information, ...}
     * @throws ItemNotFoundException item not in system
     */
    public HashMap<String, String> getData(Integer itemID) throws ItemNotFoundException {
        HashMap<String, String> data = new HashMap<>();
        Item item = getItem(itemID);
        data.put("id", String.valueOf(item.getID()));
        data.put("owner", item.getOwner());
        data.put("name", item.getName());
        data.put("description", item.getDescription());
        data.put("tags", String.join(", ", item.getTags()));
        data.put("wishlist", String.join(", ", item.getWishlist()));
        data.put("verified", String.valueOf(item.isVerified()));
        data.put("hidden", String.valueOf(item.isHidden()));
        return data;
    }

    /**
     * show names of the given items
     * @param ids the ids of the given items
     * @return List of names of the given items
     * @throws ItemNotFoundException item not in system
     */
    public List<String> showNames(List<Integer> ids) throws ItemNotFoundException {
        List<String> names = new ArrayList<>();
        for(int id:ids){
            String name = getItem(id).getName();
            names.add(name);
        }
        return names;
    }

    /**
     * Change owner of given item
     * @param itemId the id of the given item
     * @param username the username of new owner
     * @throws ItemNotFoundException item not in system
     */
    public void changeOwner(int itemId, String username) throws ItemNotFoundException {
        getItem(itemId).setOwner(username);
    }

    /**
     * Hides an Item.
     *
     * @param itemId The id of the Item object that needs to be hidden.
     *
     * @throws ItemNotFoundException item not in system
     * @throws AlreadyHiddenException item already hidden
     */
    public void hideItem(int itemId) throws ItemNotFoundException, AlreadyHiddenException {
        if (items.containsKey(itemId)) {
            if (!items.get(itemId).hide()) throw new AlreadyHiddenException();
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Unhides an Item.
     *
     * @param itemId The id of the Item object that needs to be hidden.
     *
     * @throws ItemNotFoundException item not in system
     * @throws AlreadyNotHiddenException item already not hidden
     * @throws ItemInTradeException item is in an active trade and cannot be unhidden
     */
    public void unhideItem(int itemId, int activeTrade) throws ItemNotFoundException, AlreadyNotHiddenException,
            ItemInTradeException {
        if (activeTrade != 0) throw new ItemInTradeException();
        if (items.containsKey(itemId)) {
            if (!items.get(itemId).unhide()) throw new AlreadyNotHiddenException();
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Returns a list with each element being the Item data of a verified, hidden Item in the given user's inventory
     *
     * @param username Account username
     * @return data of verified, hidden items in user's inventory
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getHiddenInventoryData(String username) throws ItemNotFoundException {
        List<HashMap<String, String>> hiddenInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(username);
        for(Item i : verifiedInventory){
            if (i.isHidden()) hiddenInventoryData.add(getData(i.getID()));
        }
        return hiddenInventoryData;
    }

    /**
     * Returns a list of ItemIDs of verified, not hidden Item in the given user's inventory
     *
     * @param username The username of the account
     * @return Item IDs of verified, not hidden items in user's inventory
     */
    public List<Integer> getUnhiddenInventory(String username){
        List<Integer> itemIDs = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(username);
        for(Item i : verifiedInventory){
            if (!i.isHidden()) itemIDs.add(i.getID());
        }
        return itemIDs;
    }


    /**
     * Returns a list with each element being the Item data of a verified, not hidden Item in the given user's inventory
     *
     * @param username Account username
     * @return data of verified, not hidden items in user's inventory
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getUnhiddenInventoryData(String username) throws ItemNotFoundException {
        List<HashMap<String, String>> unhiddenInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(username);
        for(Item i : verifiedInventory){
            if (!i.isHidden()) unhiddenInventoryData.add(getData(i.getID()));
        }
        return unhiddenInventoryData;
    }

    /**
     * Get all the data of verified, not hidden Items in the overall list of Items
     *
     * @return all data of verified, not hidden Items
     * @throws ItemNotFoundException item not in system
     */
    public List<HashMap<String, String>> getBrowsableItemsData() throws ItemNotFoundException {
        List<HashMap<String, String>> itemData = new ArrayList<>();
        // From here:
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified() && !item.isHidden()) {
                itemData.add(getData(item.getID()));
            }
        }
        return itemData;
    }

    //Item and Trade Observer Pattern below


    /** Record the fact that a Trade's status has changed
     *
     * @param exchangeData A HashMap representing the Exchange Data of the Trade
     * @param newStatus The new Status of the Trade
     */
    public void updateTradeChange(HashMap<String, HashMap<String, Integer>> exchangeData, int newStatus){
        try{

            Integer itemID;
            Item item;

            if(newStatus == 3){
                for(String user : exchangeData.keySet()){
                    itemID = exchangeData.get(user).get("FINAL");
                    if(!(itemID == null)){
                        getItem(itemID).setOwner(user);
                        getItem(itemID).hide();
                        removeWishList(user, itemID);
                    }
                }
            }

            else if(newStatus == -1 | newStatus == 0){
                for(String user : exchangeData.keySet()){
                    itemID = exchangeData.get(user).get("SENT");
                    if(!(itemID == null)){
                        item = getItem(itemID);
                        item.setOwner(user);
                        item.hide();
                    }
                }
            }

            else if(newStatus == 1 | newStatus == 2){
                for(String user : exchangeData.keySet()){
                    itemID = exchangeData.get(user).get("RECEIVED");
                    if(!(itemID == null)){
                        item = getItem(itemID);
                        item.setOwner(user);
                        item.hide();
                    }
                }
            }

        }catch(ItemNotFoundException | NotInWishlistException ignored){}
    }
}
