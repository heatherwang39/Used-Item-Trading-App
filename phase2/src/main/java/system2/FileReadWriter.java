package main.java.system2;
import java.io.*;

/**
 * A gateway to read object from file and save object to file.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class FileReadWriter {

    String filePath;

    /**
     * Creates a FileReadWriter with the path of file
     * @param filePath the path of file
     */
    public FileReadWriter(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Save object in the .ser file
     *
     * @param obj the object need to be saved
     * @throws IOException cannot save to file
     */
    public void saveToFile(Object obj) throws IOException {
        //https://www.tutorialspoint.com/java/java_serialization.htm
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
        fileOut.close();
    }

    /**
     * Read object from the .ser file
     *
     * @return object
     * @throws ClassNotFoundException if serialized class doesn't exist
     * @throws IOException cannot read from file
     */
    public Object readFromFile() throws ClassNotFoundException, IOException {
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object object = in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
