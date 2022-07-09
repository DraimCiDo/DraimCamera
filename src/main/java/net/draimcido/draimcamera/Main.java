package net.draimcido.draimcamera;

import net.draimcido.draimcamera.Commands.MainCommands;
import net.draimcido.draimcamera.Commands.TabComplete;
import net.draimcido.draimcamera.Config.CameraConfig;
import net.draimcido.draimcamera.Config.MessageConfig;
import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static FileConfiguration config;

    private static Main instance;
    private PluginDescriptionFile pdf;
    private CameraConfig config_cameras;
    private MessageConfig config_plugin;


    public HashMap<UUID, String> player_selected_camera = new HashMap<UUID, String>();
    public HashMap<UUID, CameraMode> player_camera_mode = new HashMap<UUID, CameraMode>();
    public HashMap<UUID, CameraHandler> player_camera_handler = new HashMap<UUID, CameraHandler>();

    public static Main getInstance() {
        return instance;
    }

    public static int getConfigInt(String path) {
        return config.getInt(path);
    }

    public static List<Integer> getConfigIntegerList(String path) {
        return config.getIntegerList(path);
    }

    public static Boolean getConfigBoolean(String path) {
        return config.getBoolean(path);
    }

    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public static String getConfigString(String path) {
        return config.getString(path);
    }

    public PluginDescriptionFile getPluginDescriptionFile() {
        return this.pdf;
    }

    public void onEnable() {
        pdf = this.getDescription();

        Bukkit.getServer().getPluginCommand("draimcamera").setExecutor((CommandExecutor) new MainCommands(this));
        Bukkit.getServer().getPluginCommand("draimcamera").setTabCompleter(new TabComplete(this));

        setupConfig();

        getLogger().info("DraimCamera v" + pdf.getVersion() + " enabled!");
    }

    public void onDisable() {
        getLogger().info("DraimCamera v" + pdf.getVersion() + " disabled!");
    }

    private void setupConfig() {
        config_plugin = new MessageConfig(this);
        config_cameras = new CameraConfig(this);

        config_plugin.getConfig().set("version", null);
        config_cameras.getConfig().set("version", null);

        if (!config_plugin.getConfig().isSet("Camera-Effects.spectator-mode")) config_plugin.getConfig().set("Camera-Effects.spectator-mode", true);
        if (!config_plugin.getConfig().isSet("Camera-Effects.invisible")) config_plugin.getConfig().set("Camera-Effects.invisible", false);

        for (String camera_name : config_cameras.getCameras()) {
            List<String> points = config_cameras.getPoints(camera_name);
            List<String> new_point = new ArrayList<String>();
            for (String point : points) {
                if (!point.startsWith("location:") && !point.startsWith("command:")) {
                    point = "location:" + point;
                }

                if (point.startsWith("location:") && !(point.startsWith("location:linear:") || point.startsWith("location:teleport:"))) {
                    point = point.replaceFirst("location:", "location:linear:");
                }

                new_point.add(point);
            }
            config_cameras.getConfig().set("cameras." + camera_name + ".points", new_point);
        }

        config_plugin.getConfig().set("version", getPluginDescriptionFile().getVersion());
        config_plugin.saveConfig();

        config_cameras.getConfig().set("version", getPluginDescriptionFile().getVersion());
        config_cameras.saveConfig();
    }

    public MessageConfig getMessageConfig() {
        return config_plugin;
    }

    public CameraConfig getConfigCameras() {
        return config_cameras;
    }

}
