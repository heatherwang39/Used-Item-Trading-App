package main.java.system;

import main.java.model.item.ItemNotFoundException;

import java.util.HashMap;
import java.util.List;

/**
 * An interface for the actions of a user account
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public interface UserInterface {

    /**
     * Returns a list with the data of an item for
     * @return
     */
    List<HashMap<String, String>> viewInventory() throws ItemNotFoundException;

    List<HashMap<String, String>> viewWishlist() throws ItemNotFoundException;
}
