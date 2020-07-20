package main.java.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Item is a class representing an item added by a UserAccount to their inventory. Items can be traded, removed from
 *  its owner's inventory, and wishlisted.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */
class Item implements Serializable {

    private final int id;
    private final String owner;
    private final String name;
    private final String description;
    private final List<String> tags;
    private boolean isVerified = false;

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
    }

    /**
     * Get the item's unique ID.
     * @return ID
     */
    public int getID() {
        return id;
    }/**

     * Get the item's owner's username.
     *
     * @return name
     */
    public String getOwner() {
        return owner;
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
     * Get item's tags.
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
     * Verify this item
     */
    public void verify() {
        isVerified = true;
    }
}