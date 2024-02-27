package edu.java.bot.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class AllCommands {
    private AllCommands() {}

    private static final Map<String, Command> COMMANDS = new LinkedHashMap<>();

    static {
        registerCommand(new StartCommand());
        registerCommand(new HelpCommand());
        registerCommand(new ListCommand());
        registerCommand(new TrackCommand());
        registerCommand(new UntrackCommand());
    }

    public static void registerCommand(Command command) {
        COMMANDS.put(command.getName(), command);
    }

    public static List<Command> getAllCommands() {
        return new ArrayList<>(COMMANDS.values());
    }

    public static Command getCommand(String commandName) {
        return COMMANDS.get(commandName);
    }

}
