package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Add point.
 */
public class AddPoint extends DraimCameraCommand {

    /**
     * Instantiates a new Add point.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public AddPoint(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("draimcamera.cmd.addpoint")) {
            String easign = "linear";
            if (args.length == 0) {
                String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                if (camera_name != null) {
                    plugin.getConfigCameras().camera_addpoint(((Player) sender).getLocation(), easign, camera_name);
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddPoint.add"));
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"));
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"));
                }

            } else if (args.length == 1)  {
                String camera_name = plugin.player_selected_camera.get(((Player) sender).getUniqueId());
                easign = args[0];
                if (easign.equalsIgnoreCase("linear") || easign.equalsIgnoreCase("teleport")) {
                    if (camera_name != null) {
                        plugin.getConfigCameras().camera_addpoint(((Player) sender).getLocation(), easign, camera_name);
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddPoint.add"));
                    } else {
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"));
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.help"));
                    }
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddPoint.help"));
                }

            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddPoint.help"));
            }

        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.AddPoint.no-permission"));
        }
        return false;
    }
}

