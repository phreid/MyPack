package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackItemTest {
    private PackItem item;

    @BeforeEach
    public void runBefore() {
        item = new PackItem("test");
    }

    @Test
    public void testConstructorName() {
        PackItem item = new PackItem("test");
        assertEquals(0, item.getBaseWeight());
        assertEquals(0, item.getTotalWeight());
        assertEquals(0.0, item.getCost(), 0.001);
        assertFalse(item.isConsumable());
        assertFalse(item.isWorn());
        assertFalse(item.hasChildren());
    }

    @Test
    public void testConstructorAllFields() {
        PackItem item = new PackItem("test", "desc", 100,
                1.0, true, false);
        assertEquals(0, item.getBaseWeight());
        assertEquals(100, item.getTotalWeight());
        assertEquals(1.0, item.getCost(), 0.001);
        assertFalse(item.isConsumable());
        assertTrue(item.isWorn());
        assertFalse(item.hasChildren());
    }

    @Test
    public void testGetBaseWeight() {
        item.setWeight(100);
        assertEquals(100, item.getBaseWeight());

        item.setConsumable(true);
        assertEquals(0, item.getBaseWeight());

        item.setWorn(true);
        assertEquals(0, item.getBaseWeight());

        item.setConsumable(false);
        assertEquals(0, item.getBaseWeight());
    }

    @Test
    public void testGetTotalWeight() {
        item.setWeight(100);
        assertEquals(100, item.getTotalWeight());

        item.setConsumable(true);
        assertEquals(100, item.getTotalWeight());

        item.setWorn(true);
        assertEquals(100, item.getTotalWeight());
    }
}
