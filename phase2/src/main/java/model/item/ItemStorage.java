package main.java.model.item;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.util.*;

/**
 * Use case class for storing items, creating new concrete items like book or clothing, verifying items
 * and get all the verified items in the program.
 * @author Heather Wang, Robbert Liu, Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */


public class ItemStorage implements Storage, TradeObserver {

    private final Map<Integer, Item> items;

    /**
     * Class constructor
     *
     * @param items Map containing Items referenced by their IDs.
     */
    public ItemStorage(Object items) { this.items = (Map<Integer, Item>) items; }


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


    /**
     * Get all the data of verified Items in the overall list of Items
     *
     * @return all data of verified Items
     */
    public List<HashMap<String, String>> getVerifiedItemsData(){
        List<HashMap<String, String>> itemData = new ArrayList<>();
        // From here:
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified()) {
                itemData.add(getData(item));
            }
        }
        return itemData;
    }

    /**
     * Get all unverified Items in the overall list of items.
     *
     * @return all unverified Items
     */
    public List<HashMap<String, String>> getUnverifiedItemsData(){
        List<HashMap<String, String>> itemData = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            if (!item.isVerified()) {
                itemData.add(getData(item));
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
    private List<Item> getVerifiedInventory(String username) {
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
     * Get all an Account's unverified items by username.
     *
     * @param username Account username
     * @return unverified inventory
     */
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

    /**
     * Get all items that match a search filter
     *
     * @param searchTerms keywords searched by the user
     * @param tags enabled item tags
     * @return items that match the search filter
     */
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
     * Get an Account's wishlist by username.
     *
     * @param username Account username
     * @return wishlist
     */
    private List<Item> getWishlist(String username) {
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
     * Suggest items to lend to a given user.
     *
     * @param currentUser current Account username who needs suggestion
     * @param givenUser the given Account username who may want to borrow
     * @return data of suggested items
     */
    public List<HashMap<String, String>> suggestItems(String currentUser,String givenUser) {

        List<Item> inventory = getVerifiedInventory(currentUser);
        List<Item> wishlist = getWishlist(givenUser);
        List<Item> suggestedItems = new ArrayList<>(inventory);
        List<HashMap<String, String>> suggestItemsData = new ArrayList<>();
        suggestedItems.retainAll(wishlist);
        for(Item i:suggestedItems){
            suggestItemsData.add(getData(i));
        }
        return suggestItemsData;
    }


    public List<HashMap<String, String>> getWishlistData(String username) {
        List<HashMap<String, String>> wishlistData = new ArrayList<>();
        List<Item> wishlist = getWishlist(username);
        for(Item i : wishlist){
            wishlistData.add(getData(i));
        }
        return wishlistData;
    }

    public List<HashMap<String, String>> getVerifiedInventoryData(String username){
        List<HashMap<String, String>> verifiedInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(username);
        for(Item i : verifiedInventory){
            verifiedInventoryData.add(getData(i));
        }
        return verifiedInventoryData;
    }

    public List<HashMap<String, String>> getVerifiedInventoryData(List<String> searchTerms, List<String> tags) {
        List<HashMap<String, String>> verifiedInventoryData = new ArrayList<>();
        List<Item> verifiedInventory = getVerifiedInventory(searchTerms,tags);
        for(Item i : verifiedInventory){
            verifiedInventoryData.add(getData(i));
        }
        return verifiedInventoryData;

    }

    /**
     * Creates a HashMap containing the Item's data
     * @param item the item
     * @return Data in the form of {Label: Information, ...}
     */
    private HashMap<String, String> getData(Item item) {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(item.getID()));
        data.put("owner", item.getOwner());
        data.put("name", item.getName());
        data.put("description", item.getDescription());
        data.put("tags", String.join(",", item.getTags()));
        data.put("wishlist", String.join(",", item.getWishlist()));
        data.put("verified", String.valueOf(item.isVerified()));
        return data;
    }

    public List<String> showNames(List<Integer> ids) throws ItemNotFoundException {
        List<String> names = new ArrayList<>();
        for(int id:ids){
            String name = getItem(id).getName();
            names.add(name);
        }
        return names;
    }


    //Item and Trade Observer Pattern below


    /** Record the fact that a Trade with the items distributed between owners as stored in the two parallel lists
     * (inputs/parameters) has been completed.
     *
     * @param itemIDs A parallel list representing the IDs of items involved in the trade
     * @param newOwner A parallel list representing the usernames of the new owners of the aforementioned items
     */
    public void updateTradeComplete(List<Integer> itemIDs, List<String> newOwner){
        int listsizes = itemIDs.size();
        int i = 0;
        while(i < listsizes){
            try{getItem(itemIDs.get(i)).setOwner(newOwner.get(i));}catch(ItemNotFoundException ignored){}
            i++;
        }
    }
}
