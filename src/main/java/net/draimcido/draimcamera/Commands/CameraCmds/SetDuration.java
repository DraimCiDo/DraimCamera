package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraUtils;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDuration extends DraimCameraCommand {

    public SetDuration(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.setduration")) {
            if (args.length == 1) {
                int duration = CameraUtils.timeStringToSecondsConvertor(args[0]);

                if (duration > 0) {
                    String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                    if (camera_name != null) {
                        plugin.getConfigCameras().setDuration(camera_name, duration);
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.SetDuration.success"));
                    } else {
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"));
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"));
                    }

                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.SetDuration.invalid-time"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.SetDuration.help"));
            }
        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.SetDuration.no-permission"));
        }
        return false;
    }
}

