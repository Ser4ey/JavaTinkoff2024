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
public class UntrackCommandTest {
    @Mock
    SimpleBot bot;
    @Mock
    Update update;
    @Mock
    State state;
    @Spy
    UntrackCommand untrackCommand;

    @Test
    public void testExecuteNoStatus() {
        Mockito.doNothing().when(untrackCommand).noStatus(any(), any(), any());

        Mockito.when(state.getStepName()).thenReturn(null);
        untrackCommand.execute(bot, state, update);

        verify(untrackCommand, Mockito.times(1)).noStatus(any(), any(), any());
        verify(untrackCommand, Mockito.times(0)).statusWaitUrl(any(), any(), any());
    }

    @Test
    public void testExecuteStatusWaitUrl() {
        Mockito.doNothing().when(untrackCommand).statusWaitUrl(any(), any(), any());

        Mockito.when(state.getStepName()).thenReturn(UntrackCommand.STATUS_WAIT_URL);
        untrackCommand.execute(bot, state, update);

        verify(untrackCommand, Mockito.times(0)).noStatus(any(), any(), any());
        verify(untrackCommand, Mockito.times(1)).statusWaitUrl(any(), any(), any());
    }

    @Test
    public void testNoStatus() {
        untrackCommand.execute(bot, state, update);

        Mockito.verify(state, Mockito.times(1)).setStepName(Mockito.eq(TrackCommand.STATUS_WAIT_URL));
        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq("Введите ссылку для удаления:"));
    }

    @Test
    public void testStatusWaitUrl_UrlInDB() {
        Mockito.when(untrackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(true);
        Mockito.when(bot.getMessageText(any())).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");
        Mockito.doNothing().when(untrackCommand).delUrlFromDB(any(), any());

        untrackCommand.statusWaitUrl(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq("Ссылка успешно удалена!"));
        Mockito.verify(state, Mockito.times(1)).clear();
    }

    @Test
    public void testStatusWaitUrl_UrlNotInDB() {
        Mockito.when(untrackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(bot.getMessageText(any())).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        untrackCommand.statusWaitUrl(bot, state, update);

        Mockito.verify(bot, Mockito.times(1)).sendMessage(anyLong(), Mockito.eq("Ссылка не отслеживается!"));
        Mockito.verify(state, Mockito.times(1)).clear();

    }


}



