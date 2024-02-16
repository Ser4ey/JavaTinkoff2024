package edu.java.bot.states;

import edu.java.bot.commands.Command;
import jakarta.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class State {
    private final Map<String, String> dict = new HashMap<>();
    @Getter @Setter private Command command;
    @Getter @Setter private String stepName;

    public void clear() {
        setCommand(null);
        setStepName(null);
        dict.clear();
    }

    public void update(@Nonnull String key, @Nonnull String value) {
        if (key == null || value == null) {
            throw new NullPointerException("key/value can not be null");
        }
        dict.put(key, value);
    }

    public String get(@Nonnull String key) {
        if (key == null) {
            throw new NullPointerException("key can not be null");
        }
        return dict.get(key);
    }

}


