package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.anyLong;

class StartCommandTest {
    @Test
    void testExecute() {
        SimpleBot bot = Mockito.mock(SimpleBot.class);
        State state = Mockito.mock(State.class);
        Update update = Mockito.mock(Update.class);

        StartCommand startCommand = Mockito.spy(new StartCommand());

        startCommand.execute(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq(StartCommand.WELCOME_MESSAGE));
    }
}
