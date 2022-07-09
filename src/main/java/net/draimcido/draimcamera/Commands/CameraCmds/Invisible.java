package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invisible extends DraimCameraCommand {

    public Invisible(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (sender.hasPermission("draimcamera.cmd.invisible")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
                    boolean set_invisible = args[0].equalsIgnoreCase("true");
                    player.setInvisible(set_invisible);
                } else {
                    sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.help"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.help"));
            }
        } else {
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Invisible.no-permission"));
        }
        return false;
    }
}

