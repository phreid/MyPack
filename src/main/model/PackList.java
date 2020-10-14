package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one collection of gear, i.e. an AbstractEntry with children.
 * Can contain PackItems, or other PackLists representing categories or other sub-lists of gear.
 * To encourage clean organization, it's recommended to use the Pack subclass to represent a
 * top-level collection such as an entire backpack.
 */
public class PackList extends AbstractEntry {
    protected List<AbstractEntry> children;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: name of entry is set to name, description is set to
    //          empty string
    public PackList(String name) {
        super(name);
        children = new ArrayList<>();
        hasChildren = true;
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: name of entry is set to name, description is set to
    //          description
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

    @Override
    public int getTotalWeight() {
        int totalWeight = 0;

        for (AbstractEntry entry : children) {
            totalWeight += entry.getTotalWeight();
        }

        return totalWeight;
    }

    @Override
    public int getBaseWeight() {
        int baseWeight = 0;

        for (AbstractEntry entry : children) {
            baseWeight += entry.getBaseWeight();
        }

        return baseWeight;
    }

    @Override
    public double getCost() {
        double cost = 0.0;

        for (AbstractEntry entry : children) {
            cost += entry.getCost();
        }

        return cost;
    }

    // EFFECTS: returns the number of child entries in this
    public int size() {
        return children.size();
    }

    // EFFECTS: returns the child entry at position index
    public AbstractEntry get(int index) {
        return children.get(index);
    }
}
