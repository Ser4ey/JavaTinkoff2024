package edu.java.bot.db;

public class LocalDBFactory {
    private static LocalDB instance;

    private LocalDBFactory() {}

    public static synchronized LocalDB getInstance() {
        if (instance == null) {
            instance = new LocalDB();
        }
        return instance;
    }
}
