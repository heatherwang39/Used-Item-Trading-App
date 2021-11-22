package main.java.system;

import main.java.gui.TraderGUI;

import javax.swing.*;
import java.io.IOException;


/**
 * The class representing the Trader system
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class TraderSystem {

    static final String RESOURCE_PATH = "phase2/src/main/resources/";
    static final String FILENAME_CONFIG = "filenames.config";

    /**
     * Used to run Trader system
     */
    public void run() {
        try {
            StorageGateway storageGateway = new StorageGateway(RESOURCE_PATH, FILENAME_CONFIG);
            StorageDepot storageDepot = new StorageDepot(storageGateway);
            JFrame frame = new JFrame("Trader Client");
            frame.setIconImage(new ImageIcon("phase2/src/main/resources/mainIcon.png").getImage());
            frame.setContentPane(new TraderGUI(storageDepot).MainContainer);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    } 
}
