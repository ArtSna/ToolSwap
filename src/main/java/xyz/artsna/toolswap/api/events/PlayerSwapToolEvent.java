package xyz.artsna.toolswap.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerSwapToolEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;

    private final ItemStack oldTool;
    private final ItemStack newTool;

    public PlayerSwapToolEvent(@NotNull Player who, ItemStack oldTool, ItemStack newTool) {
        super(who);

        this.oldTool = oldTool;
        this.newTool = newTool;
    }

    public ItemStack getOldTool() {
        return oldTool;
    }

    public ItemStack getNewTool() {
        return newTool;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
