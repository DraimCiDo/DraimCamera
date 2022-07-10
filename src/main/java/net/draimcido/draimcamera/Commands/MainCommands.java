package net.draimcido.draimcamera.Commands;

import net.draimcido.draimcamera.Commands.CameraCmds.*;
import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The type Main commands.
 */
public class MainCommands  implements CommandExecutor {

    private static HashMap<String, DraimCameraCommand> draim_commands = new HashMap<String, DraimCameraCommand>();

    private Main plugin;

    /**
     * Instantiates a new Main commands.
     *
     * @param plugin the plugin
     */
    public MainCommands(Main plugin) {
        this.plugin = plugin;

        new AddCommand(plugin, "addcommand");
        new AddPoint(plugin, "addpoint");
        new Create(plugin, "create");
        new Help(plugin, "help");
        new Invisible(plugin, "invisible");
        new Preview(plugin, "preview");
        new Remove(plugin, "remove");
        new RemovePoint(plugin, "removepoint");
        new Select(plugin, "select");
        new SetDuration(plugin, "setduration");
        new Start(plugin, "start");
        new StartOther(plugin, "startother");
        new Stop(plugin, "stop");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("DraimCamera v" + plugin.getDescription().getVersion());
            sender.sendMessage("Use /draimcamera help for more information.");
            sender.sendMessage("Use /draimcamera reload to reload the config.");
        } else {
            String command = args[0];
            DraimCameraCommand command_handler = get_draimcamera_command(command);
            if (command_handler != null) {
               boolean is_allowed = (sender instanceof Player && (command_handler.getExecutor() == CmdExecutor.PLAYER || command_handler.getExecutor() == CmdExecutor.ALL))
                       || sender instanceof ConsoleCommandSender && (command_handler.getExecutor() == CmdExecutor.CONSOLE || command_handler.getExecutor() == CmdExecutor.ALL);
               if (is_allowed) {
                     return command_handler.onCommand(sender, cmd, commandLabel, Arrays.copyOfRange(args, 1, args.length));
                } else {
                     sender.sendMessage("Only players can use this command.");
               }
            } else {
                sender.sendMessage("Unknown command: " + command);
            }
        }

        return false;
    }

    /**
     * Gets draimcamera command.
     *
     * @param command_name the command name
     * @return the draimcamera command
     */
    public static DraimCameraCommand get_draimcamera_command(String command_name) {
        return draim_commands.get(command_name.toLowerCase());
    }

    /**
     * Add draimcamera command.
     *
     * @param command_name    the command name
     * @param command_handler the command handler
     */
    public static void add_draimcamera_command(String command_name, DraimCameraCommand command_handler) {
        draim_commands.put(command_name.toLowerCase(), command_handler);
    }
}

