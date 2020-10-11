package model;

/**
 * Represents a top-level collection of gear, usually the items that fit
 * into one backpack.
 *
 * Packs can contain PackItems directly, but for organization purposes this
 * it's recommended that Packs contain only PackLists.
 * To facilitate this, new Packs are created with a default
 * PackList for users to add PackItems to.
 */
public class Pack extends PackList {
    public static final String DEFAULT_CATEGORY_NAME = "(uncategorized)";

    public Pack(String name) {
        super(name);
        children.add(new PackList(DEFAULT_CATEGORY_NAME));
    }

    public Pack(String name, String description) {
        super(name, description);
        children.add(new PackList(DEFAULT_CATEGORY_NAME));
    }
}
