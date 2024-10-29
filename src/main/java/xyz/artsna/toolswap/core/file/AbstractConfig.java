package xyz.artsna.toolswap.core.file;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractConfig extends YamlFile {

    public AbstractConfig(@NotNull Plugin plugin, Boolean saveDefaultConfig) {
        this(plugin, "config.yml", saveDefaultConfig);
    }

    public AbstractConfig(@NotNull Plugin plugin, String fileName, Boolean saveDefaultConfig) {
        super(plugin.getDataFolder(), fileName);
        if(saveDefaultConfig) saveDefaultConfig(plugin);
    }

}