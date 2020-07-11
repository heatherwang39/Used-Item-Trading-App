package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    /**
     * A mapping of item ids to item.
     */
    private Map<Integer, Item> items;

    String path;

    /**
     * Creates an ItemManager with lists of Item that are empty
     *
     * @param filePath the path of the data file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ItemManager(String filePath) throws IOException, ClassNotFoundException {
        items = new HashMap<Integer, Item>();
        path = filePath;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                readFromFile(filePath);
            } catch (EOFException e) {
            }
        } else {
            file.createNewFile();
        }
    }

    /**
     * Gets an item based on id
     *
     * @param itemID integer item ID
     * @return item
     * @throws ItemNotFoundException if no item with ID
     */
    public Item getItem(int itemID) throws ItemNotFoundException {
        Item item = items.get(itemID);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        return item;
    }

    /**
     * Gets the total number of items added
     *
     * @return the size of items
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * Initializes a new item based on the given parameters
     *
     * @param name          The name of the item
     * @param description   The description of the item
     * @param typeOfItem    The type of item, can be a Book or Clothing
     * @param uniqueFeature Feature associated with the type of item. Author if it is a book, brand if it is clothing
     * @throws InvalidItemException Throws exception if the typeOfItem is not valid, i.e. not Book or Clothing
     * @throws IOException file cannot be read
     */
    public Item newItem(String name, String description, String typeOfItem, String uniqueFeature) throws IOException,
            ItemNotFoundException {
        if (typeOfItem.equals("Book")) {
            return newBook(name, description, uniqueFeature);
        } else if (typeOfItem.equals("Clothing")) {
            return newClothing(name, description, uniqueFeature);
        } else {
            return newMiscItem(name, description);
        }
    }

    /**
     * Initializes a new book based on the given parameters. Adds the item to items
     *
     * @param name The name of the book
     * @param description The description of the book
     * @param author The author of the book
     * @throws IOException file cannot be read
     * @return book
     */
    public Item newBook(String name, String description, String author) throws IOException, ItemNotFoundException {
        int id = getNumberOfItems();
        Book book = new Book(name, description, id, author);
        addItem(book);
        return book;
    }

    /**
     * Initializes a new clothing based on the given parameters. Adds the item to items
     *
     * @param name The name of the clothing
     * @param description The description of the clothing
     * @param brand The brand of the clothing
     * @throws IOException file cannot be read
     * @return clothing
     */
    public Clothing newClothing(String name, String description, String brand) throws IOException {
        Clothing clothing = new Clothing(name, description, getNumberOfItems(), brand);
        addItem(clothing);
        return clothing;
    }

    /**
     * Initializes a new misc item based on the given parameters. Adds the item to items
     *
     * @param name The name of the item
     * @param description The description of the item
     * @throws IOException file cannot be read
     * @return miscItem
     */
    public Item newMiscItem(String name, String description) throws IOException {
        MiscItem miscItem = new MiscItem(name, description, getNumberOfItems());
        addItem(miscItem);
        return miscItem;
    }

    /**
     * Adds the id and the instance of Item to the overall list of Items
     * @param item The Item object that needs to be added
     * @throws IOException file cannot be read
     */
    public void addItem(Item item) throws IOException {
        items.put(item.getID(), item);
        saveToFile(path);
    }

    /**
     * Removes an instance of Item from the overall list of Items
     * @param itemId The id of the Item object that needs to be removed
     * @throws ItemNotFoundException item not in system
     * @throws IOException file cannot be read
     */
    public void removeItem(int itemId) throws ItemNotFoundException, IOException {
        items.remove(itemId);
        saveToFile(path);
    }

    /**
     * Verify an Item in the overall list of Items
     * @param itemId The id of the Item object that needs to be verified
     * @throws ItemNotFoundException
     * @throws IOException file cannot be read
     */
    public void verifyItem(int itemId) throws ItemNotFoundException, IOException {
        items.get(itemId).setIsVerified(true);
        saveToFile(path);
    }


    /**
     * Get all verified Items in the overall list of Items
     *
     * @return all verified Items
     */
    public List<Item> getVerifiedItems(){
        List<Item> verifiedItems = new ArrayList<Item>();;
        // Citation begin -->https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
        //Citation end
            Item item = entry.getValue();
            if(item.getIsVerified()){
                verifiedItems.add(item);
            }
        }
        return verifiedItems;
    }

    /**
     * Get all unverified Items in the overall list of items
     *
     * @return all unverified Items
     */
    public List<Item> getUnverifiedItems(){
        List<Item> unverifiedItems = new ArrayList<Item>();;
        // Citation begin -->https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            //Citation end
            Item item = entry.getValue();
            if(!item.getIsVerified()){
                unverifiedItems.add(item);
            }
        }
        return unverifiedItems;
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