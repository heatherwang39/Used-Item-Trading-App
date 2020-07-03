package main.java;

import java.io.*;
import java.util.*;

public class RequestManager {
    public List<Map> requests;

    /**
     * Return the list of requests
     * Each request is a Map where keys are the names of the user making the request, and value is the item
     *
     * @return the list of requests
     */
    public List<Map> getRequests() {
        return requests;
    }

    /**
     * Populates the requests list from the file at path filePath
     *
     * @param filePath the path where the data file is found
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] line;
        Item item;
        Map<String, Item> request = new HashMap<String, Item>();

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().split(",");
            item = new Item(line[1], line[2]); //Currently using abstract item, will change when an item making method is ready
            request.put(line[0], item);
            requests.add(request);
        }
        scanner.close();
    }

    /**
     * Writes a request to the file at path filePath
     *
     * @param filePath the path where the data file is found
     * @throws FileNotFoundException if filePath is not a valid path
     * @throws IOException
     */
    public void writeToCSVFile(String filePath, String username, Item item) throws IOException, FileNotFoundException {
        FileWriter fileWriter = new FileWriter(filePath);
        StringBuilder sb = new StringBuilder();

        sb.append(username);
        sb.append(", ");
        sb.append(item.getName());
        sb.append(", ");
        sb.append(item.getDescription());
        sb.append("\n");

        fileWriter.write(sb.toString());
        fileWriter.close();
    }
}
