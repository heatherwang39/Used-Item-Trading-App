package main.java.systems;

/**
 * An Interface for the actions of an account that is able to browse listings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Browsable {

    //I'm tempted to just merge this into Account, but to be honest, IDK if frozen accounts can even browse listings


    /**
     * Displays every available item listing
     */
    void browseListings();
}