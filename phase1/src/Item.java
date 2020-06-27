abstract class Item{

    private String name;
    private String description;
    private static int numberOfItems;
    private int id;
    private boolean isVerified = false;
    private String usernameOfOwner;
    private int currentStatus;
    private int timeLimit = 30;

    public Item(String name, String description){
        this.name = name;
        this.description = description;
        numberOfItems++;
        this.id = numberOfItems;
    }

    /**
     * Get whether this item can be added to system
     * @return true if the administrative user has looked at it and confirmed that it can be added to system, false if not
     */
    public boolean getIsVerified() {
        return isVerified;
    }

    /**
     * Set whether the item can be added to system
     * @param isVerified whether the item has been verified
     */
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    /**
     *Return the current status of the item
     * 0 represents that the item is available
     * 1 represents that the item is in the process of trading
     * 2 represents that the item has been borrowed by somebody
     * @return the current status of the item
     */
    public int getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Set the current status of the item
     * @param currentStatus the current status of this item
     */
    public void setCurrentStatus(int currentStatus){
        this.currentStatus = currentStatus;
    }

    /**
     * Get the username of current owner
     * @return username of current owner
     */
    public String getUsernameOfOwner() {
        return usernameOfOwner;
    }

    /**
     * Set the username of current owner
     * @param usernameOfOwner username of the owner of this item
     */
    public void setUsernameOfOwner(String usernameOfOwner) {
        this.usernameOfOwner = usernameOfOwner;
    }


}