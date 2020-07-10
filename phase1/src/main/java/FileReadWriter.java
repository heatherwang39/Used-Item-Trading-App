package main.java;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReadWriter {

    private String filepath;
    //private Object obj;

    public FileReadWriter(String filePath) {
        this.filepath = filePath;
    }

    public void saveToFile(Object obj,String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(obj);
        output.close();
    }

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
