package xyz.artsna.toolswap.bukkit.data.config.components;

import xyz.artsna.toolswap.bukkit.data.config.Config;

public class OptionConfig {

    private final Config config;

    public OptionConfig(Config config) {
        this.config = config;
    }

    public void configure() {
        config.addDefault("options.default-swap-enabled", true);
        config.addDefault("options.default-prefer-silk-enabled", false);
    }

    public boolean isDefaultSwapEnabled() {
        return config.getBoolean("options.default-swap-enabled");
    }

    public boolean isDefaultPreferSilkEnabled() {
        return config.getBoolean("options.default-prefer-silk-enabled");
    }

}
