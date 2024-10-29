package xyz.artsna.toolswap.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.bukkit.data.repositories.SwapProfileRepository;
import xyz.artsna.toolswap.bukkit.handlers.SwapHandler;
import xyz.artsna.toolswap.bukkit.data.entities.SwapProfile;
import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;

public class SwapListener implements Listener {

    private static Config config;
    private static SwapProfileRepository repository;
    private static SwapHandler handler;

    public static void Register(@NotNull ToolSwapPlugin plugin) {
        config = plugin.getConfig();
        repository = plugin.getSwapProfileRepository();
        handler = plugin.getSwapHandler();

        Bukkit.getPluginManager().registerEvents(new SwapListener(), plugin);
    }


    @EventHandler
    public void onInteractEvent(@NotNull PlayerInteractEvent e) {
        if(e.getPlayer().getGameMode() == GameMode.SURVIVAL && e.getAction().isLeftClick()) {
            if(e.getClickedBlock() == null) return;

            SwapProfile profile;
            if(repository.exists(e.getPlayer().getUniqueId()))
                profile = repository.findById(e.getPlayer().getUniqueId());
            else
                profile = repository.create(new SwapProfile(e.getPlayer().getUniqueId(), config.getOptions().isDefaultSwapEnabled(), config.getOptions().isDefaultPreferSilkEnabled()));

            if(!profile.isSwapEnabled()) return;
            handler.handleTool(e.getPlayer(), e.getClickedBlock().getType(), profile.isPreferSilk());
        }
    }

    @EventHandler
    public void onJoinEvent(@NotNull PlayerJoinEvent e) {
        if(!repository.exists(e.getPlayer().getUniqueId()))
            repository.create(new SwapProfile(e.getPlayer().getUniqueId(), config.getOptions().isDefaultSwapEnabled(), config.getOptions().isDefaultPreferSilkEnabled()));
    }
}
