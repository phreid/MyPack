package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AbstractEntryTest {
    @Test
    public void testConstructorName() {
        AbstractEntry entry = new PackItem("test");
        assertEquals("test", entry.getName());
        assertEquals("", entry.getDescription());
        assertFalse(entry.hasChildren());
    }

    @Test
    public void testConstructorNameDesc() {
        AbstractEntry entry = new PackItem("test", "desc");
        assertEquals("test", entry.getName());
        assertEquals("desc", entry.getDescription());
        assertFalse(entry.hasChildren());

    }

    @Test
    public void testSetName() {
        AbstractEntry entry = new PackItem("test");
        entry.setName("new");
        assertEquals("new", entry.getName());
    }
}
