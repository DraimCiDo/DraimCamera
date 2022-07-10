package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartOther extends DraimCameraCommand {

    public StartOther(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2) {
            String target_name = args[0];
            String camera_name = args[1];
            if (sender.hasPermission("draimcamera.cmd.startother")) {
                Player target_player = Bukkit.getPlayer(target_name);

                if (target_player != null) {
                    if (this.plugin.player_camera_mode.get(target_player.getUniqueId()) == null || this.plugin.player_camera_mode.get(target_player.getUniqueId()) == CameraMode.NONE )  {
                        if (this.plugin.getConfigCameras().camera_exists(camera_name)) {
                            this.plugin.player_camera_handler.put(target_player.getUniqueId(), new CameraHandler(plugin, target_player, camera_name).generatePath().start());
                            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.StartOther.started"));
                        } else {
                            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Select.not-select"));
                        }

                    } else {
                        sender.sendMessage(plugin.getConfig().getString("Messages.Commands.StartOther.already-started"));
                    }

                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.StartOther.player-not-found"));
                }

            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.StartOther.no-permission"));
            }

        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.StartOther.help"));
        }
        return false;
    }
}

