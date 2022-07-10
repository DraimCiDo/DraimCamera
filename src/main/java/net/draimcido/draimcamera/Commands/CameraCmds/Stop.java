package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stop extends DraimCameraCommand {

    public Stop(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("draimcamera.cmd.stop")) {
                if (this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) != null && this.plugin.player_camera_mode.get(((Player) sender).getUniqueId()) != CameraMode.NONE
                        &&  this.plugin.player_camera_handler.get(((Player) sender).getUniqueId()) != null) {
                this.plugin.player_camera_handler.get(((Player) sender).getUniqueId()).stop();
                if (!sender.hasPermission("draimcamera.hidestartmessage")) sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Stop.stopped"));
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Stop.not-started"));
                }

            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Stop.no-permission"));
            }
        }

        return false;
    }
}

