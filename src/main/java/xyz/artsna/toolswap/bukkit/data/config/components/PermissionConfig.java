package xyz.artsna.toolswap.bukkit.data.config.components;

import xyz.artsna.toolswap.bukkit.data.config.Config;

public class PermissionConfig {

    private final Config config;

    public PermissionConfig(Config config) {
        this.config = config;
    }

    public void configure() {
        config.addDefault("permissions.swap", "none");
        config.addDefault("permissions.prefer-silk", "none");
    }

    public String getSwapPermission() {
        return config.getString("permissions.swap");
    }

    public String getPreferSilkPermission() {
        return config.getString("permissions.prefer-silk");
    }
}
