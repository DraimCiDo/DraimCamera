package net.draimcido.draimcamera.Commands.CameraCmds;

import net.draimcido.draimcamera.Commands.DraimCameraCommand;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Help.
 */
public class Help extends DraimCameraCommand {

    /**
     * Instantiates a new Help.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     */
    public Help(Main plugin, String command_name) {
        super(plugin, command_name, CmdExecutor.PLAYER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("draimcamera.cmd.help")) {
                sender.sendMessage("§6DraimCamera Help");
                sender.sendMessage("§2/dc addcommand <name> §7- §fAdd a command");
                sender.sendMessage("§2/dc addpoint <name> §7- §fAdd a point");
                sender.sendMessage("§2/dc create <name> §7- §fCreate a camera");
                sender.sendMessage("§2/dc invisible §7- §fToggle invisible mode");
                sender.sendMessage("§2/dc remove <name> §7- §fRemove a camera");
                sender.sendMessage("§2/dc removepoint <name> §7- §fRemove a point");
                sender.sendMessage("§2/dc select <name> §7- §fSelect a camera");
                sender.sendMessage("§2/dc setduration <name> <duration> §7- §fSet the duration of a camera");
                sender.sendMessage("§2/dc start <name> §7- §fStart a camera");
                sender.sendMessage("§2/dc startother <name> §7- §fStart a camera on another player");
                sender.sendMessage("§2/dc stop <name> §7- §fStop a camera");

            } else {
                sender.sendMessage("§6DraimCamera Help");
                sender.sendMessage("§2You don`t have permission to use this command");
                sender.sendMessage(" ");
                sender.sendMessage("§7Created by DraimGooSe <3");
            }
        }
        return false;
    }
}


