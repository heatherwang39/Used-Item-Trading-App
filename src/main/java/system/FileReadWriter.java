package main.java.system;
import java.io.*;
import java.util.HashMap;

/**
 * A gateway to read object from file and save object to file.
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class FileReadWriter {

    final String filePath;

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
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(obj);
        output.close();
    }

    /**
     * Read object from the .ser file
     *
     * @return object
     * @throws ClassNotFoundException if serialized class doesn't exist
     * @throws IOException cannot read from file
     */
    public Object readFromFile() throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        Object obj;
        obj = input.readObject();
        input.close();
        return obj;
    }

    /**
     * Read the content as map from the .txt file
     * read txt file to hashmap: https://stackoverflow.com/questions/29061782/java-read-txt-file-to-hashmap-split-by
     *
     * @return a hashmap based on the content of txt file
     * @throws IOException cannot read from file
     */
    public HashMap<String, String> readAsMapFromTextFile() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null)
        {
            String[] text = line.split(":", 2);
            String key = text[0];
            String value = text[1];
            map.put(key, value);
        }
        br.close();
        return map;
    }
}
