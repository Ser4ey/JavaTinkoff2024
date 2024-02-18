package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class StartCommandTest {
    @Test
    void testExecute() {
        SimpleBot bot = Mockito.mock(SimpleBot.class);
        State state = Mockito.mock(State.class);
        Update update = Mockito.mock(Update.class);

        StartCommand startCommand = Mockito.spy(new StartCommand());

        startCommand.execute(bot, state, update);

        verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq(StartCommand.WELCOME_MESSAGE));
    }
}
