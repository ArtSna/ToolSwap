package xyz.artsna.toolswap.bukkit.data.config.components;

import org.bukkit.Material;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.core.exceptions.InvalidConfigException;

import java.util.List;
import java.util.stream.Collectors;

public class InteractionConfig {

    private final Config config;

    public InteractionConfig(Config config) {
        this.config = config;
    }

    public void configure() {
        // Material[] pickaxe = { Material.STONE };
        // config.addDefault("interactions.pickaxe", Stream.of(pickaxe).map(Material::toString).toList());
    }

    public List<Material> pickaxe() throws InvalidConfigException {
        if(!config.contains("interactions.pickaxe"))
            throw new InvalidConfigException("You must specify 'interactions.pickaxe' blocks in config.yml");

        var interactions = config.getStringList("interactions.pickaxe");

        if(interactions.isEmpty())
            throw new InvalidConfigException("'interactions.pickaxe' must contain at least one block");

        return interactions.stream().map(Material::valueOf).collect(Collectors.toList());
    }

    public List<Material> shovel() throws InvalidConfigException {
        if(!config.contains("interactions.shovel"))
            throw new InvalidConfigException("You must specify 'interactions.shovel' blocks in config.yml");

        var interactions = config.getStringList("interactions.shovel");

        if(interactions.isEmpty())
            throw new InvalidConfigException("'interactions.shovel' must contain at least one block");

        return interactions.stream().map(Material::valueOf).collect(Collectors.toList());
    }

    public List<Material> axe() throws InvalidConfigException {
        if(!config.contains("interactions.axe"))
            throw new InvalidConfigException("You must specify 'interactions.axe' blocks in config.yml");

        var interactions = config.getStringList("interactions.axe");

        if(interactions.isEmpty())
            throw new InvalidConfigException("'interactions.axe' must contain at least one block");

        return interactions.stream().map(Material::valueOf).collect(Collectors.toList());
    }

    public List<Material> hoe() throws InvalidConfigException {
        if(!config.contains("interactions.hoe"))
            throw new InvalidConfigException("You must specify 'interactions.hoe' blocks in config.yml");

        var interactions = config.getStringList("interactions.hoe");

        if(interactions.isEmpty())
            throw new InvalidConfigException("'interactions.hoe' must contain at least one block");

        return interactions.stream().map(Material::valueOf).collect(Collectors.toList());
    }

    public List<Material> shears() throws InvalidConfigException {
        if(!config.contains("interactions.shears"))
            throw new InvalidConfigException("You must specify 'interactions.shears' blocks in config.yml");

        var interactions = config.getStringList("interactions.shears");

        if(interactions.isEmpty())
            throw new InvalidConfigException("'interactions.shears' must contain at least one block");

        return interactions.stream().map(Material::valueOf).collect(Collectors.toList());
    }
}
