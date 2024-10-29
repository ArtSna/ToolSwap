package xyz.artsna.toolswap.bukkit.data.entities;

import java.util.UUID;

public class SwapProfile {

    private final UUID id;
    private boolean swapEnabled;
    private boolean preferSilk;

    public SwapProfile(UUID id, boolean swapEnabled, boolean preferSilk) {
        this.id = id;
        this.swapEnabled = swapEnabled;
        this.preferSilk = preferSilk;
    }

    public UUID getId() {
        return id;
    }

    public boolean isSwapEnabled() {
        return swapEnabled;
    }

    public void setSwapEnabled(boolean enabled) {
        this.swapEnabled = enabled;
    }

    public boolean isPreferSilk() {
        return preferSilk;
    }

    public void setPreferSilk(boolean preferSilk) {
        this.preferSilk = preferSilk;
    }

}
