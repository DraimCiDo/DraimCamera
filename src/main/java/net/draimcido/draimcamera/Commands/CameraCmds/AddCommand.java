package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import net.draimcido.draimcamera.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Add command.
 */
public class AddCommand extends DraimCameraCommand {

    /**
     * Instantiates a new Add command.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public AddCommand(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.addcmd")) {
            if (args.length > 0) {
                String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                if (camera_name != null) {

                    String command = String.join(" ", args);
                    plugin.getConfigCameras().camera_addcommand(camera_name, command);
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.add"), sender);
                } else {
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"), sender);
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"), sender);
                }
            } else {
                MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.help"), sender);
            }

        } else {
            MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.no-permission"), sender);
        }

        return false;
        }
}

