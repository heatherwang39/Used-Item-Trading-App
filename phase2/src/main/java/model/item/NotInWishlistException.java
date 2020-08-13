package main.java.model.item;

/**
 * AccountNotFoundException is thrown when no matching Account has an Item wishlisted.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */

public class NotInWishlistException extends Exception {

    /**
     * Class Constructor
     */
    public NotInWishlistException() {
        super("The Account has not wishlisted this item.");
    }

}
