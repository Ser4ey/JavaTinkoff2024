package edu.java.bot.states;

import edu.java.bot.commands.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.util.HashMap;


class StateTest {
    private State state;

    @BeforeEach
    public void setUp() {
        state = new State();
    }

    @Test
    public void testClear() {
        Command mockCommand = Mockito.mock(Command.class);

        assertNull(state.getCommand());
        assertNull(state.getStepName());
        assertNull(state.get("key"));

        state.setCommand(mockCommand);
        state.setStepName("Step1");
        state.update("key", "value");

        assertNotNull(state.getCommand());
        assertNotNull(state.getStepName());
        assertNotNull(state.get("key"));

        state.clear();

        assertNull(state.getCommand());
        assertNull(state.getStepName());
        assertNull(state.get("key"));
    }

    @Test
    public void testClearDict() throws NoSuchFieldException, IllegalAccessException {
        Field field = State.class.getDeclaredField("dict");
        field.setAccessible(true);
        HashMap<String, String> dict = (HashMap<String, String>) field.get(state);

        assertNull(dict.get("key_1"));

        state.update("key_1", "value_1");

        assertEquals(dict.get("key_1"), "value_1");
    }

    @Test
    public void testGetAndUpdate() {
        String key = "testKey";
        String value = "testValue";
        state.update(key, value);

        assertEquals(value, state.get(key));
    }

    @Test
    public void testUpdateWithNullKey() {
        assertThrows(NullPointerException.class, () -> state.update(null, "value"));
    }

    @Test
    public void testUpdateWithNullValue() {
        assertThrows(NullPointerException.class, () -> state.update("key", null));
    }

    @Test
    public void testGetWithNullKey() {
        assertThrows(NullPointerException.class, () -> state.get(null));
    }

}
