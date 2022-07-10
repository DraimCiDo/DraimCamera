package net.draimcido.draimcamera.Commands;

import net.draimcido.draimcamera.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Tab complete.
 */
public class TabComplete implements TabCompleter {

    private Main plugin;

    /**
     * Instantiates a new Tab complete.
     *
     * @param plugin the plugin
     */
    public TabComplete(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> list = new ArrayList<String>();

        if (args.length == 1) {
            ArrayList<String> commands_list = new ArrayList<String>();
            commands_list.add("addcommand");
            commands_list.add("addpoint");
            commands_list.add("create");
            commands_list.add("help");
            commands_list.add("invisible");
            commands_list.add("preview");
            commands_list.add("remove");
            commands_list.add("removepoint");
            commands_list.add("select");
            commands_list.add("setduration");
            commands_list.add("start");
            commands_list.add("startother");
            commands_list.add("stop");


            for (String command : commands_list) {
                if (command.toLowerCase().startsWith(args[0].toLowerCase())) {
                    list.add(command);
                }
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("select") || args[0].equalsIgnoreCase("start")) {
                for (String camera_name : this.plugin.getConfigCameras().getCameras()) {
                    list.add(camera_name);
                }
            }

            if (args[0].equalsIgnoreCase("invisible")) {
                list.add("true");
                list.add("false");
            }

            if (args[0].equalsIgnoreCase("startother")) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    list.add(player.getName());
                }
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("startother")) {
                for (String camera_name : this.plugin.getConfigCameras().getCameras()) {
                    list.add(camera_name);
                }
            }
        }

        return list;
    }
}
