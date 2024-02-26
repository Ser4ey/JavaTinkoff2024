package edu.java.bot.states;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateManagerTest {

    @Test
    void testGetState() {
        State state1 = StateManager.getState(1L);
        State state2 = StateManager.getState(1L);

        assertNotNull(state1);
        assertNotNull(state2);

        assertSame(state1, state2);
    }

    @Test
    void testGetState2() {
        State state1 = StateManager.getState(10L);
        State state2 = StateManager.getState(1L);

        assertNotNull(state1);
        assertNotNull(state2);

        assertNotSame(state1, state2);
    }

    @Test
    void testGetState3() {
        State state1 = StateManager.getState(1L);
        State state2 = StateManager.getState(1L);

        state1.setStepName("123");
        assertEquals(state1.getStepName(), state2.getStepName());
    }

}
