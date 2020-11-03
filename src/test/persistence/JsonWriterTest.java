package persistence;

import model.AbstractEntry;
import model.Pack;
import model.PackItem;
import model.PackList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ACKNOWLEDGEMENT: this class follows the structure of JsonWriterTest here:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java
 */
public class JsonWriterTest {
    private static String PATH = "./data/";

    @Test
    public void testWriterInvalidFile() {
        try {
            Pack pack = new Pack("pack1");
            JsonWriter writer = new JsonWriter(PATH + "bad\0f:ilename.json");
            writer.open();
            fail("Expected FileNotFoundException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyPackList() {
        try {
            List<AbstractEntry> packs = new ArrayList<>();
            JsonWriter writer = new JsonWriter(PATH + "testWriterEmptyList.json");
            writer.open();
            writer.write(packs);
            writer.close();

            JsonReader reader = new JsonReader(PATH + "testWriterEmptyList.json");
            List<AbstractEntry> readPacks = reader.read();
            assertTrue(readPacks.isEmpty());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void testWriterSimpleList() {
        try {
            List<AbstractEntry> packs = new ArrayList<>();
            packs.add(new Pack("pack1"));
            packs.add(new Pack("pack2", "desc2"));
            packs.add(new Pack("pack3", "desc3"));

            JsonWriter writer = new JsonWriter(PATH + "testWriterSimpleList.json");
            writer.open();
            writer.write(packs);
            writer.close();

            JsonReader reader = new JsonReader(PATH + "testWriterSimpleList.json");
            List<AbstractEntry> readPacks = reader.read();
            assertEquals(3, readPacks.size());

            Pack pack1 = (Pack) readPacks.get(0);
            assertEquals("pack1", pack1.getName());
            assertEquals("", pack1.getDescription());
            assertEquals(1, pack1.size());

            Pack pack2 = (Pack) readPacks.get(1);
            assertEquals("pack2", pack2.getName());
            assertEquals("desc2", pack2.getDescription());
            assertEquals(1, pack2.size());

            Pack pack3 = (Pack) readPacks.get(2);
            assertEquals("pack3", pack3.getName());
            assertEquals("desc3", pack3.getDescription());
            assertEquals(1, pack3.size());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralList() {
        try {
            List<AbstractEntry> packs = new ArrayList<>();
            Pack pack1 = new Pack("pack1");
            PackList cat1 = new PackList("cat1");
            cat1.add(new PackItem("item1", "desc1", 500, 50.0, false, false));
            cat1.add(new PackItem("item2", "desc2", 100, 100, true, false));
            
            PackList cat2 = new PackList("cat2");
            cat2.add(new PackItem("item3", "desc3", 100, 100, false, true));

            pack1.add(cat1);
            pack1.add(cat2);
            
            Pack pack2 = new Pack("pack2", "desc2");
            PackList cat3 = new PackList("cat3", "desc3");
            cat3.add(new PackItem("item1", "desc1", 25, 0.0, true, true));
            pack2.add(cat3);

            packs.add(pack1);
            packs.add(pack2);

            JsonWriter writer = new JsonWriter(PATH + "testWriterGeneralList.json");
            writer.open();
            writer.write(packs);
            writer.close();

            JsonReader reader = new JsonReader(PATH + "testWriterGeneralList.json");
            List<AbstractEntry> readPacks = reader.read();
            assertEquals(2, packs.size());
            pack1 = (Pack) readPacks.get(0);
            
            assertEquals("cat1", pack1.get(1).getName());
            assertEquals("", pack1.get(1).getDescription());
            assertEquals("item1", ((PackList) pack1.get(1)).get(0).getName());
            assertEquals("desc1", ((PackList) pack1.get(1)).get(0).getDescription());
            assertEquals(500, ((PackList) pack1.get(1)).get(0).getTotalWeight());
            assertEquals(50.0, ((PackList) pack1.get(1)).get(0).getCost(), 0.01);
            assertFalse(((PackItem) ((PackList) pack1.get(1)).get(0)).isWorn());
            assertFalse(((PackItem) ((PackList) pack1.get(1)).get(0)).isConsumable());

            assertEquals("item2", ((PackList) pack1.get(1)).get(1).getName());
            assertEquals("desc2", ((PackList) pack1.get(1)).get(1).getDescription());
            assertEquals(100, ((PackList) pack1.get(1)).get(1).getTotalWeight());
            assertEquals(100, ((PackList) pack1.get(1)).get(1).getCost(), 0.01);
            assertTrue(((PackItem) ((PackList) pack1.get(1)).get(1)).isWorn());
            assertFalse(((PackItem) ((PackList) pack1.get(1)).get(1)).isConsumable());

            assertEquals("item3", ((PackList) pack1.get(2)).get(0).getName());
            assertEquals("desc3", ((PackList) pack1.get(2)).get(0).getDescription());
            assertEquals(100, ((PackList) pack1.get(2)).get(0).getTotalWeight());
            assertEquals(100, ((PackList) pack1.get(2)).get(0).getCost(), 0.01);
            assertFalse(((PackItem) ((PackList) pack1.get(2)).get(0)).isWorn());
            assertTrue(((PackItem) ((PackList) pack1.get(2)).get(0)).isConsumable());

            pack2 = (Pack) readPacks.get(1);
            assertEquals("cat3", pack2.get(1).getName());
            assertEquals("desc3", pack2.get(1).getDescription());
            assertEquals("item1", ((PackList) pack2.get(1)).get(0).getName());
            assertEquals("desc1", ((PackList) pack2.get(1)).get(0).getDescription());
            assertEquals(25, ((PackList) pack2.get(1)).get(0).getTotalWeight());
            assertEquals(0.0, ((PackList) pack2.get(1)).get(0).getCost(), 0.01);
            assertTrue(((PackItem) ((PackList) pack2.get(1)).get(0)).isWorn());
            assertTrue(((PackItem) ((PackList) pack2.get(1)).get(0)).isConsumable());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }
}
