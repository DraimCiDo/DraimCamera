package net.draimcido.draimcamera.Commands;

import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The type Draim camera command.
 */
public abstract class DraimCameraCommand {

    /**
     * The Plugin.
     */
    protected Main plugin;

    private CmdExecutor executor = CmdExecutor.NONE;

    /**
     * Instantiates a new Draim camera command.
     *
     * @param plugin       the plugin
     * @param command_name the command name
     * @param executor     the executor
     */
    public DraimCameraCommand(Main plugin, String command_name, CmdExecutor executor) {
        MainCommands.add_draimcamera_command(command_name, this);
        this.executor = executor;
        this.plugin = plugin;
    }

    /**
     * On command boolean.
     *
     * @param sender  the sender
     * @param command the command
     * @param label   the label
     * @param args    the args
     * @return the boolean
     */
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    /**
     * Gets executor.
     *
     * @return the executor
     */
    public CmdExecutor getExecutor() {
        return this.executor;
    }
}
