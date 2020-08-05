package main.java.system2;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * A Gateway class for reading and writing Storage data
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StorageGateway {

    final String path;
    Map<String, String> filenameMap;
    Map<String, Object> dataMap;

    /**
     * Class Constructor.
     * Reading a config file: https://stackoverflow.com/questions/16273174/how-to-read-a-configuration-file-in-java
     *
     * @param resourcePath   path of the resources folder
     * @param filenameConfig filenames corresponding to each Storage
     * @throws IOException occurs during config read/load
     */
    public StorageGateway(String resourcePath, String filenameConfig) throws IOException {
        path = resourcePath;
        Properties storages = new Properties();
        storages.load(new FileInputStream(path + filenameConfig));
        filenameMap = (Map) storages;
        dataMap = new HashMap<>();
    }

    /** Returns Storage data from file
     *
     * @param type Represents type of Storage
     * @return new Storage class
     * @throws IOException error during reading data
     * @throws ClassNotFoundException Serialized clas not found
     */
    public Object getStorageData(StorageEnum type) throws IOException, ClassNotFoundException {
        String filePath = path + filenameMap.get(type.toString());
        File file = new File(filePath);
        FileReadWriter fileReadWriter = new FileReadWriter(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        if (dataMap.get(type.toString()) == null) {
            try {
                dataMap.put(type.toString(), fileReadWriter.readFromFile());
            } catch (EOFException ignored) {
            }
        }
        return dataMap.get(type.toString());
    }

    /**
     * Save Storage data to file by type.
     *
     * @param type Storage type
     * @throws IOException                 if an error occurs during writing to file
     */
    public void saveStorageData(StorageEnum type) throws IOException {
        new FileReadWriter(path + filenameMap.get(type.toString())).saveToFile(dataMap.get(type.toString()));
    }
}

