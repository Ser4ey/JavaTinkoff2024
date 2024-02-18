package edu.java.bot.commands;

import lombok.Getter;

@Getter
public abstract class AbstractCommand implements Command {
    private final String commandName;
    private final String commandDescription;

    public AbstractCommand(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    @Override public String toString() {
        return String.format("%s - %s", commandName, commandDescription);
    }
}
