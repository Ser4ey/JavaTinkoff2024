package edu.java.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CommandTest {
    @Test
    void testIsCommand() {
        assertThat(Command.isCommand("/rrr")).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/start", "/command", "/some"})
    void testIsCommandTrueTest(String text) {
        assertTrue(Command.isCommand(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a1", "/b2", "/start ", " /start", "/sta rt", "123", "ddd", "//dfg", "/frwds/fe",
        "/fds/gfds"})
    void testIsCommandFalseTest(String text) {
        assertFalse(Command.isCommand(text));
    }
}
