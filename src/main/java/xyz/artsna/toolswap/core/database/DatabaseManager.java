package xyz.artsna.toolswap.core.database;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.core.exceptions.DatabaseException;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    public Map<String, Database> databases = new HashMap<>();

    public Database getDatabase(@NotNull String identifier) {
        return databases.get(identifier.toLowerCase());
    }

    public void register(@NotNull String identifier, Database database) {
        databases.put(identifier.toLowerCase(), database);
    }

    public void unregister(@NotNull String identifier) {
        databases.remove(identifier.toLowerCase());
    }

    public void debugAllQueries(boolean logQuery) {
        for (Database database : databases.values()) {
            database.debugQuery(logQuery);
        }
    }

    public void executeQuery(@NotNull String identifier, String query, Object... arguments) {
        Database database = getDatabase(identifier);
        if (database == null)
            throw new DatabaseException("Database not found for identifier " + identifier);
        database.executeQuery(query, arguments);
    }

    public DataResponse executeSelect(@NotNull String identifier, String query, Object... arguments) {
        Database database = getDatabase(identifier);
        if (database == null)
            throw new DatabaseException("Database not found for identifier " + identifier);
        return database.executeSelect(query, arguments);
    }
}