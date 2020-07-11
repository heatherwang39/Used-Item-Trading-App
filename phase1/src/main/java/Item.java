package main.java;

import java.io.Serializable;

public abstract class Item implements Serializable, Entity {
    private String name;
    private String description;
    private int id;
    private boolean isVerified = false;
    private int currentStatus;
    private int timeLimit = 30;

    /**
     * Creates an Item with the name and description of Item
     */
    public Item(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    /**
     * Get name of the item
     *
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Get description of the item
     *
     * @return description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the item's unique id
     * @return item id
     */
    public int getID() {
        return id;
    }

    /**
     * Get whether this item can be added to system
     *
     * @return true if the administrative user has looked at it and confirmed that it can be added to system, false if not
     */
    public boolean getIsVerified() {
        return isVerified;
    }

    /**
     * Set whether the item can be added to system
     *
     * @param isVerified whether the item has been verified
     */
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * Return the current status of the item
     * 0 represents that the item is available
     * 1 represents that the item is in the process of trading
     * 2 represents that the item has been borrowed by somebody
     *
     * @return the current status of the item
     */
    public int getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Set the current status of the item
     *
     * @param currentStatus the current status of this item
     */
    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

}