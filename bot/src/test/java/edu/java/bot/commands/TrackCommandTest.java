package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TrackCommandTest {
    @Mock
    SimpleBot bot;
    @Mock
    Update update;
    @Mock
    State state;
    @Spy
    TrackCommand trackCommand;

    @Test
    public void testExecuteNoStatus() {
        Mockito.doNothing().when(trackCommand).noStatus(any(), any(), any());

        Mockito.when(state.getStepName()).thenReturn(null);
        trackCommand.execute(bot, state, update);

        verify(trackCommand, Mockito.times(1)).noStatus(any(), any(), any());
        verify(trackCommand, Mockito.times(0)).statusWaitUrl(any(), any(), any());
    }

    @Test
    public void testExecuteStatusWaitUrl() {
        Mockito.doNothing().when(trackCommand).statusWaitUrl(any(), any(), any());

        Mockito.when(state.getStepName()).thenReturn(TrackCommand.STATUS_WAIT_URL);
        trackCommand.execute(bot, state, update);

        verify(trackCommand, Mockito.times(0)).noStatus(any(), any(), any());
        verify(trackCommand, Mockito.times(1)).statusWaitUrl(any(), any(), any());
    }

    @Test
    public void testNoStatus() {
        trackCommand.execute(bot, state, update);

        Mockito.verify(state, Mockito.times(1)).setStepName(Mockito.eq(TrackCommand.STATUS_WAIT_URL));
        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq("Введите ссылку для отслеживания:"));
    }

    @Test
    public void testStatusWaitUrl_UrlInDB() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(true);
        Mockito.when(bot.getMessageText(any())).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        trackCommand.statusWaitUrl(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq("Ссылка уже отслеживается"));
    }

    @Test
    public void testStatusWaitUrl_UrlNotInDB() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(bot.getMessageText(any())).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        trackCommand.statusWaitUrl(bot, state, update);

        Mockito.verify(bot, never()).sendMessage(anyLong(), Mockito.eq("Ссылка уже отслеживается"));
    }
}



