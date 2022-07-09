package net.draimcido.draimcamera.Commands;

import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CmdExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class DraimCameraCommand {

    protected Main plugin;

    private CmdExecutor executor = CmdExecutor.NONE;

    public DraimCameraCommand(Main plugin, String command_name, CmdExecutor executor) {
        MainCommands.add_draimcamera_command(command_name, this);
        this.executor = executor;
        this.plugin = plugin;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public CmdExecutor getExecutor() {
        return this.executor;
    }
}
