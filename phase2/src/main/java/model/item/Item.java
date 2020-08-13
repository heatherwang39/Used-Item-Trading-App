package main.java.model.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Item is a class representing an item added by a UserAccount to their inventory. Items can be traded, removed from
 *  its owner's inventory, and wishlisted.
 *
 * @author Heather Wang, Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class Item implements Serializable {

    private final int id;
    private String owner;
    private final String name;
    private final String description;
    private final List<String> tags;
    private final List<String> wishlist;
    private boolean isVerified;
    private boolean isHidden;

    /**
     * Class constructor.
     *
     * @param name name of item
     * @param tags list of tags associated with the item
     */
    public Item(int id, String owner, String name, String description, List<String> tags) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.wishlist = new ArrayList<>();
        isVerified = false;
        isHidden = false;
    }

    /**
     * Get the item's unique ID.
     * @return ID
     */
    public int getID() {
        return id;
    }

     /**
     * Get the item's owner's username.
     *
     * @return name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the item's owner to be the given owner
     *
     * @param owner The item's new owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get item's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get item's description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get Item's tags.
     *
     * @return tags
     */
    public List<String> getTags() {return new ArrayList<>(tags); }

    /**
     * Get whether this item has been verified by an AdminAccount yet.
     *
     * @return true if it has been verified by an Admin, false if not
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * Verify this item.
     */
    public void verify() {
        isVerified = true;
    }

    /**
     * Get usernames of Accounts that have wishlisted this Item.
     *
     * @return usernames
     */
    public List<String> getWishlist() {return new ArrayList<>(wishlist); }

    /**
     * Add username to wishlist.
     *
     * @param username Account username
     */
    public void addWishlist(String username) { wishlist.add(username); }

    /**
     * Remove username from wishlist
     *
     * @param username Account username
     */
    public void removeWishlist(String username) throws NotInWishlistException {
        if (!wishlist.contains(username)) {
            throw new NotInWishlistException();
        }
        wishlist.remove(username);
    }

    /**
     * Get whether this item has been hidden by its owner
     *
     * @return true if it has been hidden by its owner, false if not
     */
    public boolean isHidden() { return isHidden; }

    /**
     * Hides this item
     *
     * @return true if successfully hidden, false if already hidden
     */
    public boolean hide() {
        if (isHidden) return false;
        else {
            isHidden = true;
            return true;
        }
    }

    /**
     * Unhides this item
     *
     * @return true if successfully unhidden, false if already not hidden
     */
    public boolean unhide() {
        if (!isHidden) return false;
        else {
            isHidden = false;
            return true;
        }
    }
}