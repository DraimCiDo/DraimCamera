package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Remove extends DraimCameraCommand {

    public Remove(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.remove")) {
            if (args.length == 1) {
                String camera_name = args[0];
                if (plugin.getConfigCameras().remove_camera(camera_name)) {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Remove.removed"));
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Remove.not-found"));
                }

            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Remove.help"));
            }

        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Remove.no-permission"));
        }

        return false;
    }
}


