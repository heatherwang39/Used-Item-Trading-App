/**
 * Exception to be thrown when an item could not be found in any List<Item>
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Class constructor
     */
    public ItemNotFoundException() {
        super("Item was not found in container.");
    }
}
