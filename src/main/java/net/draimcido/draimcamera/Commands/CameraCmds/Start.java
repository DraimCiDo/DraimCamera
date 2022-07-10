package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import net.draimcido.draimcamera.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start extends DraimCameraCommand {

    public Start(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("draimcamera.cmd.start")) {
                if (this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == null || this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == CameraMode.NONE) {
                    String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                    if (camera_name != null) {
                        this.plugin.player_camera_handler.put(((Player) sender).getUniqueId(), new CameraHandler(plugin, (Player) sender, camera_name).generatePath().start());
                    } else {
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"), sender);
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"), sender);
                    }

                } else {
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.already-started"), sender);
                }

            } else {
                MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.help"), sender);
            }

        } else if (args.length == 1) {
            String camera_name = args[0];

            if (sender.hasPermission("draimcamera.cmd.start." + camera_name.toLowerCase())) {
                if (this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == null || this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) == CameraMode.NONE) {
                    if (this.plugin.getConfigCameras().camera_exists(camera_name)) {
                        this.plugin.player_camera_handler.put(((Player) sender).getUniqueId(), new CameraHandler(plugin, (Player) sender, camera_name).generatePath().start());
                    } else {
                        MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.not-found"), sender);
                    }

                } else {
                    MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.already-started"), sender);
                }

            } else {
                MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.no-permission"), sender);
            }

        } else {
            MessageUtils.sendMessage(plugin.getConfig().getString("Messages.Commands.Start.help"), sender);
        }
        return false;
    }
}



