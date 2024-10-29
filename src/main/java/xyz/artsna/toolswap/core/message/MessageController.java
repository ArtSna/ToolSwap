package xyz.artsna.toolswap.core.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;
import xyz.artsna.toolswap.core.message.data.constraints.Locale;
import xyz.artsna.toolswap.core.message.data.entities.Message;
import xyz.artsna.toolswap.core.message.data.repositories.LocaleProfileRepository;
import xyz.artsna.toolswap.core.message.exceptions.InvalidMessageValueTypeException;
import xyz.artsna.toolswap.core.message.exceptions.MessageNotFoundException;
import xyz.artsna.toolswap.core.message.handlers.LocaleHandler;
import xyz.artsna.toolswap.core.message.listeners.LocaleListener;

import java.util.Objects;

public class MessageController {

    private final ToolSwapPlugin plugin;

    private final LocaleHandler localeHandler;

    public MessageController(ToolSwapPlugin plugin) {
        this.plugin = plugin;

        this.localeHandler = new LocaleHandler(new LocaleProfileRepository(plugin));
    }

    public void configure() {
        LocaleListener.Register(plugin, localeHandler);

        saveDefaultMessages(Locale.en_us);
        saveDefaultMessages(Locale.pt_br);
    }

    public LocaleHandler getLocaleHandler() {
        return localeHandler;
    }

    public void send(String key, CommandSender sender) {
        var locale = plugin.getConfig().getMessengerConfig().getFallbackLocale();

        if(sender instanceof Player) {
            locale = localeHandler.getLocale((Player) sender);
        }

        sender.sendMessage(getMessage(key, locale).getValues());
    }

    public void send(String key, @NotNull Player player) {
        player.sendMessage(getMessage(key, player).getValues());
    }

    public Message getMessage(String key, Player player) {
        return getMessage(key, localeHandler.getLocale(player));
    }

    public Message getMessage(String key, Locale locale) {
        var file = plugin.getConfig().getMessengerConfig().getMessagesFile(locale);

        if(!file.contains(key) && !locale.equals(plugin.getConfig().getMessengerConfig().getFallbackLocale()))
            return getMessage(key, plugin.getConfig().getMessengerConfig().getFallbackLocale());
        else if(!file.contains(key) && locale.equals(plugin.getConfig().getMessengerConfig().getFallbackLocale()))
            throw new MessageNotFoundException("Not found message for key: '" + key + "' in file: '" + file.getFilePath() + "'");

        if(file.isString(key))
            return new Message(key, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(file.getString(key))));
        else if(file.isList(key))
            return new Message(key, file.getStringList(key).stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).toList());
        else
            throw new InvalidMessageValueTypeException("The value must be a string list or a single string");
    }

    private void saveDefaultMessages(@NotNull Locale locale) {
        var dirPath = Objects.requireNonNull(plugin.getConfig().getString("messenger.messages-directory"))
                .replaceAll("%locale%", locale.toString());

        plugin.getConfig().getMessengerConfig().getMessagesFile(locale).saveResource(plugin, dirPath, false);
    }
}