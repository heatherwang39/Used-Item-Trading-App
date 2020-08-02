package main.java;
import java.io.*;

/**
 * A gateway to read object from file and save object to file.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class FileReadWriter {

    /**
     * Creates a FileReadWriter with the path of file
     * @param filePath the path of file
     */
    public FileReadWriter(String filePath) {
    }

    /**
     * Save object in the .ser file with the given filepath
     * @param obj the object need to be saved
     * @param filePath the path of file
     * @throws IOException cannot save to file
     */
    public void saveToFile(Object obj, String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(obj);
        output.close();
    }

    /**
     * Read object from the .ser file with the given filepath
     * @param filePath the path of file
     * @return object
     * @throws ClassNotFoundException if serialized class doesn't exist
     * @throws IOException cannot read from file
     */
    public Object readFromFile(String filePath) throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        Object obj;
        obj = input.readObject();
        input.close();
        return obj;
    }

}
