package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Preview.
 */
public class Preview extends DraimCameraCommand {

    /**
     * Instantiates a new Preview.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public Preview(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.preview")) {
            if (this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == null || this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == CameraMode.NONE) {
                if (args.length == 1) {
                    String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                    if (camera_name != null) {
                        int preview_time = plugin.getConfig().getInt("point-preview-time");

                        int num = Integer.parseInt(args[0]) - 1;

                        this.plugin.player_camera_handler.put(((Player) sender).getUniqueId(), new CameraHandler(plugin, (Player) sender, camera_name).generatePath().preview((Player) sender, num, preview_time));

                    } else {
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Preview.no-camera"));
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Preview.help"));
                    }

                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Preview.help"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Preview.arleady-active"));
            }
        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Preview.no-permission"));
        }

        return false;
    }
}


