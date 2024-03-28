package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import edu.java.bot.service.ScrapperService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

public class ListCommandTest {
    @Test
    public void testExecuteWithNoLinks() {
        State state = Mockito.mock(State.class);
        ChatBotMessage chatMessage = Mockito.mock(ChatBotMessage.class);
        ScrapperService scrapperService = Mockito.mock(ScrapperService.class);

        ListCommand listCommand = Mockito.spy(new ListCommand(scrapperService));

        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.emptyList());

        CommandAnswer commandAnswer = listCommand.execute(chatMessage, state);
        assertNotNull(commandAnswer);

        assertEquals(
            commandAnswer,
            new CommandAnswer(ListCommand.NO_LINKS, false)
        );
    }

    @Test
    public void testExecuteWithOneLink() {
        State state = Mockito.mock(State.class);
        ChatBotMessage chatMessage = Mockito.mock(ChatBotMessage.class);
        ScrapperService scrapperService = Mockito.mock(ScrapperService.class);
        ListCommand listCommand = Mockito.spy(new ListCommand(scrapperService));

        Mockito.when(listCommand.getUserLinks(anyLong())).thenReturn(Collections.singletonList("https://example.com"));

        CommandAnswer commandAnswer = listCommand.execute(chatMessage, state);
        assertNotNull(commandAnswer);

        assertNotEquals(commandAnswer.getAnswerText(), ListCommand.NO_LINKS);
        assertFalse(commandAnswer.isWithPagePreview());
    }

    @Test
    public void testExecuteWithManyLinks() {
        State state = Mockito.mock(State.class);
        ChatBotMessage chatMessage = Mockito.mock(ChatBotMessage.class);
        ScrapperService scrapperService = Mockito.mock(ScrapperService.class);
        ListCommand listCommand = Mockito.spy(new ListCommand(scrapperService));

        Mockito.when(listCommand.getUserLinks(anyLong())).
            thenReturn(List.of("https://example1.com", "https://example2.com", "https://example3.com"));

        CommandAnswer commandAnswer = listCommand.execute(chatMessage, state);
        assertNotNull(commandAnswer);

        assertNotEquals(commandAnswer.getAnswerText(), ListCommand.NO_LINKS);
        assertFalse(commandAnswer.isWithPagePreview());
    }
}

