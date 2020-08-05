package main.java.system2;

import main.java.gui.TraderGUI;

import javax.swing.*;
import java.io.IOException;

public class TraderMain {

    static final String RESOURCE_PATH = "phase2/src/main/resources/";
    static final String FILENAME_CONFIG = "filenames.config";

    public static void main(String[] args) {
        try {
            StorageGateway storageGateway = new StorageGateway(RESOURCE_PATH, FILENAME_CONFIG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize Controllers here

        JFrame frame = new JFrame("Trader Client");
        frame.setContentPane(new TraderGUI().MainContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    } 
}
