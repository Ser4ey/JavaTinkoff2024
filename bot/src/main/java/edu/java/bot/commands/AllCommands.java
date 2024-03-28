package edu.java.bot.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllCommands {
    private final Map<String, Command> commandsMap = new LinkedHashMap<>();

    public AllCommands(@Autowired List<Command> commands) {
        for (Command command : commands) {
            registerCommand(command);
        }
    }

    public void registerCommand(Command command) {
        commandsMap.put(command.getName(), command);
    }

    public List<Command> getAllCommands() {
        return new ArrayList<>(commandsMap.values());
    }

    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

}
