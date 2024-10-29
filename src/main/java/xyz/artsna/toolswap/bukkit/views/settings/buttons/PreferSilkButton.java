package xyz.artsna.toolswap.bukkit.views.settings.buttons;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.bukkit.data.entities.SwapProfile;
import xyz.artsna.toolswap.bukkit.data.repositories.SwapProfileRepository;
import xyz.artsna.toolswap.core.inventory.Button;
import xyz.artsna.toolswap.core.message.MessageController;

import java.util.List;

public class PreferSilkButton extends Button {

    public PreferSilkButton(Player player, @NotNull SwapProfile profile, @NotNull MessageController messenger, Config config, SwapProfileRepository repository) {
        super(Material.BOOK);
        var preferSilkToggleMeta = getItemMeta();
        preferSilkToggleMeta.displayName(Component.text(messenger.getMessage("views.settings.buttons.prefer-silk-toggle.display-name", player).getFirst()));
        preferSilkToggleMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(profile.isPreferSilk())
            preferSilkToggleMeta.addEnchant(Enchantment.UNBREAKING, 1, true);

        String status =  profile.isPreferSilk() ? "enabled" : "disabled";

        var lore = messenger.getMessage("views.settings.buttons.prefer-silk-toggle.lore", player);
        lore.replace("%status%", messenger.getMessage("views.settings.buttons.prefer-silk-toggle.status." + status, player).getFirst());

        for(String line : lore.getValues())
            preferSilkToggleMeta.lore(List.of(Component.text(line)));

        setItemMeta(preferSilkToggleMeta);

        onClick((e, c) -> {
            if(config.getPermissions().getPreferSilkPermission() != null && !config.getPermissions().getPreferSilkPermission().equalsIgnoreCase("none") && !c.getPlayer().hasPermission(config.getPermissions().getPreferSilkPermission())){
                messenger.send("errors.no-permission-feature", c.getPlayer());
                return;
            }

            profile.setPreferSilk(!profile.isPreferSilk());
            repository.update(profile);

            if(profile.isPreferSilk()) messenger.send("views.settings.buttons.prefer-silk-toggle.actions.enabled", c.getPlayer());
            else messenger.send("views.settings.buttons.prefer-silk-toggle.actions.disabled", c.getPlayer());
            c.getCurrentWindow().update();
        });
    }
}