package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessageInterface;
import edu.java.bot.states.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class TrackCommandTest {
    private State state;
    private ChatBotMessageInterface chatMessage;
    private TrackCommand trackCommand;

    @BeforeEach
    public void init() {
        state = Mockito.mock(State.class);
        chatMessage = Mockito.mock(ChatBotMessageInterface.class);
        trackCommand = Mockito.spy(new TrackCommand());
    }

    @Test
    public void testExecuteNoStatus() {
        Mockito.doAnswer(invocation -> null).when(trackCommand).noStatus(any());
        Mockito.when(state.getStepName()).thenReturn("0");

        trackCommand.execute(chatMessage, state);

        verify(trackCommand, Mockito.times(1)).noStatus(any());
        verify(trackCommand, never()).statusWaitUrl(any(), any());
    }

    @Test
    public void testExecuteStatusWaitUrl() {
        Mockito.doAnswer(invocation -> null).when(trackCommand).statusWaitUrl(any(), any());
        Mockito.when(state.getStepName()).thenReturn(TrackCommand.STATUS_WAIT_URL);

        trackCommand.execute(chatMessage, state);

        verify(trackCommand, never()).noStatus(any());
        verify(trackCommand, Mockito.times(1)).statusWaitUrl(any(), any());
    }

    @Test
    public void testNoStatus() {
        CommandAnswer commandAnswer = trackCommand.execute(chatMessage, state);

        verify(state, Mockito.times(1)).setStepName(Mockito.eq(TrackCommand.STATUS_WAIT_URL));
        assertEquals(
            new CommandAnswer("Введите ссылку для отслеживания:", false),
            commandAnswer
        );
    }

    @Test
    public void testStatusWaitUrl_UrlInDB() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(true);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        CommandAnswer commandAnswer = trackCommand.statusWaitUrl(chatMessage, state);

        assertEquals(
            new CommandAnswer("Ссылка уже отслеживается", false),
            commandAnswer
        );

        verify(state, Mockito.times(1)).clear();
    }

    @Test
    public void testStatusWaitUrl_UrlNotInDB() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        CommandAnswer commandAnswer = trackCommand.statusWaitUrl(chatMessage, state);
        assertNotEquals(commandAnswer.getAnswerText(), "Ссылка уже отслеживается");

        verify(state, Mockito.times(1)).clear();
    }

    @Test
    public void testStatusWaitUrl_UrlValid() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        CommandAnswer commandAnswer = trackCommand.statusWaitUrl(chatMessage, state);
        assertNotEquals(commandAnswer.getAnswerText(), "Ссылка не валидна");

        verify(state, Mockito.times(1)).clear();

    }

    @Test
    public void testStatusWaitUrl_UrlNotValid() {
        Mockito.when(chatMessage.getMessageText()).thenReturn("123");

        CommandAnswer commandAnswer = trackCommand.statusWaitUrl(chatMessage, state);

        assertEquals(
            new CommandAnswer("Ссылка не валидна", false),
            commandAnswer
        );

        verify(state, Mockito.times(1)).clear();
    }

    @Test
    public void testStatusWaitUrl_UrlTrack() {
        Mockito.when(trackCommand.checkUrlAlreadyInDB(anyLong(), anyString())).thenReturn(false);
        Mockito.when(chatMessage.getMessageText()).thenReturn("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1");

        CommandAnswer commandAnswer = trackCommand.statusWaitUrl(chatMessage, state);

        assertEquals(
            new CommandAnswer("Ссылка успешно добавлена!", false),
            commandAnswer
        );

        verify(state, Mockito.times(1)).clear();
    }
}

