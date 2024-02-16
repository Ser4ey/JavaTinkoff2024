package edu.java.bot.states;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;


public class StateManager {
    private final Map<Long, State> usersStates = new HashMap<>();

    @NonNull
    public State getState(Long userId) {
        State userState = usersStates.get(userId);
        if (userState == null) {
            userState = new State();
            usersStates.put(userId, userState);
        }
        return userState;
    }

}
