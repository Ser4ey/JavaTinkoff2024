package edu.java.bot.commands;

import lombok.Getter;

@Getter
public abstract class AbstractCommand implements Command {
    protected String commandName;
    protected String commandDescription;

    public AbstractCommand(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

}
