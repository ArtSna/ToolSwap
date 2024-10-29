package xyz.artsna.toolswap.core.message.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.core.message.data.constraints.Locale;
import xyz.artsna.toolswap.core.message.handlers.LocaleHandler;

public class LocaleListener implements Listener {

    private static LocaleHandler handler;

    public static void Register(Plugin plugin, LocaleHandler _handler) {
        handler = _handler;
        Bukkit.getPluginManager().registerEvents(new LocaleListener(), plugin);
    }

    @EventHandler
    public void onLocaleChange(@NotNull PlayerLocaleChangeEvent event) {
        handler.handleLocaleChange(event.getPlayer(), Locale.valueOf(event.locale().toString().toLowerCase()));
    }

}
