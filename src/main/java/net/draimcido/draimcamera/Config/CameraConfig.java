package net.draimcido.draimcamera.Config;

import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The type Camera config.
 */
public class CameraConfig {

    private File configFile;
    private FileConfiguration config;

    private Main plugin;

    /**
     * Instantiates a new Camera config.
     *
     * @param plugin the plugin
     */
    public CameraConfig(Main plugin) {
        this.plugin = plugin;

        createConfigFile();
    }

    /**
     * Create config file.
     */
    public void createConfigFile() {
        configFile = new File(plugin.getDataFolder(), "camera.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("camera.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public FileConfiguration getConfig() {
        return this.config;
    }

    /**
     * Save config.
     */
    public void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Could not save camera.yml");
        }
    }

    /**
     * Create camera boolean.
     *
     * @param camera_name the camera name
     * @return the boolean
     */
    public boolean create_camera(String camera_name) {
        if (camera_exists(camera_name))
            return false;

        getConfig().set("cameras." + camera_name + ".duration", 10);
        getConfig().set("cameras." + camera_name + ".points", new ArrayList<String>());
        saveConfig();
        return true;
    }

    /**
     * Remove camera boolean.
     *
     * @param camera_name the camera name
     * @return the boolean
     */
    public boolean remove_camera(String camera_name) {
        if (!camera_exists(camera_name))
            return false;

        getConfig().set("cameras." + get_camera_name_ignorecase(camera_name), null);
        saveConfig();
        return true;
    }

    /**
     * Camera exists boolean.
     *
     * @param camera_name the camera name
     * @return the boolean
     */
    public boolean camera_exists(String camera_name) {
        boolean exists = false;
        for (String cam : getConfig().getConfigurationSection("cameras").getKeys(false)) {
            if (cam.equals(camera_name)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * Gets camera name ignorecase.
     *
     * @param input_name the input name
     * @return the camera name ignorecase
     */
    public String get_camera_name_ignorecase(String input_name) {
        String camera_name = null;
        for (String cam : getConfig().getConfigurationSection("cameras").getKeys(false)) {
            if (cam.equalsIgnoreCase(input_name)) {
                camera_name = cam;
                break;
            }
        }
        return camera_name;
    }

    /**
     * Camera addpoint.
     *
     * @param loc         the loc
     * @param easing      the easing
     * @param camera_name the camera name
     */
    public void camera_addpoint(Location loc, String easing, String camera_name) {
        if (!camera_exists(camera_name)) return;

        String new_point = "location:" + easing + ":" + CameraUtils.serializeLocation(loc);

        List<String> camera_points = getConfig().getStringList("cameras." + get_camera_name_ignorecase(camera_name) + ".points");
        camera_points.add(new_point);

        getConfig().set("cameras." + get_camera_name_ignorecase(camera_name) + ".points", camera_points);
        saveConfig();
    }

    /**
     * Camera addcommand.
     *
     * @param command     the command
     * @param camera_name the camera name
     */
    public void camera_addcommand(String command, String camera_name) {
        if (!camera_exists(camera_name)) return;

        String new_point = "command:" + command;

        List<String> camera_commands = getConfig().getStringList("cameras." + get_camera_name_ignorecase(camera_name) + ".commands");
        camera_commands.add(new_point);

        getConfig().set("cameras." + get_camera_name_ignorecase(camera_name) + ".commands", camera_commands);
        saveConfig();
    }

    /**
     * Camera removepoint.
     *
     * @param camera_name the camera name
     * @param num         the num
     */
    public void camera_removepoint(String camera_name, int num) {
        if (!camera_exists(camera_name)) return;

        List<String> camera_points = getConfig().getStringList("cameras." + get_camera_name_ignorecase(camera_name) + ".points");

        if (num < 0) num = 0;
        if (num > camera_points.size() - 1) num = camera_points.size() - 1;

        if (camera_points.size() > 0) {
            if (num == -1)
                num = camera_points.size() - 1;
            camera_points.remove(num);

            getConfig().set("cameras." + get_camera_name_ignorecase(camera_name) + ".points", camera_points);
            saveConfig();
        }
    }

    /**
     * Gets points.
     *
     * @param camera_name the camera name
     * @return the points
     */
    public List<String> getPoints(String camera_name) {
        if (!camera_exists(camera_name)) return null;

        return getConfig().getStringList("cameras." + get_camera_name_ignorecase(camera_name) + ".points");
    }

    /**
     * Sets duration.
     *
     * @param camera_name the camera name
     * @param duration    the duration
     * @return the duration
     */
    public boolean setDuration(String camera_name, int duration) {
        if (!camera_exists(camera_name)) return false;

        getConfig().set("cameras." + get_camera_name_ignorecase(camera_name) + ".duration", duration);
        saveConfig();
        return true;
    }

    /**
     * Gets duration.
     *
     * @param camera_name the camera name
     * @return the duration
     */
    public int getDuration(String camera_name) {
        if (!camera_exists(camera_name)) return -1;

        return getConfig().getInt("cameras." + get_camera_name_ignorecase(camera_name) + ".duration");
    }

    /**
     * Gets cameras.
     *
     * @return the cameras
     */
    public Set<String> getCameras() {
        return getConfig().getConfigurationSection("cameras").getKeys(false);
    }

    /**
     * Add player boolean.
     *
     * @param uuid the uuid
     * @return the boolean
     */
    public boolean addPlayer(UUID uuid) {
        List<String> players = getConfig().getStringList("players");

        if (!players.contains(uuid.toString())) {
            players.add(uuid.toString());

            getConfig().set("players", players);
            saveConfig();
            return true;
        }
        return false;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public List<String> getPlayers() {
        return getConfig().getStringList("players");
    }

}
