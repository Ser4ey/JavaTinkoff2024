package edu.java.bot.commands;

import lombok.Getter;

@Getter
public enum RegisteredCommand {
    START_COMMAND(new StartCommand()),
    HELP_COMMAND(new HelpCommand()),
    LIST_COMMAND(new ListCommand()),
    TRACK_COMMAND(new TrackCommand()),
    UNTRACK_COMMAND(new UntrackCommand());

    private final AbstractCommand command;

    RegisteredCommand(AbstractCommand command) {
        this.command = command;
    }

    @Override public String toString() {
        return String.format("RegisteredCommands{command=%s}", command);
    }

}
