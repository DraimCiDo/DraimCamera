package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import net.draimcido.draimcamera.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Create.
 */
public class Create extends DraimCameraCommand {

    /**
     * Instantiates a new Create.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public Create(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.create")) {
            if (args.length == 1) {
                String camera_name = args[0];
                if (plugin.getConfigCameras().create_camera(camera_name)) {
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Create.created"), sender);
                    plugin.player_selected_camera.put(((Player) sender).getUniqueId(), plugin.getConfigCameras().get_camera_name_ignorecase(camera_name));
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.select"), sender);
                } else {
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Create.already-exists"), sender);
                }

            } else {
                MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Create.help"), sender);
            }

        } else {
            MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Create.no-permission"), sender);
        }
        return false;
    }
}

