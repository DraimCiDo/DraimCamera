package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import net.draimcido.draimcamera.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Invisible.
 */
public class Invisible extends DraimCameraCommand {

    /**
     * Instantiates a new Invisible.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public Invisible(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (sender.hasPermission("draimcamera.cmd.invisible")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
                    boolean set_invisible = args[0].equalsIgnoreCase("true");
                    player.setInvisible(set_invisible);
                } else {
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.help"), sender);
                }
            } else {
                MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.help"), sender);
            }
        } else {
            MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.no-permission"), sender);
        }
        return false;
    }
}

