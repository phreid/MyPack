package model;

/**
 * Represents a single piece of gear in a pack. Items have
 * a name, description, cost (in dollars), and weight (in grams).
 * Description is blank by default. Cost and weight
 * are zero by default (an item can't literally weigh nothing, but
 * for small items the weight might be inconsequential.)
 *
 * Items can be marked as worn and/or consumable. These items count
 * towards total weight, but not base weight.
 */
public class PackItem extends AbstractEntry {
    private int weight;
    private double cost;
    private boolean isWorn;
    private boolean isConsumable;

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    // EFFECTS: name of item is set to name, description is set to empty string,
    //          weight and cost are set to zero, item is neither worn nor consumable
    public PackItem(String name) {
        super(name);
        weight = 0;
        cost = 0.0;
        isWorn = false;
        isConsumable = false;
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    // EFFECTS: name of item is set to name, description is set to description,
    //          weight and cost are set to zero, item is neither worn nor consumable
    public PackItem(String name, String description) {
        this(name);
        this.description = description;
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    // EFFECTS: name of item is set to name, description is set to description,
    //          weight is set to weight, cost is set to cost,
    //          item's worn status is set to isWorn, item's consumable status
    //          is set to isConsumable
    public PackItem(String name, String description, int weight,
                    double cost, boolean isWorn, boolean isConsumable) {
        super(name, description);
        this.weight = weight;
        this.cost = cost;
        this.isWorn = isWorn;
        this.isConsumable = isConsumable;
    }

    @Override
    public int getTotalWeight() {
        return weight;
    }

    @Override
    public int getBaseWeight() {
        if (isWorn || isConsumable) {
            return 0;
        } else {
            return weight;
        }
    }

    public double getCost() {
        return cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isWorn() {
        return isWorn;
    }

    public void setWorn(boolean worn) {
        isWorn = worn;
    }

    public boolean isConsumable() {
        return isConsumable;
    }

    public void setConsumable(boolean consumable) {
        isConsumable = consumable;
    }
}
