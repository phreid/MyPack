package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackTest {

    @Test
    public void testConstructorName() {
        Pack pack = new Pack("test");

        assertEquals(1, pack.size());
        AbstractEntry defaultCategory = pack.get(0);
        assertEquals(Pack.DEFAULT_CATEGORY_NAME, defaultCategory.getName());
    }

    @Test
    public void testConstructorNameDesc() {
        Pack pack = new Pack("test", "desc");

        assertEquals(1, pack.size());
        AbstractEntry defaultCategory = pack.get(0);
        assertEquals(Pack.DEFAULT_CATEGORY_NAME, defaultCategory.getName());

    }
}