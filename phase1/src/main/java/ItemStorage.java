package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStorage {
    /**
     * A mapping of item ids to item.
     */
    private Map<Integer, Item> items;
    private FileReadWriter frw;
    private String path;

    /**
     * Creates an ItemManager with lists of Item that are empty
     *
     * @param filePath the path of the data file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ItemStorage(String filePath) throws IOException, ClassNotFoundException {
        this.path = filePath;
        items = new HashMap<>();
        File file = new File(path);
        frw = new FileReadWriter(path);
        if (file.exists()) {
            try {
                items = (Map<Integer, Item>) frw.readFromFile(path);
            } catch (EOFException e) {
                System.out.println("Cannot read from file");
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
     * @param typeOfItem    The type of item, can be a Book or Clothes
     * @param uniqueFeature Feature associated with the type of item. Author if it is a book, brand if it is clothes
     * @throws InvalidItemException Throws exception if the typeOfItem is not valid, i.e. not Book or Clothes
     * @throws IOException          file cannot be read
     */
    public Item newItem(String name, String description, String typeOfItem, String uniqueFeature) throws InvalidItemException, IOException {
        if (typeOfItem.equals("Book")) {
            return newBook(name, description, uniqueFeature);
        } else if (typeOfItem.equals("Clothing")) {
            return newClothing(name, description, uniqueFeature);
        } else {
            return newMiscItem(name, description);
        }
        //else throw new InvalidItemException();
    }

    /**
     * Initializes a new book based on the given parameters. Adds the item to items
     *
     * @param name        The name of the book
     * @param description The description of the book
     * @param author      The author of the book
     * @return book
     * @throws IOException file cannot be read
     */
    public Book newBook(String name, String description, String author) throws IOException {
        int id = getNumberOfItems();
        Book book = new Book(name, description, id, author);
        addItem(book);
        return book;
    }

    /**
     * Initializes a new clothing based on the given parameters. Adds the item to items
     *
     * @param name        The name of the clothing
     * @param description The description of the clothing
     * @param brand       The brand of the clothing
     * @return clothing
     * @throws IOException file cannot be read
     */
    public Clothing newClothing(String name, String description, String brand) throws IOException {
        Clothing clothing = new Clothing(name, description, getNumberOfItems(), brand);
        addItem(clothing);
        return clothing;
    }

    /**
     * Initializes a new misc item based on the given parameters. Adds the item to items
     *
     * @param name        The name of the item
     * @param description The description of the item
     * @return miscItem
     * @throws IOException file cannot be read
     */
    public Item newMiscItem(String name, String description) throws IOException {
        MiscItem miscItem = new MiscItem(name, description, getNumberOfItems());
        addItem(miscItem);
        return miscItem;
    }

    /**
     * Adds the id and the instance of Item to the overall list of Items
     *
     * @param item The Item object that needs to be added
     * @throws IOException file cannot be read
     */
    public void addItem(Item item) throws IOException {
        items.put(item.getID(), item);
        frw.saveToFile(items, path);
    }

    /**
     * Removes an instance of Item from the overall list of Items
     *
     * @param itemId The id of the Item object that needs to be removed
     * @throws ItemNotFoundException item not in system
     * @throws IOException           file cannot be read
     */
    public void removeItem(int itemId) throws ItemNotFoundException, IOException {
        if (items.containsKey(itemId)) {
            items.remove(itemId);
            frw.saveToFile(items, path);
        } else {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Verify an Item in the overall list of Items
     *
     * @param itemId The id of the Item object that needs to be verified
     * @throws ItemNotFoundException item not in system
     * @throws IOException           file cannot be read
     */
    public void verifyItem(int itemId) throws ItemNotFoundException, IOException {
        if (items.containsKey(itemId)) {
            items.get(itemId).setIsVerified(true);
            frw.saveToFile(items, path);
        } else {
            throw new ItemNotFoundException();
        }
    }


    /**
     * Get all verified Items in the overall list of Items
     *
     * @return all verified Items
     */
    public List<Item> getVerifiedItems() {
        List<Item> verifiedItems = new ArrayList<>();
        // Citation begin -->https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            //Citation end
            Item item = entry.getValue();
            if (item.getIsVerified()) {
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
    public List<Item> getUnverifiedItems() {
        List<Item> unverifiedItems = new ArrayList<>();
        // Citation begin -->https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            //Citation end
            Item item = entry.getValue();
            if (!item.getIsVerified()) {
                unverifiedItems.add(item);
            }
        }
        return unverifiedItems;
    }
}
