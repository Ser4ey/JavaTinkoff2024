package edu.java.bot.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LocalDB implements UserLinkDB {
    private final Map<Long, List<String>> db = new HashMap<>();

    public List<String> getUserLinks(long userId) {
        db.computeIfAbsent(userId, k -> new LinkedList<>());
        return db.get(userId);
    }

    public void addUserLinks(long userId, String link) {
        db.computeIfAbsent(userId, k -> new LinkedList<>());
        db.get(userId).add(link);
    }

    public void delUserLinks(long userId, String link) {
        db.computeIfAbsent(userId, k -> new LinkedList<>());
        db.get(userId).remove(link);
    }

    public boolean checkUserLink(long userId, String link) {
        db.computeIfAbsent(userId, k -> new LinkedList<>());
        return db.get(userId).contains(link);
    }
}


