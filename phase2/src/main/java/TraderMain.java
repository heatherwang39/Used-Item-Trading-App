package main.java;

import main.java.system.TraderSystem;

/**
 * The main class and the main method
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class TraderMain {

    /**
     * Creates an TraderClient instance and calls its run method which boots up the program.
     */
    public static void main(String[] args) {

        TraderSystem ts = new TraderSystem();
        ts.run();

    }
}
