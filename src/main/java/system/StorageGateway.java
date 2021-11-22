package main.java.system;

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
    protected StorageGateway(String resourcePath, String filenameConfig) throws IOException {
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
     * @throws ClassNotFoundException Serialized class not found
     */
    public Object getStorageData(StorageEnum type, Object newData) throws IOException, ClassNotFoundException {
        String filePath = path + filenameMap.get(type.toString());
        File file = new File(filePath);
        FileReadWriter fileReadWriter = new FileReadWriter(filePath);
        if (dataMap.containsKey(type.toString())) {
            return dataMap.get(type.toString());
        }
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            assert newFile;
            dataMap.put(type.toString(), newData);
            return newData;
        }
        Object data = null;
        try {
            data = fileReadWriter.readFromFile();
        } catch (EOFException ignored) {}
        if (data == null) {
            dataMap.put(type.toString(), newData);
            return newData;
        }
        dataMap.put(type.toString(), data);
        return data;
    }

    /**
     * Save Storage data to file.
     *
     * @throws IOException if an error occurs during writing to file
     */
    public void saveStorageData() throws IOException {
        for(StorageEnum type: StorageEnum.values()){
            saveStorageData(type);
        }
    }


    /**
     * Save Storage data to file by type.
     *
     * @param type Storage type
     * @throws IOException if an error occurs during writing to file
     */
    public void saveStorageData(StorageEnum type) throws IOException {
        FileReadWriter fileReadWriter = new FileReadWriter(path + filenameMap.get(type.toString()));
        Object data = dataMap.get(type.toString());
        fileReadWriter.saveToFile(data);
    }


    /**
     * Gets file path
     *
     * @return file path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets filename map
     *
     * @return filenames
     */
    public Map<String, String> getFilenameMap() {
        return filenameMap;
    }

}

