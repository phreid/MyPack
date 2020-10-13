package model;

/**
 * Represents a top-level collection of gear, usually the items that fit
 * into one backpack.
 *
 * Packs can contain PackItems directly, but for organization purposes
 * it's recommended that Packs contain only PackLists.
 * To facilitate this, new Packs are created with a default
 * PackList for users to add PackItems to.
 */
public class Pack extends PackList {
    public static final String DEFAULT_CATEGORY_NAME = "(uncategorized)";

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: name is set to name, description is set to empty string,
    //          and a default PackList is added to this
    public Pack(String name) {
        super(name);
        children.add(new PackList(DEFAULT_CATEGORY_NAME));
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: name is set to name, description is set to description,
    //          and a default PackList is added to this
    public Pack(String name, String description) {
        super(name, description);
        children.add(new PackList(DEFAULT_CATEGORY_NAME));
    }
}
