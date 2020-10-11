package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackListTest {
    private PackList packList;

    @BeforeEach
    public void runBefore() {
        packList = new PackList("test");
        packList.setDescription("zzz");
    }

    @Test
    public void testAddEntry() {
        PackItem item1 = new PackItem("a");
        packList.add(item1);
        assertEquals(1, packList.size());

        PackItem item2 = new PackItem("b");
        PackItem item3 = new PackItem("c");
        packList.add(item2);
        packList.add(item3);

        assertEquals(3, packList.size());
        assertEquals("a", packList.get(0).getName());
        assertEquals("b", packList.get(1).getName());
        assertEquals("c", packList.get(2).getName());
    }

    @Test
    public void testRemoveEntry() {
        PackItem item1 = new PackItem("a");
        packList.add(item1);

        packList.remove(item1);
        assertEquals(0, packList.size());
    }

    @Test
    public void testGetTotalWeight() {
        assertEquals(0, packList.getTotalWeight());

        PackItem item1 = new PackItem("a");
        item1.setWeight(100);
        PackItem item2 = new PackItem("b");
        item2.setWeight(200);
        PackItem item3 = new PackItem("c");
        item3.setWeight(300);
        item3.setConsumable(true);

        packList.add(item1);
        packList.add(item2);
        packList.add(item3);

        assertEquals(600, packList.getTotalWeight());
    }

    @Test
    public void testGetBaseWeight() {
        assertEquals(0, packList.getBaseWeight());

        PackItem item1 = new PackItem("a");
        item1.setWeight(100);
        PackItem item2 = new PackItem("b");
        item2.setWeight(200);
        PackItem item3 = new PackItem("c");
        item3.setWeight(300);
        item3.setConsumable(true);

        packList.add(item1);
        packList.add(item2);
        packList.add(item3);

        assertEquals(300, packList.getBaseWeight());
    }

    @Test
    public void testGetCost() {
        assertEquals(0.0, packList.getCost(),0.001);

        PackItem item1 = new PackItem("a");
        item1.setCost(100.10);
        PackItem item2 = new PackItem("b");
        item2.setCost(50.25);
        PackItem item3 = new PackItem("c");
        item3.setCost(50.50);

        packList.add(item1);
        packList.add(item2);
        packList.add(item3);

        assertEquals(200.85, packList.getCost(),0.001);
    }
}
