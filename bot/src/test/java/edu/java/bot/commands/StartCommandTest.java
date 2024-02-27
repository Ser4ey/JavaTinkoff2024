package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StartCommandTest {
    @Test
    void testExecute() {
        State state = Mockito.mock(State.class);
        ChatBotMessage chatMessage = Mockito.mock(ChatBotMessage.class);

        StartCommand startCommand = Mockito.spy(new StartCommand());

        CommandAnswer commandAnswer = startCommand.execute(chatMessage, state);
        assertNotNull(commandAnswer);

        assertEquals(
            commandAnswer,
            new CommandAnswer(StartCommand.WELCOME_MESSAGE, true)
        );

    }
}
