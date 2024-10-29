package xyz.artsna.toolswap.bukkit.data.repositories;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;
import xyz.artsna.toolswap.bukkit.data.entities.SwapProfile;
import xyz.artsna.toolswap.core.database.Database;

import java.util.UUID;

public class SwapProfileRepository {

    private final Database database;

    public SwapProfileRepository(@NotNull ToolSwapPlugin plugin) {
        this.database = plugin.getDatabaseManager().getDatabase("main");

        database.executeQuery("CREATE TABLE IF NOT EXISTS swap_profiles (" +
                "id TEXT PRIMARY KEY," +
                "swap_enabled INTEGER NOT NULL," +
                "prefer_silk INTEGER NOT NULL" +
                ");");
    }

    public SwapProfile findById(UUID id) {
        var res = database.executeSelect("SELECT * FROM swap_profiles WHERE id=?", id);
        return res.stream().map(row -> new SwapProfile(id, row.getBoolean("swap_enabled"), row.getBoolean("prefer_silk"))).findFirst().orElse(null);
    }

    public boolean exists(UUID id) {
        return findById(id) != null;
    }

    public SwapProfile create(@NotNull SwapProfile profile) {
        database.executeQuery("INSERT INTO swap_profiles (id, swap_enabled, prefer_silk) VALUES (?,?,?)", profile.getId(), profile.isSwapEnabled(), profile.isPreferSilk());
        return profile;
    }

    public void update(@NotNull SwapProfile profile) {
        database.executeQuery("UPDATE swap_profiles SET swap_enabled=?, prefer_silk=? WHERE id=?", profile.isSwapEnabled(), profile.isPreferSilk(), profile.getId());
    }

    public void delete(UUID id) {
        database.executeQuery("DELETE FROM swap_profiles WHERE id=?", id);
    }
}