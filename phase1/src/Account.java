import java.util.ArrayList;
import java.util.List;

/**
 * RAn abstract account in the Trade system representing a single user or admin of the program. All accounts
 * stores the username, password, and email, along with the account's inventory and wishlist.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Account {

    private final String username;
    private final String password;
    private final String email;
    private final List<Item> wishlist;
    private final List<Item> inventory;


    /**
     * Class constructor
     * @param username account username
     * @param password account password
     * @param email account email address
     */
    public Account(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        inventory = new ArrayList<>();
        wishlist = new ArrayList<>();
    }

    /**
     * Check the privilege level of the account
     * @return if the account is an admin
     */
    abstract boolean isAdmin();

    /**
     * Verify the password's correctness
     * @param password input password
     * @return if input password matches stored password
     */
    public boolean isPassword(String password){
        return password.equals(this.password);
    }

    /**
     * Get the account username
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Get the account email
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Get a shallow copy of account inventory
     * @return inventory
     */
    public List<Item> getInventory() { return new ArrayList<>(inventory); }

    /**
     * Get a shallow copy of account wishlist
     * @return wishlist
     */
    public List<Item> getWishlist() { return new ArrayList<>(wishlist); }

    /**
     * Add an item to account inventory
     * @param item item to be added
     */
    public void addInventory(Item item) { inventory.add(item); }

    /**
     * Add an item to account wishlist
     * @param item item to be added
     */
    public void addWishlist(Item item) { wishlist.add(item); }

    /**
     * Remove an item from account inventory
     * @param id item id
     */
    public void removeInventory(int id) {
        boolean found = false;
        for (int i = 0; i < inventory.size() && !found ; i ++) {
            if (inventory.get(i).getID() == id) {
                found = true;
                inventory.remove(i);
            }
        }
        if (!found) throw new ItemNotFoundException();
    }

    /**
     * Remove an item from account wishlist
     * @param id item id
     */
    public void removeWishlist(int id) {
        boolean found = false;
        for (int i = 0; i < wishlist.size() && !found; i++) {
            if (wishlist.get(i).getID() == id) {
                found = true;
                wishlist.remove(i);
            }
        }
        if (!found) throw new ItemNotFoundException();
    }

    /**
     * Retrieve an item from account inventory
     * @param id  item id
     */
    public Item getFromInventory (int id) {
        for (Item i : inventory) {
            if (i.getID() == id) return i;
        }
        throw new ItemNotFoundException();
    }

    /**
     * Retrieve an item from account wishlist
     * @param id  item id
     */
    public Item getFromWishlist (int id) {
        for (Item i : wishlist) {
            if (i.getID() == id) return i;
        }
        throw new ItemNotFoundException();
    }
}
