package xyz.artsna.toolswap.bukkit.data.config.components;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.core.file.YamlFile;
import xyz.artsna.toolswap.core.message.data.constraints.Locale;

import java.util.Objects;

public class MessengerConfig {

    private final Config config;

    public MessengerConfig(Config config) {
        this.config = config;
    }

    public void configure() {
        config.addDefault("messenger.fallback-locale", "en_us");
        config.addDefault("messenger.messages-directory", "lang/messages-%locale%.yml");
    }

    public Locale getFallbackLocale() {
        return Locale.valueOf(Objects.requireNonNull(config.getString("messenger.fallback-locale")).toLowerCase());
    }

    public YamlFile getMessagesFile(@NotNull Locale locale) {
        var dirPath = Objects.requireNonNull(config.getString("messenger.messages-directory"))
                .replaceAll("%locale%", locale.toString());
        return new YamlFile(config.getPlugin().getDataFolder(), dirPath);
    }
}
