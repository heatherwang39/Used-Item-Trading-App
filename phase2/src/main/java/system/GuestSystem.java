package main.java.system;

import main.java.item.ItemStorage;

/**
 * Presenter that returns the needed information for GUI client to display for an account that can browse listings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class GuestSystem implements Browsable {

    protected ItemStorage is;

    public GuestSystem(ItemStorage is) {this.is = is;}

    @Override
    public void browseListings() {
        //Unsure if entity display is still being used/reworked to be used here or not
    }
}
