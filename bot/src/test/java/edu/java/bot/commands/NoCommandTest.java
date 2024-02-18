package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

class NoCommandTest {

    @Test
    void testExecute() {
        SimpleBot bot = Mockito.mock(SimpleBot.class);
        State state = Mockito.mock(State.class);
        Update update = Mockito.mock(Update.class);

        NoCommand noCommand = Mockito.spy(new NoCommand());

        noCommand.execute(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), anyString());
    }
}
