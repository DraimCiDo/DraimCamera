package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePoint extends DraimCameraCommand {

    public RemovePoint(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.removepoint")) {
            if (args.length == 0 || args.length == 1) {
                int num = -1;
                if (args.length == 1) {
                    num = Integer.parseInt(args[0]) - 1;
                }

                String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                if (camera_name != null) {
                    plugin.getConfigCameras().camera_removepoint(camera_name, num);
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.RemovePoint.success"));
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.RemovePoint.no-camera"));
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.RemovePoint.help"));
                }

            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.RemovePoint.help"));
            }

        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.RemovePoint.no-permission"));
        }
        return false;
    }
}

