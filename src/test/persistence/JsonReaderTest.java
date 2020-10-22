package persistence;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private static String PATH = "./data/";

    @Test
    public void testReadEmptyFile() {
        JsonReader reader = new JsonReader(PATH + "testReaderEmptyList.json");
        try {
            List<Pack> packList = reader.read();
            assertEquals(0, packList.size());
        } catch (IOException e) {
            e.printStackTrace();
            fail("error reading file.");
        }
    }

    @Test
    public void testReadMinimalFile() {
        JsonReader reader = new JsonReader(PATH + "testReaderMinimalList.json");
        try {
            List<Pack> packList = reader.read();
            assertEquals(2, packList.size());

            Pack pack1 = packList.get(0);
            assertEquals("pack1", pack1.getName());
            assertEquals("", pack1.getDescription());
            assertEquals(1, pack1.size());
            assertTrue(pack1.get(0) instanceof PackList);

            PackList child1 = (PackList) pack1.get(0);
            assertEquals("(uncategorized)", child1.getName());
            assertEquals("", child1.getDescription());
            assertEquals(0, child1.size());

            Pack pack2 = packList.get(1);
            assertEquals("pack2", pack2.getName());
            assertEquals("desc1", pack2.getDescription());
            assertEquals(1, pack2.size());
            assertTrue(pack1.get(0) instanceof PackList);

            PackList child2 = (PackList) pack2.get(0);
            assertEquals("(uncategorized)", child2.getName());
            assertEquals("", child2.getDescription());
            assertEquals(0, child2.size());
        } catch (IOException e) {
            fail("error reading file.");
        }
    }

    @Test
    public void testReadNoFileExists() {
        JsonReader reader = new JsonReader(PATH + "not a real file");
        try {
            List<Pack> packList = reader.read();
            fail("no exception thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReadGeneralFile() {
        JsonReader reader = new JsonReader(PATH + "testReaderGeneralList.json");
        try {
            List<Pack> packList = reader.read();
            assertEquals(2, packList.size());

            Pack pack1 = packList.get(0);
            assertTrue(pack1.get(0) instanceof PackList);

            PackList child1 = (PackList) pack1.get(0);
            assertEquals(0, child1.size());

            PackList child2 = (PackList) pack1.get(1);
            assertEquals(2, child2.size());


            PackItem item1 = (PackItem) child2.get(0);
            assertEquals("item1",item1.getName());
            assertEquals("desc3", item1.getDescription());
            assertEquals(100, item1.getTotalWeight());
            assertEquals(50.50, item1.getCost(), 0.001);
            assertFalse(item1.isWorn());
            assertTrue(item1.isConsumable());

            assertEquals("item2",child2.get(1).getName());

            PackList child3 = (PackList) pack1.get(2);
            assertEquals("item3",child3.get(0).getName());

            Pack pack2 = packList.get(1);
            PackList child4 = (PackList) pack2.get(2);
            assertEquals("item4", child4.get(0).getName());


        } catch (IOException e) {
            fail("error reading file.");
        }
    }
}
