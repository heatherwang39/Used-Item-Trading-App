package main.java.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * Get all verified Items in the overall list of Items
     *
     * @return all verified Items
     */
    public List<Item> getVerifiedItems() {
        List<Item> verifiedItems = new ArrayList<>();
        // From here:
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            if (item.isVerified()) {
                verifiedItems.add(item);
            }
        }
        return verifiedItems;
    }

    /**
     * Get all unverified Items in the overall list of items.
     *
     * @return all unverified Items
     */
    public List<Item> getUnverifiedItems() {
        List<Item> unverifiedItems = new ArrayList<>();
        // From here:
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            if (!item.isVerified()) {
                unverifiedItems.add(item);
            }
        }
        return unverifiedItems;
    }

    public 
}
