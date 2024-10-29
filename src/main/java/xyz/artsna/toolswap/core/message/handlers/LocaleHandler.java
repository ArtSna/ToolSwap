package xyz.artsna.toolswap.core.message.handlers;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.core.message.data.constraints.Locale;
import xyz.artsna.toolswap.core.message.data.entities.LocaleProfile;
import xyz.artsna.toolswap.core.message.data.repositories.LocaleProfileRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LocaleHandler {

    private final LocaleProfileRepository repository;

    private final Map<UUID, LocaleProfile> cached = new HashMap<>();

    public LocaleHandler(LocaleProfileRepository repository) {
        this.repository = repository;
    }

    public LocaleProfile handleLocaleChange(@NotNull Player player, Locale newLocale) {
        LocaleProfile profile;

        if(repository.exists(player.getUniqueId())){
            profile = repository.findById(player.getUniqueId());
            profile.setLocale(newLocale);
            repository.update(profile);
        }else {
            profile = repository.create(new LocaleProfile(player.getUniqueId(), newLocale));
        }

        cached.put(player.getUniqueId(), profile);
        return profile;
    }

    public Locale getLocale(@NotNull Player player) {
        LocaleProfile profile = cached.get(player.getUniqueId());
        return profile == null ? handleSetupPlayer(player) : profile.getLocale();
    }

    private Locale handleSetupPlayer(@NotNull Player player) {
        return handleLocaleChange(player, Locale.valueOf(player.locale().toString().toLowerCase())).getLocale();
    }
}
