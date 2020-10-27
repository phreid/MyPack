package ui;

import model.AbstractEntry;
import model.PackItem;
import model.PackList;
import model.Pack;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Runs the app and processes user input.
 *
 * ACKNOWLEDGEMENT: The structure of this class follows the TellerApp class from the
 * TellerApp project
 */
public class MyPackApp {
    private List<Pack> packs;
    private Scanner input;
    private Pack currentPack;

    private static final int NAME_CELL_WIDTH = 30;
    private static final String DEFAULT_FILE_PATH = "./data/savedPacks.json";

    // MODIFIES: this
    // EFFECTS: runs the application
    public MyPackApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input, saves the list of packs on exit
    private void runApp() {
        init();

        String command;

        JsonWriter writer = new JsonWriter(DEFAULT_FILE_PATH);
        while (true) {
            displayTopMenu();
            command = input.nextLine();

            if (command.equals("q")) {
                try {
                    writer.open();
                    writer.write(packs);
                } catch (IOException e) {
                    System.out.println("error saving to file. your changes were not saved.");
                } finally {
                    writer.close();
                }
                break;
            } else {
                processTopMenu(command);
            }
        }

        System.out.println("\nyour changes were saved.");
        System.out.println("\ngoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the app and loads the saved packs file
    private void init() {
        try {
            packs = loadPacks();
        } catch (IOException e) {
            System.out.println("error reading saved packs file.");
            System.exit(0); // TODO: handle load error better
        }
        input = new Scanner(System.in);
    }

    // EFFECTS: reads the saved packs file and returns it as a list of Packs,
    //          or throws IOException
    private List<Pack> loadPacks() throws IOException {
        JsonReader reader = new JsonReader(DEFAULT_FILE_PATH);
        return reader.read();
    }

    // EFFECTS: displays the top-level pack menu
    private void displayTopMenu() {
        System.out.println("\nYour Packs:");

        for (int i = 0; i < packs.size(); i++) {
            printEntry(packs.get(i), i, 0);
        }

        System.out.println("\nSelect from:");
        System.out.println("\t(n)ew pack");
        System.out.println("\t(e)dit pack");
        System.out.println("\t(d)elete pack");
        System.out.println("\t(q)uit");
    }

    // MODIFIES: this
    // EFFECTS: processes input at the top-level pack menu
    private void processTopMenu(String command) {
        if (command.equals("n")) {
            List<PackList> newList = newListMenu(true);
            for (PackList list : newList) {
                packs.add((Pack) list);
            }
        } else if (command.equals("e")) {
            choosePackMenu();
        } else if (command.equals("d")) {
            // TODO: delete pack menu
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the new list menu for users to create one or more new lists. if isPack
    //          is true, user is at the top-level menu and this method will return a list of Pack.
    //          otherwise, returns a list of PackList
    private List<PackList> newListMenu(boolean isPack) {
        String command;
        PackList newList;
        List<PackList> result = new ArrayList<>();

        while (true) {
            if ((newList = getNewList(isPack)) == null) {
                break;
            }

            result.add(newList);

            while (true) {
                System.out.println("\nAdded " + (isPack ? "pack" : "category") + ". Select from:");
                System.out.println("\t(f)inished");
                System.out.println("\t(a)dd another");
                command = input.nextLine();

                if (command.equals("f")) {
                    return result;
                } else if (command.equals("a")) {
                    break;
                } else {
                    System.out.println("Please enter a valid command.");
                }
            }
        }

        return result;
    }

    // EFFECTS: prompts the user for a name and description for a new PackList.
    //          if isPack is true, the user is at the top-level menu and the method returns a Pack.
    //          otherwise, returns a PackList. returns null if the user cancels
    private PackList getNewList(boolean isPack) {
        String name;
        String description;

        System.out.println("New " + (isPack ? "pack" : "category") + " (enter 'c' to cancel):");
        name = getName();
        if (name == null) {
            return null;
        }

        description = getDescription();
        if (description == null) {
            return null;
        }

        if (isPack) {
            return new Pack(name, description);
        } else {
            return new PackList(name, description);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the choose pack menu and processes user input
    private void choosePackMenu() {
        String command;
        int index;

        if (packs.isEmpty()) {
            System.out.println("\nNo packs to edit.");
            return;
        }

        while (true) {
            System.out.print("\nEnter the pack number to edit (or 'c' to cancel): ");
            command = input.nextLine();
            if (command.equals("c")) {
                break;
            }

            index = getInteger(command);

            if (index > packs.size() - 1) {
                System.out.println("\nPlease enter a valid pack number.");
            } else {
                currentPack = packs.get(index);
                displayEditPackMenu();
                break;
            }
        }
    }

    // REQUIRES: command is a string representation of an integer greater than
    //           or equal to zero
    // EFFECTS: returns the int represented by command
    private int getInteger(String command) {
        return Integer.parseInt(command);
    }

    // REQUIRES: command is a string representation of a double greater than
    //           or equal to zero
    // EFFECTS:  returns the double represented by command
    private double getDouble(String command) {
        return Double.parseDouble(command);
    }

    // MODIFIES: this
    // EFFECTS: displays the edit pack menu
    private void displayEditPackMenu() {
        while (true) {
            System.out.println("\n");
            printCurrentPack();

            System.out.println("\nSelect from:");
            System.out.println("\tedit (n)ame");
            System.out.println("\tedit (d)escription");
            System.out.println("\t(a)dd category");
            //System.out.println("\tedit (c)ategory"); TODO: implement edit category menu
            System.out.println("\tadd (i)tem");
            System.out.println("\t(r)eturn to previous menu");

            String command = input.nextLine();
            if (command.equals("r")) {
                break;
            } else {
                processEditPackMenu(command);
            }
        }
    }

    // EFFECTS: processes input at the edit pack menu
    private void processEditPackMenu(String command) {
        if (command.equals("n")) {
            processEditPackName();
        } else if (command.equals("d")) {
            processEditPackDescription();
        } else if (command.equals("a")) {
            processAddCategory();
        } else if (command.equals("c")) {
            // TODO: implement edit category menu
        } else if (command.equals("i")) {
            processAddItem();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input at the add item menu
    private void processAddItem() {
        PackList list = chooseListMenu();
        if (list != null) {
            List<PackItem> items = newItemMenu();
            for (PackItem item : items) {
                list.add(item);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input at the add category menu
    private void processAddCategory() {
        List<PackList> newList = newListMenu(false);
        for (PackList list : newList) {
            currentPack.add(list);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input at the edit pack description menu
    private void processEditPackDescription() {
        String description = getDescription();
        if (description != null) {
            currentPack.setDescription(description);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input at the edit pack name menu
    private void processEditPackName() {
        String name = getName();
        if (name != null) {
            currentPack.setName(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to choose a PackList from the current pack and returns it, or returns null
    //          if the user cancels. Does not allow the user to select a Pack.
    private PackList chooseListMenu() {
        String command;
        int index;
        List<AbstractEntry> entries = getAllEntries(currentPack);

        while (true) {
            System.out.print("\nEnter the category number to add an item to (or 'c' to cancel): ");
            command = input.nextLine();
            if (command.equals("c")) {
                break;
            }

            index = getInteger(command);

            if (index > entries.size() - 1
                    || entries.get(index) instanceof Pack
                    || entries.get(index) instanceof PackItem) {
                System.out.println("\nPlease enter a valid category number.");
            } else {
                return (PackList) entries.get(index);
            }
        }

        return null;
    }

    // MODIFIES: this
    // EFFECTS: displays the new item menu for users to create one or more new items.
    //          returns a list of the new items
    private List<PackItem> newItemMenu() {
        String command;
        PackItem newItem;
        List<PackItem> result = new ArrayList<>();

        while (true) {
            if ((newItem = getNewItem()) == null) {
                break;
            }

            result.add(newItem);

            while (true) {
                System.out.println("\nAdded item. Select from:");
                System.out.println("\t(f)inished");
                System.out.println("\t(a)dd another");
                command = input.nextLine();

                if (command.equals("f")) {
                    return result;
                } else if (command.equals("a")) {
                    break;
                } else {
                    System.out.println("Please enter a valid command.");
                }
            }
        }

        return result;
    }

    // EFFECTS: displays the new item menu for users to create one new PackItem.
    //          returns the PackItem, or null if the user cancels.
    private PackItem getNewItem() {
        System.out.println("New item (enter 'c' to cancel):");

        String name = getName();
        if (name == null) {
            return null;
        }

        String description = getDescription();
        if (description == null) {
            return null;
        }

        Integer weight = getWeight();
        if (weight == null) {
            return null;
        }

        Double cost = getCost();
        if (cost == null) {
            return null;
        }

        List<Boolean> booleans = getPackItemBooleans();
        if (booleans == null) {
            return null;
        }

        return new PackItem(name, description, weight, cost, booleans.get(0), booleans.get(1));
    }

    // EFFECTS: gets a list of booleans representing [isWorn, isConsumable] for
    //          a single PackItem, or returns null if the user cancels
    private List<Boolean> getPackItemBooleans() {
        List<Boolean> result = new ArrayList<>();

        Boolean isWorn = getIsWorn();
        if (isWorn == null) {
            return null;
        } else {
            result.add(isWorn);
        }

        Boolean isConsumable = getIsConsumable();
        if (isConsumable == null) {
            return null;
        } else {
            result.add(isConsumable);
        }

        return result;
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a name and returns it, or
    //          returns null if the user cancels.
    private String getName() {
        String command;

        while (true) {
            System.out.print("name (required): ");
            command = input.nextLine();
            if (command.length() == 0) {
                System.out.println("\nA name is required.");
            } else if (command.equals("c")) {
                return null;
            } else {
                return command;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a description and returns it, or
    //          returns null if the user cancels.
    private String getDescription() {
        String command;

        System.out.print("description (optional): ");
        command = input.nextLine();
        if (command.equals("c")) {
            return null;
        } else {
            return command;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a weight and returns it, or
    //          returns null if the user cancels.
    private Integer getWeight() {
        String command;

        System.out.print("weight (in grams, required) (please enter numbers only): ");
        command = input.nextLine();
        if (command.equals("c")) {
            return null;
        } else {
            return getInteger(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a cost and returns it, or
    //          returns null if the user cancels.
    private Double getCost() {
        String command;

        System.out.print("cost (in dollars and cents, required) (please enter numbers only): ");
        command = input.nextLine();
        if (command.equals("c")) {
            return null;
        } else {
            return getDouble(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a boolean representing if the item is worn and
    //          returns it, or returns null if the user cancels.
    private Boolean getIsWorn() {
        String command;

        System.out.print("is the item worn? ('true' or 'false', required): ");
        command = input.nextLine();
        if (command.equals("c")) {
            return null;
        } else {
            return getBoolean(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a boolean representing if the item is worn and
    //          returns it, or returns null if the user cancels.
    private Boolean getIsConsumable() {
        String command;

        System.out.print("is the item consumable? ('true' or 'false', required): ");
        command = input.nextLine();
        if (command.equals("c")) {
            return null;
        } else {
            return getBoolean(command);
        }
    }

    // REQUIRES: command is one "true" or "false"
    // EFFECTS: returns the boolean represented by command
    private boolean getBoolean(String command) {
        return Boolean.parseBoolean(command);
    }

    // EFFECTS: prints the current Pack to the console
    private void printCurrentPack() {
        List<AbstractEntry> entries = getAllEntries(currentPack);
        for (int i = 0; i < entries.size(); i++) {
            AbstractEntry entry = entries.get(i);
            if (entry instanceof Pack) {
                printEntry(entry, i, 0);
            } else if (entry instanceof PackList) {
                printEntry(entry, i, 1);
            } else {
                printEntry(entry, i, 2);
            }
        }
    }

    // REQUIRES: index and indentLevel greater than or equal to 0
    // EFFECTS: prints a single entry to the console
    // ACKNOWLEDGEMENT: information on String.format() came from here:
    //                  https://dzone.com/articles/java-string-format-examples.
    //                  (I knew about StringBuilder from previous Java experience)
    private void printEntry(AbstractEntry entry, int index, int indentLevel) {
        StringBuilder builder = new StringBuilder();
        StringBuilder indentBuilder = new StringBuilder();

        do {
            indentBuilder.append("  ");
            indentLevel--;
        } while (indentLevel >= 0);

        String indent = indentBuilder.toString();
        int nameCellWidth = NAME_CELL_WIDTH - indent.length();
        String nameCellFormat = "%-" + nameCellWidth + "s";

        builder.append("(").append(index).append(")")
                .append(indent).append(String.format(nameCellFormat, entry.getName()))
                .append(String.format("%-20.18s", entry.getDescription()))
                .append(String.format("%-8.7s", "$" + String.format("%.2f", entry.getCost())))
                .append(String.format("%-21.20s", "total weight: " + entry.getTotalWeight() + "g"))
                .append(String.format("%-20.19s", "base weight: " + entry.getBaseWeight() + "g"));

        if (entry.hasChildren()) {
            builder.append(String.format("%-20s", ""))
                    .append(String.format("%-20s", ""));
        } else {
            builder.append(String.format("%-20s", "consumable: " + ((PackItem) entry).isConsumable()))
                    .append(String.format("%-20s", "worn: " + ((PackItem) entry).isWorn()));
        }

        System.out.println(builder.toString());
    }

    // EFFECTS: returns all the entries in packList, including packList itself.
    //          ui helper function, allows to users select PackItems directly from the
    //          pack menu without needing to select a category first
    private List<AbstractEntry> getAllEntries(PackList packList) {
        List<AbstractEntry> result = new ArrayList<>();
        result.add(packList);

        for (int i = 0; i < packList.size(); i++) {
            AbstractEntry entry = packList.get(i);
            result.add(entry);
            if (entry.hasChildren()) {
                PackList list = (PackList) entry;
                for (int j = 0; j < list.size(); j++) {
                    result.add(list.get(j));
                }
            }
        }

        return result;
    }
}
