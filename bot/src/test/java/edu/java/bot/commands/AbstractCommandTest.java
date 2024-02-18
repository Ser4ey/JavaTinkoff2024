package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractCommandTest {
    private final AbstractCommand testCommand = new AbstractCommand("test", "Test command") {
        @Override
        public void execute(SimpleBot bot, State state, Update update) { }
    };

    @Test
    public void testGetCommandName() {
        assertEquals("test", testCommand.getCommandName());
    }

    @Test
    public void testGetCommandDescription() {
        assertEquals("Test command", testCommand.getCommandDescription());
    }

    @Test
    public void testToString() {
        assertEquals("test - Test command", testCommand.toString());
    }
}
