package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one collection of gear. Can contain PackItems, or other PackLists
 * representing categories or other sub-lists of gear. To encourage clean
 * organization, it's recommended to use the Pack subclass to represent a
 * top-level collection such as an entire backpack.
 */
public class PackList extends AbstractEntry {
    protected List<AbstractEntry> children;

    public PackList(String name) {
        super(name);
        children = new ArrayList<>();
        hasChildren = true;
    }

    public PackList(String name, String description) {
        this(name);
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: entry is added to this
    public void add(AbstractEntry entry) {
        children.add(entry);
    }

    // MODIFIES: this
    // EFFECTS: entry is removed from this
    public void remove(AbstractEntry entry) {
        children.remove(entry);
    }

    // EFFECTS: returns the total weight of all entries in this
    @Override
    public int getTotalWeight() {
        int totalWeight = 0;

        for (AbstractEntry entry : children) {
            totalWeight += entry.getTotalWeight();
        }

        return totalWeight;
    }

    // EFFECTS: returns the total base weight of all entries in this
    @Override
    public int getBaseWeight() {
        int baseWeight = 0;

        for (AbstractEntry entry : children) {
            baseWeight += entry.getBaseWeight();
        }

        return baseWeight;
    }

    // EFFECTS: returns the total cost of all entries in this
    @Override
    public double getCost() {
        double cost = 0.0;

        for (AbstractEntry entry : children) {
            cost += entry.getCost();
        }

        return cost;
    }

    public int size() {
        return children.size();
    }

    // EFFECTS: returns the entry at position index
    public AbstractEntry get(int index) {
        return children.get(index);
    }
}
