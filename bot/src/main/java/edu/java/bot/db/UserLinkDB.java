package edu.java.bot.db;

import java.util.List;

public interface UserLinkDB {
    List<String> getUserLinks(long userId);

    void addUserLinks(long userId, String link);

    void delUserLinks(long userId, String link);

    boolean checkUserLinks(long userId, String link);
}
