package xyz.artsna.toolswap.core.message.data.repositories;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;
import xyz.artsna.toolswap.core.database.Database;
import xyz.artsna.toolswap.core.message.data.constraints.Locale;
import xyz.artsna.toolswap.core.message.data.entities.LocaleProfile;

import java.util.UUID;

public class LocaleProfileRepository {

    private final Database database;

    public LocaleProfileRepository(@NotNull ToolSwapPlugin plugin) {
        this.database = plugin.getDatabaseManager().getDatabase("lang");

        database.executeQuery("CREATE TABLE IF NOT EXISTS locale_profiles (" +
                "id TEXT PRIMARY KEY," +
                "locale TEXT NOT NULL" +
                ");");
    }

    public LocaleProfile findById(UUID id) {
        var res = database.executeSelect("SELECT * FROM locale_profiles WHERE id=?", id);
        return res.stream().map(row -> new LocaleProfile(id, Locale.valueOf(row.getString("locale").toLowerCase()))).findFirst().orElse(null);
    }

    public boolean exists(UUID id) {
        return findById(id) != null;
    }

    public LocaleProfile create(@NotNull LocaleProfile profile) {
        database.executeQuery("INSERT INTO locale_profiles (id, locale) VALUES (?,?)", profile.getId(), profile.getLocale().toString());
        return profile;
    }

    public void update(@NotNull LocaleProfile profile) {
        database.executeQuery("UPDATE locale_profiles SET locale=? WHERE id=?", profile.getLocale().toString(), profile.getId());
    }

    public void delete(UUID id) {
        database.executeQuery("DELETE FROM locale_profiles WHERE id=?", id);
    }
}