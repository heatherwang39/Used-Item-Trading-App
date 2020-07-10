package main.java;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

public class FileTest {
    private static String items;
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        String temp = "this is a test";
        File file = new File("phase1/src/main/resources/serializeditems.ser");
        FileReadWriter frw = new FileReadWriter("phase1/src/main/resources/serializeditems.ser");
        frw.saveToFile(temp,"phase1/src/main/resources/serializeditems.ser");

        items = new String();

        if (file.exists()) {
            try {
                items = (String)frw.readFromFile("phase1/src/main/resources/serializeditems.ser");
                System.out.println("##########################\n"+items);
            } catch(EOFException e) {
                System.out.println("Cannot read from file");
            }
        } else {
            file.createNewFile();
        }
    }
}