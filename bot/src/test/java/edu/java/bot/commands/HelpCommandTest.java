package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


class HelpCommandTest {
    @Test
    void testExecute() {
        State state = Mockito.mock(State.class);
        ChatBotMessage chatMessage = Mockito.mock(ChatBotMessage.class);

        HelpCommand helpCommand = Mockito.spy(new HelpCommand());

        CommandAnswer commandAnswer = helpCommand.execute(chatMessage, state);

        assertNotNull(commandAnswer);
        assertNotNull(commandAnswer.getAnswerText());
        assertFalse(commandAnswer.isWithPagePreview());
    }
}
