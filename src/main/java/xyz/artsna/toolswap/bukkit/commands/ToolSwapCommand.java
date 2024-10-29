package xyz.artsna.toolswap.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.bukkit.ToolSwapPlugin;
import xyz.artsna.toolswap.bukkit.data.repositories.SwapProfileRepository;
import xyz.artsna.toolswap.bukkit.views.settings.SettingsView;
import xyz.artsna.toolswap.core.command.Arguments;
import xyz.artsna.toolswap.core.command.Command;
import xyz.artsna.toolswap.core.command.JavaCommand;
import xyz.artsna.toolswap.core.command.SenderType;
import xyz.artsna.toolswap.core.message.MessageController;

import java.util.List;

@Command(name = "toolswap", senderType = SenderType.PLAYER)
public class ToolSwapCommand extends JavaCommand {

    private final MessageController messenger = ToolSwapPlugin.instance.getMessenger();
    private final Config config = ToolSwapPlugin.instance.getConfig();
    private final SwapProfileRepository repository = ToolSwapPlugin.instance.getSwapProfileRepository();

    public ToolSwapCommand() {
        if(config.getPermissions().getSwapPermission() != null && !config.getPermissions().getSwapPermission().equalsIgnoreCase("none"))
            setPermission(config.getPermissions().getSwapPermission());
    }

    @Override
    public boolean perform(CommandSender sender, Arguments args) {
        new SettingsView(messenger, config, repository).open((Player) sender);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Arguments args) {
        return List.of();
    }

}
