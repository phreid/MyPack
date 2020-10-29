package persistence;

import model.Pack;
import model.PackItem;
import model.PackList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a reader that loads a list of Packs from JSON data stored in a file.
 *
 * ACKNOWLEDGEMENT: this class follows the structure of the JsonReader class here:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
 */
public class JsonReader {
    private String filePath;

    // EFFECTS: constructs a new reader from source
    public JsonReader(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: reads list of Packs from source file and returns the list
    //          or throws IOException if an error occurs while reading the file
    public List<Pack> read() throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePacks(jsonObject);
    }

    // EFFECTS: reads data from a file and returns it as a String,
    //          or throws IOException if an error occurs while reading the file
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();

        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
        }

        return builder.toString();
    }

    // EFFECTS: parses JSON object into list of Packs and returns the list
    private List<Pack> parsePacks(JSONObject jsonObject) {
        List<Pack> packs = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("packs");

        for (Object json : jsonArray) {
            Pack nextPack = parseSinglePack((JSONObject) json);
            packs.add(nextPack);
        }

        return packs;
    }

    // EFFECTS: parses JSON object into a single Pack and returns it
    private Pack parseSinglePack(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Pack pack = new Pack(name, description);

        JSONArray jsonArray = jsonObject.getJSONArray("children");
        for (Object json : jsonArray) {
            addPackListToPack((JSONObject) json, pack);
        }

        return pack;
    }

    // MODIFIES: pack
    // EFFECTS: parses JSON object into a PackList and adds it to pack
    private void addPackListToPack(JSONObject jsonObject, Pack pack) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        boolean isDefaultCategory = name.equals(Pack.DEFAULT_CATEGORY_NAME);

        PackList packList;
        if (isDefaultCategory) {
            packList = (PackList) pack.get(0);
        } else {
            packList = new PackList(name, description);
        }

        JSONArray jsonArray = jsonObject.getJSONArray("children");
        for (Object json : jsonArray) {
            addItemToPackList((JSONObject) json, packList);
        }

        if (! isDefaultCategory) {
            pack.add(packList);
        }
    }

    // MODIFIES: packList
    // EFFECTS: parses JSON object into a PackItem and adds it to packList
    private void addItemToPackList(JSONObject jsonObject, PackList packList) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int weight = jsonObject.getInt("weight");
        double cost = jsonObject.getDouble("cost");
        boolean isWorn = jsonObject.getBoolean("isWorn");
        boolean isConsumable = jsonObject.getBoolean("isConsumable");

        PackItem item = new PackItem(name, description, weight, cost, isWorn, isConsumable);
        packList.add(item);
    }
}
