package xyz.artsna.toolswap.bukkit.data.config;

import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;
import xyz.artsna.toolswap.bukkit.data.config.components.*;
import xyz.artsna.toolswap.core.file.AbstractConfig;

public class Config extends AbstractConfig {

    private final ToolSwapPlugin plugin;

    private final OptionConfig options = new OptionConfig(this);
    private final InteractionConfig interactions = new InteractionConfig(this);
    private final PermissionConfig permissions = new PermissionConfig(this);
    private  final MessengerConfig messenger = new MessengerConfig(this);

    public Config(ToolSwapPlugin plugin) {
        super(plugin, true);

        this.plugin = plugin;

        DatabaseConfig databases = new DatabaseConfig(this);

        databases.configure();
        options.configure();
        interactions.configure();
        permissions.configure();
        messenger.configure();

        options().copyDefaults(true);
        save();
    }

    public ToolSwapPlugin getPlugin() {
        return plugin;
    }

    public boolean isDebug() {
        return contains("debug") && getBoolean("debug");
    }

    public OptionConfig getOptions() {
        return options;
    }

    public PermissionConfig getPermissions() {
        return permissions;
    }

    public InteractionConfig getInteractions() {
        return interactions;
    }

    public MessengerConfig getMessengerConfig() {
        return messenger;
    }
}