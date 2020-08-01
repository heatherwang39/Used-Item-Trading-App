package main.java.item;

import java.util.*;

/**
 * Use case class for storing items, creating new concrete items like book or clothing, verifying items
 * and get all the verified items in the program.
 * @author Heather Wang, Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */


public class ItemStorage {

    private final Map<Integer, Item> items;

    /**
     * Class constructor
     *
     * @param items Map containing Items referenced by their IDs.
     */
    public ItemStorage(Map<Integer, Item> items) { this.items = items; }

    /**
     * Gets an item from ID.
     *
     * @param itemID item ID
     * @return item
     * @throws ItemNotFoundException if no item exists with that ID.
     */
    public Item getItem(int itemID) throws ItemNotFoundException {
        Item item = items.get(itemID);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        return item;
    }

    /**
     * Gets the total number of items added
     *
     * @return the size of items
     */
    public int generateNextID() {
        return items.size();
    }


    /**
     * Initializes a new Item based on the given parameters. Adds the Item to items.
     *
     * @param name        The Item's name
     * @param description The Item's description
     * @return Item
     */
    public Item newItem(String owner, String name, String description, List<String> tags) {
        int id = generateNextID();
        Item item = new Item(id, owner, name, description, tags);
        items.put(generateNextID(), item);
        return item;
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

    /**
     * Creates a HashMap containing the Item's data
     * @param item the item
     * @return Data in the form of {Label: Information, ...}
     */
    public HashMap<String, String> getData(Item item) {
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

}
