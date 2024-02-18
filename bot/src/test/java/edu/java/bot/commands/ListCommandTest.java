package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListCommandTest {
    @Mock
    SimpleBot bot;
    @Mock
    Update update;
    @Mock
    State state;
    @Spy
    ListCommand listCommand;

    @Test
    public void testExecuteWithNoLinks() {
        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.emptyList());
        listCommand.execute(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq(ListCommand.NO_LINKS));
    }

    @Test
    public void testExecuteWithOneLink() {
        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.singletonList("https://example.com"));
        listCommand.execute(bot, state, update);

        verify(bot, never()).sendMessage(anyLong(), Mockito.eq(ListCommand.NO_LINKS));
        verify(bot, Mockito.times(1)).sendMessage(anyLong(), anyString());
    }

    @Test
    public void testExecuteWithManyLinks() {
        Mockito.when(listCommand.getUserLinks(anyLong())).
            thenReturn(List.of("https://example1.com", "https://example2.com", "https://example3.com"));
        listCommand.execute(bot, state, update);

        verify(bot, never()).sendMessage(anyLong(), Mockito.eq(ListCommand.NO_LINKS));
        verify(bot, Mockito.times(1)).sendMessage(anyLong(), anyString());
    }

    @Test
    public void testSendMessageIsCalledOnceAndTwice() {
        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.emptyList());
        listCommand.execute(bot, state, update);
        verify(bot, times(1)).sendMessage(anyLong(), anyString());

        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.singletonList("https://example.com"));
        listCommand.execute(bot, state, update);
        verify(bot, times(2)).sendMessage(anyLong(), anyString());
    }
}

