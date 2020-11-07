package model;

import java.io.IOException;

/**
 * Abstract class representing one entry (Pack, PackList, or PackItem) in a collection
 * of gear.
 *
 * Classes extending AbstractEntry can represent collections of gear (PackList, Pack) or
 * a single piece of gear (PackItem). If the class represents a collection, the
 * AbstractEntry is said to have children.
 */
public abstract class AbstractEntry {
    protected String name;
    protected String description;
    protected boolean hasChildren;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: creates a new AbstractEntry with no children. name of entry is set to name,
    //          description is set to empty string
    public AbstractEntry(String name) {
        this.name = name;
        this.description = "";
        hasChildren = false;
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: creates a new AbstractEntry with no children. name of entry is set to name,
    //          description is set to description
    public AbstractEntry(String name, String description) {
        this(name);
        this.description = description;
    }

    // EFFECTS: returns the total weight of this entry and any
    //          child entries in grams
    public abstract int getTotalWeight();

    // EFFECTS: returns the base weight of this entry and any
    //          child entries in grams
    public abstract int getBaseWeight();

    // EFFECTS: returns the total cost of this entry and any
    //          child entries in dollars and cents
    public abstract double getCost();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean hasChildren() {
        return hasChildren;
    }
}
