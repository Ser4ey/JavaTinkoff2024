package edu.java.bot.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class LocalDBFactoryTest {
    @Test
    public void testGetInstance() {
        LocalDB firstInstance = LocalDBFactory.getInstance();
        assertNotNull(firstInstance);

        LocalDB secondInstance = LocalDBFactory.getInstance();
        assertNotNull(secondInstance);

        assertSame(firstInstance, secondInstance);
    }
}
