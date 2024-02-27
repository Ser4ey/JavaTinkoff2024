package edu.java.bot.states;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;


public class StateManager {
    private StateManager() {}

    private static final Map<Long, State> USERS_STATES = new HashMap<>();

    @NonNull
    public static State getState(Long userId) {
        State userState = USERS_STATES.get(userId);
        if (userState == null) {
            userState = new State();
            USERS_STATES.put(userId, userState);
        }
        return userState;
    }

}
