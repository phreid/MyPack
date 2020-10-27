package persistence;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Represents a writer that writes a JSON representation of a list of Packs to file
 *
 * ACKNOWLEDGEMENT: this class follows the structure of the JsonWriter class here:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destinationPath;

    // EFFECTS: constructs a new writer to write to file at filePath
    public JsonWriter(String filePath) {
        destinationPath = filePath;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, or throws FileNotFoundException if destination file
    //          cannot be opened
    public void open() throws IOException {
        writer = new PrintWriter(new File(destinationPath));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of packList to file
    public void write(List<Pack> packList) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Pack pack : packList) {
            jsonArray.put(packToJson(pack));
        }

        json.put("packs", jsonArray);
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: returns a JSONObject representing a single Pack
    private JSONObject packToJson(Pack pack) {
        JSONObject json = new JSONObject();
        json.put("name", pack.getName());
        json.put("description", pack.getDescription());

        JSONArray childrenToJson = new JSONArray();
        for (int i = 0; i < pack.size(); i++) {
            addCategoryToArray(childrenToJson, (PackList) pack.get(i));
        }

        json.put("children", childrenToJson);
        return json;
    }

    // MODIFIES: array
    // EFFECTS: adds a JSON representation of category to array
    private void addCategoryToArray(JSONArray array, PackList category) {
        JSONObject json = new JSONObject();
        json.put("name", category.getName());
        json.put("description", category.getDescription());

        JSONArray childrenToJson = new JSONArray();
        for (int i = 0; i < category.size(); i++) {
            addItemToArray(childrenToJson, (PackItem) category.get(i));
        }

        json.put("children", childrenToJson);
        array.put(json);
    }

    // MODIFIES: array
    // EFFECTS: adds a JSON representation of item to array
    private void addItemToArray(JSONArray array, PackItem item) {
        JSONObject json = new JSONObject();
        json.put("name", item.getName());
        json.put("description", item.getDescription());
        json.put("weight", item.getTotalWeight());
        json.put("cost", item.getCost());
        json.put("isWorn", item.isWorn());
        json.put("isConsumable", item.isConsumable());

        array.put(json);
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes String representation of JSON object to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
