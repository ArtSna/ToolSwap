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

public class ToolSwapButton extends Button {

    public ToolSwapButton(Player player, @NotNull SwapProfile profile, @NotNull MessageController messenger, Config config, SwapProfileRepository repository) {
        super(Material.IRON_PICKAXE);

        var toolSwapToggleMeta = getItemMeta();
        toolSwapToggleMeta.displayName(Component.text(messenger.getMessage("views.settings.buttons.tool-swap-toggle.display-name", player).getFirst()));
        toolSwapToggleMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        toolSwapToggleMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        if(profile.isSwapEnabled())
            toolSwapToggleMeta.addEnchant(Enchantment.UNBREAKING, 1, true);

        String status =  profile.isSwapEnabled() ? "enabled" : "disabled";

        var lore = messenger.getMessage("views.settings.buttons.tool-swap-toggle.lore", player);
        lore.replace("%status%", messenger.getMessage("views.settings.buttons.tool-swap-toggle.status." + status, player).getFirst());

        for(String line : lore.getValues())
            toolSwapToggleMeta.lore(List.of(Component.text(line)));

        setItemMeta(toolSwapToggleMeta);

        onClick((e, c) -> {
            if(config.getPermissions().getSwapPermission() != null && !config.getPermissions().getSwapPermission().equalsIgnoreCase("none") && !c.getPlayer().hasPermission(config.getPermissions().getSwapPermission())){
                messenger.send("errors.no-permission-feature", c.getPlayer());
                return;
            }

            profile.setSwapEnabled(!profile.isSwapEnabled());
            repository.update(profile);

            if(profile.isSwapEnabled()) messenger.send("views.settings.buttons.tool-swap-toggle.actions.enabled", c.getPlayer());
            else messenger.send("views.settings.buttons.tool-swap-toggle.actions.disabled", c.getPlayer());
            c.getCurrentWindow().update();
        });
    }

}