package main.java.system2;

import java.io.IOException;

public class TraderMain {

    static final String RESOURCE_PATH = "phase2/src/main/resources/";
    static final String FILENAME_CONFIG = "filenames.config";

    public static void main(String[] args) {
        try {
            StorageFactory storageFactory = new StorageFactory(RESOURCE_PATH, FILENAME_CONFIG);
        } catch (IOException | InvalidStorageTypeException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    } 
}
