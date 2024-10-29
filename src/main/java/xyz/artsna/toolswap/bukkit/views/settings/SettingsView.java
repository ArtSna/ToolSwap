package xyz.artsna.toolswap.bukkit.views.settings;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.bukkit.data.repositories.SwapProfileRepository;
import xyz.artsna.toolswap.bukkit.views.settings.buttons.PreferSilkButton;
import xyz.artsna.toolswap.bukkit.views.settings.buttons.ToolSwapButton;
import xyz.artsna.toolswap.core.inventory.View;
import xyz.artsna.toolswap.core.inventory.ViewContext;
import xyz.artsna.toolswap.core.message.MessageController;

public class SettingsView extends View {

    private final MessageController messenger;
    private final Config config;
    private final SwapProfileRepository repository;

    public SettingsView(MessageController messenger, Config config, SwapProfileRepository repository) {
        super(27, "Tool Swap Settings");

        this.messenger = messenger;
        this.config = config;
        this.repository = repository;

        setDefaultCancel(true);
    }

    @Override
    public void onOpen(@NotNull ViewContext context) {
        updateTitle(messenger.getMessage("views.settings.title", context.getPlayer()).getFirst());
    }

    @Override
    public void onRender(@NotNull ViewContext context) {
        var profile = repository.findById(context.getPlayer().getUniqueId());

        setItem(11, new ToolSwapButton(context.getPlayer(), profile, messenger, config, repository));
        setItem(15, new PreferSilkButton(context.getPlayer(), profile, messenger, config, repository));
    }
}
