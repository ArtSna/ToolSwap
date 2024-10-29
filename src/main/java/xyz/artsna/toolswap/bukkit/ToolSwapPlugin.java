package xyz.artsna.toolswap.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.bukkit.data.repositories.SwapProfileRepository;
import xyz.artsna.toolswap.bukkit.handlers.SwapHandler;
import xyz.artsna.toolswap.bukkit.listeners.SwapListener;
import xyz.artsna.toolswap.core.command.CommandManager;
import xyz.artsna.toolswap.core.database.DatabaseManager;
import xyz.artsna.toolswap.core.inventory.ViewListener;
import xyz.artsna.toolswap.core.message.MessageController;

public final class ToolSwapPlugin extends JavaPlugin {

    public static ToolSwapPlugin instance;
    public ToolSwapPlugin() { instance = this; }


    private final DatabaseManager databaseManager = new DatabaseManager();
    private final CommandManager commandManager = new CommandManager(this);
    private final Config config = new Config(this);
    private final MessageController messenger = new MessageController(this);

    private final SwapHandler swapHandler = new SwapHandler(this);
    private final SwapProfileRepository swapProfileRepository = new SwapProfileRepository(this);

    @Override
    public void onEnable() {
        commandManager.configure();
        messenger.configure();

        ViewListener.Register(this);
        SwapListener.Register(this);
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public MessageController getMessenger() {
        return messenger;
    }

    public @NotNull Config getConfig() {
        return config;
    }

    public SwapProfileRepository getSwapProfileRepository() {
        return swapProfileRepository;
    }

    public SwapHandler getSwapHandler() {
        return swapHandler;
    }
}
