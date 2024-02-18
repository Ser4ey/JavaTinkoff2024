package edu.java.bot.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredCommandTest {
    @Test
    public void testStartCommand() {
        assertEquals(StartCommand.class, RegisteredCommand.START_COMMAND.getCommand().getClass());
    }

    @Test
    public void testHelpCommand() {
        assertEquals(HelpCommand.class, RegisteredCommand.HELP_COMMAND.getCommand().getClass());
    }

    @Test
    public void testListCommand() {
        assertEquals(ListCommand.class, RegisteredCommand.LIST_COMMAND.getCommand().getClass());
    }

    @Test
    public void testTrackCommand() {
        assertEquals(TrackCommand.class, RegisteredCommand.TRACK_COMMAND.getCommand().getClass());
    }

    @Test
    public void testUntrackCommand() {
        assertEquals(UntrackCommand.class, RegisteredCommand.UNTRACK_COMMAND.getCommand().getClass());
    }

    @Test
    public void testToString() {
        String string = String.format("RegisteredCommands{command=%s}", RegisteredCommand.START_COMMAND.getCommand().toString());
        assertEquals(string, RegisteredCommand.START_COMMAND.toString());
    }

}
