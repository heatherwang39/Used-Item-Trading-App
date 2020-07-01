package main.java;

import javax.naming.InvalidNameException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AccountManager {

    Map<String, Account> accounts;
    String path;

    public AccountManager(String path) throws IOException, ClassNotFoundException {
        accounts = new HashMap<>();
        this.path = path;
        File file = new File(path);
        if (file.exists()) {
            readFromFile(path);
        } else {
            file.createNewFile();
        }
    }

    private void readFromFile(String path) throws ClassNotFoundException, IOException {
        InputStream file = new FileInputStream(path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        accounts = (Map<String, Account>) input.readObject();
        input.close();
    }

    private void saveToFile(String path) throws IOException {
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(accounts);
        output.close();
    }

    private boolean isInvalidLogin(String username, String password) {
        // Regex from: https://stackoverflow.com/questions/34916716
        Pattern p = Pattern.compile("^[a-zA-Z]([\\w -]*[a-zA-Z])?$");
        return !p.matcher(username).matches() || !p.matcher(password).matches();
    }

    private boolean isInvalidEmail(String email) {

        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
                "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(" +
                "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
                "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08" +
                "\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))");
        return !p.matcher(email).matches();
    }

    public UserAccount createUserAccount(String username, String password, String email, boolean isFrozen)
            throws IOException, InvalidLoginException, InvalidEmailException {
        if (isInvalidLogin(username, password)) { throw new InvalidLoginException(); }
        if (isInvalidEmail(email)) {throw new InvalidEmailException(); }
        UserAccount user = new UserAccount(username, password, email, isFrozen);
        accounts.put(username, user);
        saveToFile(path);
        return user;
    }

    public AdminAccount createAdminAccount(String username, String password, String email)
            throws IOException, InvalidLoginException, InvalidEmailException {
        if (isInvalidLogin(username, password)){ throw new InvalidLoginException(); }
        if (isInvalidEmail(email)) {throw new InvalidEmailException(); }
        AdminAccount user = new AdminAccount(username, password, email);
        accounts.put(username, user);
        saveToFile(path);
        return user;
    }

    public Account getAccount(String username) throws AccountNotFoundException{
        if (accounts.containsKey(username)){
            return accounts.get(username);
        } else {
            throw new AccountNotFoundException();
        }
    }

    public List<Account> getAccounts() { return new ArrayList<>(accounts.values()); }

    public boolean tryLogin(String username, String password){
        return accounts.containsKey(username) && accounts.get(username).isPassword(password);
    }

    public void addInventory(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).addInventory(itemID);
        saveToFile(path);
    }

    public void addInventory(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.addInventory(id);}
        saveToFile(path);
    }

    public void addWishlist(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).addWishlist(itemID);
        saveToFile(path);
    }

    public void addWishlist(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.addWishlist(id);}
        saveToFile(path);
    }

    public void removeInventory(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).removeInventory(itemID);
        saveToFile(path);
    }

    public void removeInventory(String username, List<Integer> itemIDs) throws AccountNotFoundException,
            IOException {
        Account account = getAccount(username);
        for (int id: itemIDs) { account.removeInventory(id);}
        saveToFile(path);
    }

    public void removeWishlist(String username, int itemID) throws AccountNotFoundException, IOException {
        getAccount(username).removeWishlist(itemID);
        saveToFile(path);
    }

    public void removeWishlist(String username, List<Integer> itemIDs) throws AccountNotFoundException, IOException {
        Account account = getAccount(username);
        for (int id : itemIDs) {
            account.removeWishlist(id);
        }
        saveToFile(path);
    }
}
