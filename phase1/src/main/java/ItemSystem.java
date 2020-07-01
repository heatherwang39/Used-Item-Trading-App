package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSystem {

    /** A mapping of item ids to item. */
    private Map<Integer, Item> items;

    /**
     * Creates an ItemManager with lists of Item that are empty
     *
     * @param filePath the path of the data file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ItemSystem(String filePath) throws IOException, ClassNotFoundException {
        items = new HashMap<Integer, Item>();
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }
    }

    /**
     * Adds the id and the instance of Item to the overall list of Items
     * @param item The Item object that needs to be added
     */
    public void addItem(Item item){
        items.put(item.getID(), item);
        System.out.println("added successfully");
    }

    /**
     * Removes an instance of Item from the overall list of Items
     * @param itemId The id of the Item object that needs to be removed
     * @throws ItemNotFoundException
     */
    public void removeItem(int itemId) throws ItemNotFoundException{
        items.remove(itemId);
        System.out.println("removed successfully");
    }

    /**
     * Verify an Item in the overall list of Items
     * @param itemId The id of the Item object that needs to be verified
     * @throws ItemNotFoundException
     */
    public void verifyItem(int itemId) throws ItemNotFoundException{
        items.get(itemId).setIsVerified(true);
        System.out.println("verified successfully");
    }


    /**
     * Get all verified Items in the overall list of Items
     * @return all verified Items
     */
    public List<Item> getVerifiedItems(){
        List<Item> verifiedItems = new ArrayList<Item>();;
        // Citation begin -->https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
        //Citation end
            Item item = entry.getValue();
            if(item.getIsVerified() == true){
                verifiedItems.add(item);
            }
        }
        return verifiedItems;
    }

    /**
     * Populates the items map from the file at path filePath.
     *
     * @param filePath the path of the data file
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void readFromFile(String filePath) throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        items = (Map<Integer, Item>) input.readObject();
        input.close();
    }

    /**
     * Save the items map to the file at path filePath.
     *
     * @param filePath the path of the data file
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(items);
        output.close();
    }

}