package main.java.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Presenter for formatting item data into an easy to output GUI format
 * Useful for Requests tabs
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class ItemPresenter {

    /**
     * Initializes a new ItemPresenter
     */
    public ItemPresenter() {}

    /**
     * Formats a list of hashmaps representing data of items for GUI Lists to display
     * Format of item data: "itemID. itemName, Owner: ownerName, Description: description, Tags: tag1, tag2, ..."
     * Example: "15. Book, Owner: Fadi, Description: for reading, Tags: hardcover, fiction, comedy"
     *
     * @param items List of hashmaps that contain item data
     * @return List of formatted Strings of each item data
     */
    public List<String> formatItemsToListView(List<HashMap<String, String>> items) {
        List<String> formatItem = new ArrayList<>();
        for (HashMap<String, String> itemData : items) {
            String itemInfo = itemData.get("id") + ". " + itemData.get("name") + ", Owner: " +
                    itemData.get("owner") +
                    "\nDescription: " +
                    itemData.get("description") +
                    "\nTags: " +
                    itemData.get("tags") +
                    "\n";
            formatItem.add(itemInfo);
        }
        return formatItem;
    }
}
