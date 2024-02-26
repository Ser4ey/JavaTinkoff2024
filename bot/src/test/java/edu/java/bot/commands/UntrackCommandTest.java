package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class UntrackCommandTest {
    private State state;
    private ChatBotMessage chatMessage;
    private UntrackCommand untrackCommand;

    @BeforeEach
    public void init() {
        state = Mockito.mock(State.class);
        chatMessage = Mockito.mock(ChatBotMessage.class);
        untrackCommand = Mockito.spy(new UntrackCommand());
    }

    @Test
    public void testExecuteNoStatus() {
        Mockito.doAnswer(invocation -> null).when(untrackCommand).noStatus(any());

        Mockito.when(state.getStepName()).thenReturn(null);
        untrackCommand.execute(chatMessage, state);

        verify(untrackCommand, Mockito.times(1)).noStatus(any());
        verify(untrackCommand, never()).statusWaitUrl(any(), any());
    }

    @Test
    public void testExecuteStatusWaitUrl() {
        Mockito.doAnswer(invocation -> null).when(untrackCommand).statusWaitUrl(any(), any());

        Mockito.when(state.getStepName()).thenReturn(UntrackCommand.STATUS_WAIT_URL);
        untrackCommand.execute(chatMessage, state);

        verify(untrackCommand, never()).noStatus(any());
        verify(untrackCommand, Mockito.times(1)).statusWaitUrl(any(), any());
    }

    @Test
    public void testNoStatus() {
        CommandAnswer commandAnswer = untrackCommand.execute(chatMessage, state);

        assertEquals(
            new CommandAnswer("Введите ссылку для удаления:", false),
            commandAnswer
        );

        Mockito.verify(state, Mockito.times(1)).setStepName(Mockito.eq(TrackCommand.STATUS_WAIT_URL));
    }

    @Test
    public void testStatusWaitUrl_UrlInDB() {
        Mockito.when(untrackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(true);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");
        Mockito.doNothing().when(untrackCommand).delUrlFromDB(any(), any());

        CommandAnswer commandAnswer = untrackCommand.statusWaitUrl(chatMessage, state);

        assertEquals(
            new CommandAnswer("Ссылка успешно удалена!", false),
            commandAnswer
        );

        Mockito.verify(state, Mockito.times(1)).clear();
    }

    @Test
    public void testStatusWaitUrl_UrlNotInDB() {
        Mockito.when(untrackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        CommandAnswer commandAnswer = untrackCommand.statusWaitUrl(chatMessage, state);

        assertEquals(
            new CommandAnswer("Ссылка не отслеживается!", false),
            commandAnswer
        );

        Mockito.verify(state, Mockito.times(1)).clear();
    }
}

