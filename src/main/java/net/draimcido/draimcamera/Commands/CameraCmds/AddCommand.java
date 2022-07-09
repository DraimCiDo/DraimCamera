package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand extends DraimCameraCommand {

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
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.add"));
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"));
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.help"));
            }

        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddCommand.no-permission"));
        }

        return false;
        }
}

