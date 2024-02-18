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
        verify(trackCommand, Mockito.times(1)).statusWaitUrl(any(), any(), any());    }
}



